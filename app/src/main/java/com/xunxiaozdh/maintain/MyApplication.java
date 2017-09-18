package com.xunxiaozdh.maintain;

import android.app.Application;
import android.content.Context;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/25 0025 10:46
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
