package com.xunxiaozdh.mydata;

import android.os.Handler;

import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.myevent.MyEvent;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ParamsThroughActivity  implements Serializable {

    String dataList;
    //Class<?> dataType;
    //Handler handler;
    //MyError.ERROR result;
    //MyEvent.EVENT event;

    public String getDataList() {
        return dataList;
    }

    public void setDataList(String dataList) {
        this.dataList = dataList;
    }


}
