package com.xunxiaozdh.myevent;

import com.xunxiaozdh.error.MyError;

import java.util.EnumSet;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/8/31 0031 13:46
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class MyEvent {

    private static final int UPDATE = 0x10010000;

    public enum EVENT {

        Update_ListView(0x0001 | UPDATE),
        Show_History,

        RequestCode_StaffCode,

        Insert_To_Sql,

        Get_Stuff_Code,

        Check_Stuff_Permisson,

        UnKnown_Event;

        private final int value;

        EVENT() {
            this(MyEvent.EVENT.Counter.nextValue);
        }

        EVENT(int value) {
            this.value = value;
            MyEvent.EVENT.Counter.nextValue = value + 1;
        }

        private static class Counter {
            private static int nextValue = 0;
        }

        public final int getValue() {
            return value;
        }

        public static MyEvent.EVENT valueOf(int value) {
            for (MyEvent.EVENT event : EnumSet.allOf(MyEvent.EVENT.class)) {
                if (event.getValue() == value) {
                    return event;
                }
            }
            return EVENT.UnKnown_Event;
        }
    }

}
