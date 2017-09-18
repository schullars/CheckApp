package com.xunxiaozdh.maintain;

import android.util.Log;
import android.view.Window;

import java.security.spec.ECField;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/25 0025 9:23
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyLog {

    public static final int Verbose     = 2;
    public static final int Debug       = 3;
    public static final int Info        = 4;
    public static final int Warn        = 5;
    public static final int Error       = 6;
    public static final int Nothing     = 7;

    public static final int LEVEL       = Verbose;

    public static String getTag()
    {
        try {
            StackTraceElement stack = new Throwable().getStackTrace()[1];
            return String.format("[%s][%s][%s][%s]",stack.getFileName(),stack.getClassName().substring(stack.getClassName().lastIndexOf('.')+1),stack.getMethodName(), stack.getLineNumber());
        }catch (Exception e)
        {
            return String.format("%s:%s","getTag failed",(null != e && null != e.getMessage())?e.getMessage():"get exception failed");
        }
    }

    public static void v(String tag,String info){
        if (LEVEL < Verbose){return;}
        Log.v(tag,info);
    }

    public static void d(String tag,String info){
        if (LEVEL > Debug){return;}
        Log.d(tag,info);
    }

    public static void i(String tag,String info){
        if (LEVEL > Info){return;}
        Log.i(tag,info);
    }
    public static void w(String tag,String info){
        if (LEVEL > Warn){return;}
        Log.w(tag,info);
    }
    public static void e(String tag,String info){
        if (LEVEL > Error){return;}
        Log.e(tag,info);
    }
    public static void n(String tag,String info){
        if (LEVEL > Nothing){return;}
        //Log.v(tag,info);
    }
}
