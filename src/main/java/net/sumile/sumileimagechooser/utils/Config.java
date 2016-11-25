package net.sumile.sumileimagechooser.utils;


import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * 配置文件包含 配置文件缓存路径，
 */
public class Config {

    private String mReadCacheRoot;


    private SharedPreferences sp;
    /***
     * 是否是第一次进入
     ***/
    private boolean isFirstEnter;
    private String mLastUserID;//上次用户id
    private Context mContext;

    public Config(Context context) {
        super();
        // TODO Auto-generated constructor stub
        mContext = context;
        if (!init(context)) {
            DebugLog.d("config", "应用程序配置数据未初始化");
        }

    }

    private boolean init(Context context) {
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        //mReadCacheRoot = Environment.getExternalStorageDirectory() + "/" + "CWReader";

        File dir = context.getExternalFilesDir("cache");
        if (dir == null) {
            mReadCacheRoot = context.getFilesDir().toString() + "/cache";
        } else {
            mReadCacheRoot = dir.toString();
        }
        loadXmlConfig();


        return true;
    }

    private void loadXmlConfig() {
        mLastUserID = sp.getString("lastuserid", "-1");
        isFirstEnter = sp.getBoolean("isFirstEnter", true);
    }

    public String getOnlineBookDir(int onlineID) {
        String dir = mReadCacheRoot + "/book/" + onlineID;
        return dir;
    }

    /**
     * 得到拍照图片存储路径
     *
     * @return
     */
    public String getCameraPath() {
        String dir = mReadCacheRoot + "/photo";
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }

        return dir;
    }

    /**
     * 得到用户头像路径
     *
     * @return
     */
    public String getUserLogoPath(String userId) {
        String dir = mReadCacheRoot + "/photo";
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
        dir += "/avatar_" + userId + ".jpg";
        return dir;
    }

    public String getDownloadFileDir() {
        String dir = mReadCacheRoot + "/download";
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
        return dir;
    }

    public String getCrashLogDir() {
        String dir = mReadCacheRoot + "/crash";
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
        return dir;
    }


    public boolean isFirstEnter() {
        return isFirstEnter;
    }

    public void setFirstEnter(boolean isFirstEnter) {
        this.isFirstEnter = isFirstEnter;
        sp.edit().putBoolean("isFirstEnter", isFirstEnter);
    }


}
