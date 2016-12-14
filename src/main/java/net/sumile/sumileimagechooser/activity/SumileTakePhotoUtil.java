package net.sumile.sumileimagechooser.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import net.sumile.sumileimagechooser.SumileImageChooserUtil;
import net.sumile.sumileimagechooser.util.PermissionUtil;

import java.io.File;

/**
 * Created by sumile on 2016/11/29.
 */

public class SumileTakePhotoUtil extends Activity {
    private File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.request(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtil.PermissionCallBack() {
            @Override
            public void havePermission() {
                PermissionUtil.request(SumileTakePhotoUtil.this, Manifest.permission.CAMERA, new PermissionUtil.PermissionCallBack() {
                    @Override
                    public void havePermission() {
                        takePhoto(SumileTakePhotoUtil.this);
                    }

                    @Override
                    public void requestFail() {
                        toast("无法拍摄照片，请授予程序打开照相机的权限");
                        finish();
                    }
                });
            }

            @Override
            public void requestFail() {
                toast("无法拍摄照片，请授予程序读写存储卡的权限");
                finish();
            }
        });
    }

    public void toast(String msg) {
        Toast.makeText(SumileTakePhotoUtil.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            // 从相机返回的数据
            if (hasSdCard()) {
                if (f != null) {
                    if (f.exists()) {
                        String path = f.getAbsolutePath();
                        Intent intent = new Intent();
                        intent.putExtra("data", path);
                        mCallBack.onActivityResult(intent);
                    }
                } else {
                }
            } else {
            }
        }
        finish();
    }

    private static SumileImageChooserUtil.ActivityResult mCallBack;

    public static void takePhoto(Activity activity, SumileImageChooserUtil.ActivityResult callBack) {
        mCallBack = callBack;
        Intent intent = new Intent(activity, SumileTakePhotoUtil.class);
        activity.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void takePhoto(Context context) {
        if (hasSdCard()) {
            try {
                File dir = new File(Environment.getExternalStorageDirectory() + "/AutoUnion/" + "image");
                if (!dir.exists())
                    dir.mkdirs();

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                f = new File(dir, System.currentTimeMillis() + ".jpg");// localTempImgDir和localTempImageFileName是自己定义的名字
                Uri u = Uri.fromFile(f);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                startActivityForResult(intent, 1);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "呀，没有权限访问你的图片~", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "呀，没有权限访问你的图片~", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean hasSdCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
