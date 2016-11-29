package net.sumile.sumileimagechooser.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by sumile on 2016/11/29.
 */

public class PermissionUtil {
    static PermissionCallBack mCallBack;
    private static String mPermission;
    private static final int REQUEST_CODE = 10002;

    public static abstract class PermissionCallBack {
        public abstract void havePermission();

        public void requestFail() {

        }
    }

    public static void request(Activity acty, String permission, PermissionCallBack callBack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (acty.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                boolean ifNeedShow = acty.shouldShowRequestPermissionRationale(permission);
                if (ifNeedShow) {
                    //介绍为什么需要权限
                    Toast.makeText(acty, "程序需要授权才能拍照，请到设置中打开拍照权限", Toast.LENGTH_SHORT).show();
                } else {
                    acty.requestPermissions(new String[]{permission}, REQUEST_CODE);
                    mPermission = permission;
                    mCallBack = callBack;
                }
            } else {
                callBack.havePermission();
            }
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (permissions[0].equals(mPermission)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                mCallBack.havePermission();
            } else {
                //用户不同意，自行处理即可
                mCallBack.requestFail();
            }
        }
    }
}
