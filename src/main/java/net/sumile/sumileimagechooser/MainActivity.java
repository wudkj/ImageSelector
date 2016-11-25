package net.sumile.sumileimagechooser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_main)
    public void onClick() {
        SumileImageChooserUtil.startThisForPics(this, 3, new SumileImageChooserUtil.ActivityResult() {
            @Override
            public void onActivityResult(Intent data) {
                ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra("data");
                Log.e("sumile", list.toString());
            }
        });
    }
}
