package net.sumile.sumileimagechooser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.sumile.sumileimagechooser.activity.ImageChooserActivity;

/**
 * Created by sumile on 2016/11/24.
 */

public class SumileImageChooserUtil extends Activity {
    private static int mMax_pics = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toImageChooseActivity(mMax_pics);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == REQUEST_CODE) {
            if (requestCode == REQUEST_CODE) {
                if (data != null) {
                    if (mCallBack != null) {
                        mCallBack.onActivityResult(data);
                    }
                }
            }
        }
        finish();
    }

    public interface ActivityResult {
        /**
         *data.getExtera
         * @param data
         */
        void onActivityResult(Intent data);
    }

    public static final int REQUEST_CODE = 1009000;

    private void toImageChooseActivity(int max_pics) {
        Intent intent = new Intent(this, ImageChooserActivity.class);
        intent.putExtra(ImageChooserActivity.MAX_PICS, max_pics);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public static ActivityResult mCallBack;

    /**
     * {@code SumileImageChooserUtil.startThisForPics(this, 3, new SumileImageChooserUtil.ActivityResult() {
    @Override public void onActivityResult(Intent data) {
    ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra("data");
    for (String str : list) {
    ImageView imageView = new ImageView(MainActivity.this);
    BitmapCache cache = new BitmapCache();
    cache.displayBmp(imageView, str, str, null);
    activityMain.addView(imageView);
    }
    }
    });}
     */
    public static void startThisForPics(Activity activity, int max_pic, ActivityResult callBack) {
        mMax_pics = max_pic;
        mCallBack = callBack;
        Intent intent = new Intent(activity, SumileImageChooserUtil.class);
        activity.startActivity(intent);
    }
}
