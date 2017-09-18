package com.xunxiaozdh.maintain;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  schullar
 * Company: Xunxiao
 * Date:    2017/9/2 0002 16:42
 * Mail:    schullar@outlook.com
 * Descrip:
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static List<Activity> getActivities() {
        return activities;
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void removeActivity(int index){activities.remove(index);}

    public static Activity getActivityAt(int index){
        if (null == ActivityCollector.getActivities() || index > ActivityCollector.getActivities().size()){
            return null;
        }
        return ActivityCollector.getActivities().get(index);
    }

    public static Activity getActivity(Class<?> cls){
        for (Activity activity : activities){
            if (activity.getClass() == cls){
                return activity;
            }
        }
        return null;
    }

    public static boolean finishAt(int index){
        if (index <0 || index > activities.size()){return false;}
        activities.get(index).finish();
        removeActivity(index);
        return true;
    }

    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
