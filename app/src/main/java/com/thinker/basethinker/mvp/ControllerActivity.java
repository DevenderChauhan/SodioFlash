package com.thinker.basethinker.mvp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farley on 17/3/14.
 * description:activity管理类
 */

public class ControllerActivity {
    static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }
    public static void removeActivity(Activity activity){
        if (activities.contains(activity)) {
            activities.remove(activity);
            activity.finish();
        }
    }
    public static void finishAll(){
        for (Activity activity:activities){
            activity.finish();
        }
    }
}
