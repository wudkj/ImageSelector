package net.sumile.sumileimagechooser.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.sumile.imageviewpager.activity.ImagePagerActivity;
import net.sumile.sumileimagechooser.R;
import net.sumile.sumileimagechooser.SumileImageChooserUtil;
import net.sumile.sumileimagechooser.adapter.FolderListAdapter;
import net.sumile.sumileimagechooser.adapter.ImageGridAdapter;
import net.sumile.sumileimagechooser.bean.ImageBucket;
import net.sumile.sumileimagechooser.bean.ImageItem;
import net.sumile.sumileimagechooser.util.ImageBucketProvider;
import net.sumile.sumileimagechooser.utils.Bimp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sumile on 2016/11/19.
 */

public class ImageChooserActivity extends FragmentActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.left_layout)
    RelativeLayout leftLayout;
    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.main_right_text)
    TextView mainRightText;
    @Bind(R.id.right_layout_text)
    RelativeLayout rightLayoutText;
    @Bind(R.id.folderName)
    TextView folderName;
    @Bind(R.id.selectedNum)
    TextView selectedNum;
    @Bind(R.id.preview)
    TextView preview;
    @Bind(R.id.tmp_bar)
    RelativeLayout tmpBar;
    @Bind(R.id.gridview)
    GridView gridview;
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.folderLinearLayout)
    LinearLayout folderLinearLayout;
    @Bind(R.id.frame)
    LinearLayout frame;
    //所有的相册数据
    private List<ImageBucket> buckets;
    //当前正在显示的相册position
    private int mPosition = 0;
    //当前正在显示的相册的图片列表
    private List<ImageItem> imageList;
    //当前正在显示的相册的名字
    private String bucketName;
    //允许选择的最大的图片数量
    private int max_pic = 3;
    //显示图片的grid的adapter
    private ImageGridAdapter adapter;
    private List<ImageBucket> folderList;
    private FolderListAdapter folderAdapter;
    private ArrayList<String> selectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagechooserlayout);
        ButterKnife.bind(this);
        Bimp.clear();
        parseIntent();
        initAnim();
        initFolderLayout();
        initData();
        initAction();
    }

    public static final String MAX_PICS = "MAX_PICS";

    private void parseIntent() {
        Intent intent = getIntent();
        max_pic = intent.getIntExtra(MAX_PICS, 1);
    }

    private void initFolderLayout() {
        buckets = ImageBucketProvider.getImageBuckets(ImageChooserActivity.this);
        folderList = buckets;
        folderAdapter = new FolderListAdapter(this, folderList);
        list.setAdapter(folderAdapter);
    }

    private void initViewWithData() {
        selectList = getSelectedPic();
        //界面元素设置
        folderName.setText(bucketName);
        topTitle.setText(bucketName);
        rightLayoutText.setVisibility(View.VISIBLE);
        mainRightText.setText("确定(" + Bimp.drr.size() + "/" + max_pic + ")");
        selectedNum.setVisibility(View.GONE);
    }


    private Animation showAnim;
    private Animation hideAnim;

    private void initAnim() {
        showAnim = AnimationUtils.loadAnimation(this, R.anim.photo_dialog_in_anim);
        showAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                frame.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hideAnim = AnimationUtils.loadAnimation(this, R.anim.photo_dialog_out_anim);
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                frame.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                folderLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private boolean checkAnimIsRunning() {
        if ((hideAnim.hasStarted() && !hideAnim.hasEnded()) || (showAnim.hasStarted() && !showAnim.hasEnded())) {
            return true;
        } else {
            return false;
        }
    }

    private void hideFolderLinearLayout() {
        if (checkAnimIsRunning()) {
            return;
        }
        folderLinearLayout.startAnimation(hideAnim);
    }

    private void showFolderLinearLayout() {
        if (checkAnimIsRunning()) {
            return;
        }
        folderLinearLayout.setVisibility(View.VISIBLE);
        folderLinearLayout.startAnimation(showAnim);
    }

    private void initAction() {
        //文件夹名字的点击事件，点击之后要显示所有文件夹
        folderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderLinearLayout.getVisibility() == View.VISIBLE) {
                    hideFolderLinearLayout();
                } else {
                    showFolderLinearLayout();
                }
            }
        });
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFolderLinearLayout();
            }
        });
        selectedNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bimp.clear();
                updateUI();
                adapter.clearSelect();
            }
        });
        //预览的点击事件
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePagerActivity.startActivityWithHF(ImageChooserActivity.this, 0, Bimp.drr, new ImagePagerActivity.OnSelectedListener() {
                    @Override
                    public void onSelected(boolean ifSelected, String path) {
                        if (ifSelected) {
                            Bimp.drr.add(path);
                        } else {
                            Bimp.drr.remove(path);
                        }
                        updateUI();
                        adapter.updateSelect(ifSelected, path);
                    }
                });
            }
        });
        mainRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectList = getSelectedPic();

//                for (int i = 0; i < selectList.size(); i++) {
//                    if (Bimp.drr.size() < max_pic) {
//                        if (Bimp.drr.contains(selectList.get(i))) {
//                            continue;
//                        }
//                        Bimp.drr.add(selectList.get(i));
//                        Bimp.isTakePic.add(false);
//                    }
//                }

//                Intent finishBroadCast = new Intent();
//                finishBroadCast.setAction(FINISH_IMAGEPIC);
//                sendBroadcast(finishBroadCast);

                Intent result = new Intent();
                result.putExtra("data", (Serializable) selectList);
                setResult(SumileImageChooserUtil.REQUEST_CODE, result);
                finish();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                initData();
                hideFolderLinearLayout();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<String> getSelectedPic() {
//        ArrayList<String> list = new ArrayList<String>();
//        Collection<String> c = Bimp.drr.values();
//        Iterator<String> it = c.iterator();
//        for (; it.hasNext(); ) {
//            list.add(it.next()); // 把选中图片的原始地址添加到Bimp.drr
//        }
        return Bimp.drr;
    }

    private void initData() {
        //获得相册
        imageList = buckets.get(mPosition).imageList;
        bucketName = buckets.get(mPosition).bucketName;
        //设置adapter
        adapter = new ImageGridAdapter(ImageChooserActivity.this, imageList, max_pic);
        gridview.setAdapter(adapter);
        //adapter中选择之后的回调
        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            @Override
            public void onListen(int count) {
                updateUI();
            }
        });
        initViewWithData();
    }

    private void updateUI() {
        mainRightText.setText("确定(" + Bimp.drr.size() + "/" + max_pic + ")");
        preview.setText("预览(" + Bimp.drr.size() + ")");
        if (Bimp.drr.size() > 0) {
            mainRightText.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_view_border));
            mainRightText.setTextColor(getResources().getColor(R.color.title_yes_text_color_white));
            preview.setText("预览(" + Bimp.drr.size() + ")");
            preview.setClickable(true);
            preview.setTextColor(getResources().getColor(R.color.bar_text_color_light));
            selectedNum.setVisibility(View.GONE);
            selectedNum.setText("已选择" + Bimp.drr.size() + "张(清空选择)");
        } else {
            mainRightText.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_view_border_dark));
            mainRightText.setTextColor(getResources().getColor(R.color.title_yes_text_color));
            preview.setText("预览");
            preview.setClickable(false);
            preview.setTextColor(getResources().getColor(R.color.bar_text_color));
            selectedNum.setVisibility(View.GONE);
        }
    }
}
