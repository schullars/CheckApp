package com.xunxiaozdh.mydata;

import android.os.Handler;

import com.xunxiaozdh.error.MyError;
import com.xunxiaozdh.myevent.MyEvent;

import java.sql.ResultSet;
import java.util.List;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/30 0030 10:17
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class SqlCmd {
    public enum CmdType{
        ExcuteQuary,// ?????????
        ExcuteInsert,
        Excute,// ????????????????
    }

    String cmd;
    Class<?> dataType;
    List<Object> dataList;
    Handler handler;
    CmdType cmdType;
    MyError.ERROR result;
    MyEvent.EVENT event;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    public MyError.ERROR getResult() {
        return result;
    }

    public void setResult(MyError.ERROR result) {
        this.result = result;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public CmdType getCmdType() {
        return cmdType;
    }

    public void setCmdType(CmdType cmdType) {
        this.cmdType = cmdType;
    }

    public MyEvent.EVENT getEvent() {
        return event;
    }

    public void setEvent(MyEvent.EVENT event) {
        this.event = event;
    }
}
