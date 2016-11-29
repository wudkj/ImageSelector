package net.sumile.sumileimagechooser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.sumile.sumileimagechooser.utils.BitmapCache;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

//    @Bind(R.id.activity_main)
//    LinearLayout activityMain;
//    private View rootView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        rootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
//        setContentView(rootView);
//        ButterKnife.bind(this);
//    }
//
//    @OnClick(R.id.activity_main)
//    public void onClick() {
//        SumileImageChooserUtil.startThisForPics(this, 3, new SumileImageChooserUtil.ActivityResult() {
//            @Override
//            public void onActivityResult(Intent data) {
//                ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra("data");
//                for (String str : list) {
//                    ImageView imageView = new ImageView(MainActivity.this);
//                    BitmapCache cache = new BitmapCache();
//                    cache.displayBmp(imageView, str, str, null);
//                    activityMain.addView(imageView);
//                }
//            }
//        });
//    }
}
