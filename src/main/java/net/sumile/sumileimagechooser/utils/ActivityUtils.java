package net.sumile.sumileimagechooser.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * It use to finish all activities</br> To get start you should：</br> 1： Invoke
 * the method ActivityUtils.add(this) in onCreate method in activties<br>
 * 2： ActivityUtils.remove(this) in onDestroy method in activties.</br>
 * </br> when you quit app,you should invoke finishProgram
 */

@SuppressWarnings("unused")
public class ActivityUtils {
    private static final String TAG = ActivityUtils.class.getSimpleName();

    private static List<Activity> activitys = new ArrayList<>();

    public static void add(Activity activity) {
        activitys.add(activity);
        DebugLog.d(TAG, "add:" + activity.getClass().getSimpleName());
    }

    public static void remove(Activity activity) {
        if (activity != null) {
            activitys.remove(activity);
            DebugLog.d(TAG, "remove:" + activity.getClass().getSimpleName());
        }
    }

    public static void exitApp() {
        for (Activity activity : activitys) {
            if (null != activity) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void finishAllActivitys() {
        for (Activity activity : activitys) {
            if (null != activity) {
                activity.finish();
            }
        }
    }


    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }
}
