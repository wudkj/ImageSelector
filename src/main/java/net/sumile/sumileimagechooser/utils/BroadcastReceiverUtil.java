package net.sumile.sumileimagechooser.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by sumile on 2016/11/14.
 */

public class BroadcastReceiverUtil {
    /**
     * 地图上选择位置
     */
    public static class CHOOSEPOSITION {
        public static String ACTION = "CHOOSEPOSITION";
        public static String latlng = "latlng";
        public static String type = "type";
        public static String result = "result";
        public static String bundle = "bundle";
    }



    public static BroadcastReceiver regist(Activity activity, final String action, final OnReceiveInterface callBack) {
        BroadcastReceiver receiver = null;
        activity.registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (action.equals(intent.getAction())) {
                    if (callBack != null) {
                        callBack.onReceive(intent);
                    }
                }
            }
        }, new IntentFilter(action));
        return receiver;
    }

    public static void sendBroadCastReceiver(Activity activity, Intent intent) {
        activity.sendBroadcast(intent);
    }

    public static abstract class OnReceiveInterface {
        public abstract void onReceive(Intent intent);
    }

    public static void unRegisteReceiver(Activity activity, BroadcastReceiver receiver) {
        activity.unregisterReceiver(receiver);
    }
}
