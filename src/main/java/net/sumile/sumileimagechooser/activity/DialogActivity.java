package net.sumile.sumileimagechooser.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import net.sumile.sumileimagechooser.R;
import net.sumile.sumileimagechooser.adapter.FolderListAdapter;
import net.sumile.sumileimagechooser.bean.ImageBucket;
import net.sumile.sumileimagechooser.util.ImageBucketProvider;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sumile on 2016/11/19.
 */

public class DialogActivity extends DialogFragment {
    @Bind(R.id.list)
    ListView list;
    private View mRootView;
    private FragmentManager fragmentManager;
    //要显示的所有的文件夹
    private List<ImageBucket> folderList;
    //文件夹的adapter
    private FolderListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.folderchooselayout, container, true);
        fragmentManager = getChildFragmentManager();
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        folderList = ImageBucketProvider.getImageBuckets(getActivity());
        adapter = new FolderListAdapter(getActivity(), folderList);
        list.setAdapter(adapter);
        setPositionOnBottom();
    }

    private void setPositionOnBottom() {
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Window window = getDialog().getWindow();
        final WindowManager.LayoutParams layoutParams = window.getAttributes();
//        window.setWindowAnimations(R.style.main_menu_animstyle);
//        width = layoutParams.width = (int) (dm.widthPixels);
//        height = layoutParams.height = (int) (dm.heightPixels);
        layoutParams.gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
