package net.sumile.sumileimagechooser.utils;

import android.os.Environment;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Constant {
    // sd卡路径
    public static final String ETAXER_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/runyige";
    public static final String AVATAR_PATH_IN_SDCARD = ETAXER_PATH + "/picture";
    public static final int IMAGE_REQUEST_CODE = 100000001;

    public static final String MAX_PIC = "max_pic";

    public static final String EXTRA_IMAGE_LIST = "imagelist";
}
