package com.xunxiaozdh.maintain;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/9/2 0002 16:46
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
