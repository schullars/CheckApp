package com.xunxiaozdh.error;

import java.util.EnumSet;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/30 0030 10:44
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyError {

    private static final int SQL_ERROR = 0x10010000;

    public enum ERROR{
        // sql server error
        Get_Data_From_Sql_Failed(0x0001|SQL_ERROR),
        Connect_Sql_Failed,
        Generate_Sql_Cmd_Failed,
        Insert_To_Sql_Failed,

        // uart get data error
        Uart_Invalid_Cmd_Params,
        Uart_Send_Cmd_Failed,
        Uart_Receive_Data_Failed,


        No_Error,
        UnInit,
        UnKnown_Error;

        private final int value;

        ERROR(){
            this(Counter.nextValue);
        }
        ERROR(int value){
            this.value = value;
            Counter.nextValue = value + 1;
        }

        private static class Counter{
            private static int nextValue = 0;
        }

        public int getValue(){
            return value;
        }

        public static ERROR valueOf(int value){
            for (ERROR error: EnumSet.allOf(ERROR.class)){
                if (error.getValue() == value){
                    return error;
                }
            }
            return ERROR.UnKnown_Error;
        }
    }


}
