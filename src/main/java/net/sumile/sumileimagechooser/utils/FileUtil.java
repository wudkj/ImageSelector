package net.sumile.sumileimagechooser.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/7/27.
 */
public class FileUtil {
    public static File avatarFile; //拍照的文件

    //获取图片保存的路径
    public static Uri getCaptureFilePath(Context context) {
        avatarFile = null;
        String saveDir = "";
        if (FileUtil.isSdcardAvailable()) {
            saveDir = Constant.AVATAR_PATH_IN_SDCARD;
            FileUtil.createSaveDir(saveDir);

        } else {
            UIHelper.ToastMessage(context, "请查看sd卡是否存在");
            return null;
        }
        String fileName = "picture" + System.currentTimeMillis() + ".JPEG";
        avatarFile = new File(saveDir, fileName);
        DebugLog.e("avatarFile:::", avatarFile.getPath());

        return Uri.fromFile(avatarFile);
    }

    public static void createSaveDir(String savePath) {
        File savedir = new File(savePath);
        if (!savedir.exists()) {
            savedir.mkdirs();
        } else {
            // deleteAllFiles(savedir);
        }
    }

    /**
     * 检查存储卡是否插入
     *
     * @return
     */
    public static boolean isSdcardAvailable() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
