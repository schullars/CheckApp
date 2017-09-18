package com.xunxiaozdh.other;

import android.os.Message;

import com.smatek.uart.UartComm;
import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.maintain.MyLog;
import com.xunxiaozdh.mydata.UartCmd;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/9/2 0002 10:48
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class BarCodeScanner {

    public enum CmdType{
        Uart_Trigger,
        Hid_Trigger,
    }

    private static final String CmdType_HID = "SET%SCAN_HID_T\r\n";
    private static final String CmdType_UART = "SET%SCAN_USART\r\n";
    private static final String Uart_Com_Device = "/dev/ttyS3";
    private static int myLock = 0;


    private static UartComm uartComm = null;
    private static int uart_fd = 0;
    private UartCmd uartCmd;

    public static BarCodeScanner newInstance(UartCmd uartCmd){

        if (myLock>0){return null;}// 不能多次占用

        myLock = 1;
        BarCodeScanner barCodeScanner = new BarCodeScanner();
        barCodeScanner.uartCmd = uartCmd;
        if (0> barCodeScanner.initUart()){
            myLock = 0;
            return null;
        }
        return barCodeScanner;
    }

    private int initUart(){// 初始化串口
        if (null != uartComm){
            return uart_fd;
        }
        try{
            uartComm = new UartComm();
            uart_fd = uartComm.uartInit(Uart_Com_Device);
            uartComm.setOpt(uart_fd,9600,8,0,1);
        }catch (Exception e){
            MyLog.e(MyLog.getTag(),"init com for scanner failed.");
            if (0 < uart_fd && null != uartComm){
                try {
                    uartComm.uartDestroy(uart_fd);
                    uartComm = null;
                    uart_fd = 0;
                }catch (Exception ex){MyLog.e(MyLog.getTag(),"destroy uart failed,info:"+ex.getMessage());}
            }
            return -1;
        }
        return uart_fd;
    }

    private void DeinitUart(){
        if (0 >= myLock){ return;}

        if (null == uartComm || 0 >= uart_fd){return;}

        uartComm.uartDestroy(uart_fd);
        uartComm = null;
        uart_fd = 0;
        myLock = 0;
    }

    private int[] TransStrTointArray(String str){
        if (null == str){
            return null;
        }
        int[] intArray = new int[str.length()];
        for (int i=0;i<str.length();i++){
            intArray[i] = str.charAt(i);
        }
        return intArray;
    }

    private String TransIntArrayToStr(int[] intArray,int len){
        if (null == intArray){
            return "";
        }
        String str="";
        for (int i=0;i<len;i++){
            str +=  String.format("%c",intArray[i]&0xff);
        }
        return str;
    }

    private String ReceiveDataFromUart(){
        int[] recvInt=new int[256];
        int len=0;
        try {
            len = uartComm.recv(uart_fd,recvInt,recvInt.length);
        }catch (Exception e){
            MyLog.d(MyLog.getTag(),"receive data from uart failed,data is:\r\n"+recvInt.toString());
        }

        return TransIntArrayToStr(recvInt,len);
    }

    Thread UartThread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (null == uartCmd){
                MyLog.e(MyLog.getTag(),"uartCmd is null, do not trigger scanner");
                return;
            }
            if (null == uartCmd.getCmdType()){
                MyLog.w(MyLog.getTag(),"uartCmdType is null,default as hid mode");
            }

            Message message = new Message();
            message.what = MyError.ERROR.No_Error.getValue();
            uartCmd.setResult(MyError.ERROR.No_Error);

            int[] intArray;
            switch (uartCmd.getCmdType()){
                case Uart_Trigger:
                    intArray = TransStrTointArray(CmdType_UART);
                    break;
                case Hid_Trigger:
                default:
                    intArray = TransStrTointArray(CmdType_HID);
                    break;
            }

            try{
                uartComm.send(uart_fd,intArray,intArray.length);

                if (uartCmd.getCmdType() == CmdType.Uart_Trigger){
                    uartCmd.setData(ReceiveDataFromUart());
                    if (0 == uartCmd.getData().length()){
                        uartCmd.setResult(MyError.ERROR.Uart_Receive_Data_Failed);
                        message.what = MyError.ERROR.Uart_Receive_Data_Failed.getValue();
                    }
                }

            }catch (Exception e){
                MyLog.e(MyLog.getTag(),"send cmd to uart failed");
                uartCmd.setResult(MyError.ERROR.Uart_Send_Cmd_Failed);
                message.what = MyError.ERROR.Uart_Send_Cmd_Failed.getValue();
            }

            DeinitUart();
            uartCmd.getHandler().sendMessage(message);
        }
    });

    public void start(){
        UartThread.start();
    }
}
