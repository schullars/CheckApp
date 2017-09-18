package com.xunxiaozdh.mydata;

import android.os.Handler;

import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.other.BarCodeScanner;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/9/2 0002 11:19
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class UartCmd {
    private BarCodeScanner.CmdType cmdType;
    private String data;
    private Handler handler;
    private MyError.ERROR result;

    public BarCodeScanner.CmdType getCmdType() {
        return cmdType;
    }

    public void setCmdType(BarCodeScanner.CmdType cmdType) {
        this.cmdType = cmdType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public MyError.ERROR getResult() {
        return result;
    }

    public void setResult(MyError.ERROR result) {
        this.result = result;
    }
}
