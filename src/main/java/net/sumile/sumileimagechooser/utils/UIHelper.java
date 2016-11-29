package net.sumile.sumileimagechooser.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.MessageQueue;
import android.util.TypedValue;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UIHelper {

    private static ProgressDialog pdDialog;

    public static void ToastMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(String msg) {
//        Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }


    private static Method mQueueNext;
    private static MessageQueue mQueue;
    private static Field mTarget;


    /**
     * dip转换px
     */
    public static int dip2px(Context acty, int dip) {
//        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                acty.getResources().getDisplayMetrics());
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(Context acty, int px) {
        final float scale = acty.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 显示进度框
     */
    public static ProgressDialog showProgressDialog(Context context) {

        if (pdDialog == null) {
            pdDialog = new ProgressDialog(context);
        }

        pdDialog.setMessage("数据正在提交，请稍候...");
        pdDialog.setCancelable(true);
        pdDialog.setIndeterminate(false);
        pdDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdDialog.show();
        return pdDialog;
    }

//    private ProgressDialog pdDialog;

    /**
     * 显示进度框
     */
    public static ProgressDialog showProgressMessageDialog(Context context,
                                                           String Message) {
        pdDialog = new ProgressDialog(context);
        pdDialog.setMessage(Message);
        pdDialog.setCancelable(true);
        pdDialog.setIndeterminate(false);
        pdDialog.setCanceledOnTouchOutside(false);
        pdDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdDialog.show();
        return pdDialog;
    }

    /**
     * 显示下载进度框
     */
    public static ProgressDialog showProgressDownloadDialog(Context context) {
        ProgressDialog pdDialog = new ProgressDialog(context);
        pdDialog.setMessage("正在下载，请稍候...");
        pdDialog.setCancelable(true);
        pdDialog.setIndeterminate(false);
        pdDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdDialog.show();
        return pdDialog;
    }

    /**
     * 隐藏进度框
     */
    public static void dissmissProgressDialog(ProgressDialog pdDialog) {
        if (pdDialog != null) {
            pdDialog.dismiss();
        }
    }

    /**
     * 隐藏进度框
     */
    public static void dissmissProgressDialog() {
        if (pdDialog != null) {
            pdDialog.dismiss();
        }
    }
}
