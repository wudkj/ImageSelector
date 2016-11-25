package net.sumile.sumileimagechooser.activity;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import net.sumile.sumileimagechooser.R;
import net.sumile.sumileimagechooser.adapter.FolderListAdapter;
import net.sumile.sumileimagechooser.bean.ImageBucket;
import net.sumile.sumileimagechooser.util.ImageBucketProvider;

import java.util.List;

/**
 * Created by sumile on 2016/11/19.
 */

public class PopupWindowActivity {
    private static PopupWindow mPopupWindow;
    private static List<ImageBucket> folderList;
    private static FolderListAdapter adapter;

    public static void show(Activity activity, View v) {
        View popupView = LayoutInflater.from(activity).inflate(R.layout.folderchooselayout, null);
        ListView list = (ListView) popupView.findViewById(R.id.list);
        folderList = ImageBucketProvider.getImageBuckets(activity);
        adapter = new FolderListAdapter(activity, folderList);
        list.setAdapter(adapter);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

}
