package net.sumile.sumileimagechooser.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import net.sumile.imageviewpager.activity.ImagePagerActivity;
import net.sumile.sumileimagechooser.R;
import net.sumile.sumileimagechooser.bean.ImageItem;
import net.sumile.sumileimagechooser.utils.Bimp;
import net.sumile.sumileimagechooser.utils.BitmapCache;
import net.sumile.sumileimagechooser.utils.UIHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


public class ImageGridAdapter extends BaseAdapter {

    private TextCallback textcallback = null;
    final String TAG = getClass().getSimpleName();
    private Context act;
    private List<ImageItem> dataList;
    public LinkedHashMap<String, String> map = new LinkedHashMap<>();
    private BitmapCache cache;
    private int selectTotal = Bimp.bmp.size();
    private Set set = new HashSet(); //记录上次选中的图片
    private int maxPic;
    /**
     * 为gridView的item'上imageview设置图片的回调
     */
    BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, Bitmap bitmap, Object... params) {
            if (imageView != null && bitmap != null) {
                String url = (String) params[0];
                if (url != null && url.equals(imageView.getTag())) {
                    (imageView).setImageBitmap(bitmap);

                } else {
                    Log.e(TAG, "callback, bmp not match");
                }
            } else {
                Log.e(TAG, "callback, bmp null");
            }
        }
    };

    public void clearSelect() {
        for (ImageItem item : dataList) {
            if (item.isSelected) {
                item.isSelected = false;
            }
        }
        selectTotal = Bimp.drr.size();
        notifyDataSetChanged();
    }

    public void updateSelect(boolean ifSelected, String path) {
        if (path == null) {
            return;
        }
        for (ImageItem item : dataList) {
            if (path.equals(item.imagePath)) {
                item.isSelected = ifSelected;
            }
        }
        selectTotal = Bimp.drr.size();
        notifyDataSetChanged();
    }

    public List<ImageItem> getData() {
        return dataList;
    }

    public interface TextCallback {
        void onListen(int count);
    }

    public void setTextCallback(TextCallback listener) {
        textcallback = listener;
    }

    private ArrayList<String> pathList = new ArrayList<>();

    public ImageGridAdapter(Context act, List<ImageItem> list, int maxPic) {
        this.act = act;
        dataList = list;
        if (list != null) {
            for (ImageItem item : list) {
                if (item != null) {
                    pathList.add(item.imagePath);
                }
            }
        }
        cache = new BitmapCache();
        this.maxPic = maxPic;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (dataList != null) {
            count = dataList.size();
        }
        return count;
    }

    @Override
    public ImageItem getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder {
        // 获取屏幕宽
        private int width = ((Activity) act).getWindowManager().getDefaultDisplay().getWidth();
        private int itemWrite = (width - 2 * (UIHelper.dip2px(5))) / 3;
        private ImageView iv;
        private ImageView selected;
        private TextView text;
        private CheckBox isChecked;
        private LinearLayout grid_linearlayout;
        private TextView frame;
    }

    private int height;
    private int width;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(act, R.layout.item_image_grid, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.image);
            holder.frame = (TextView) convertView.findViewById(R.id.tv_frame);
            LayoutParams params = new LayoutParams(holder.itemWrite, holder.itemWrite);
            holder.frame.setLayoutParams(params);
            holder.iv.setLayoutParams(params);
            holder.selected = (ImageView) convertView.findViewById(R.id.isSelected2);
            holder.isChecked = (CheckBox) convertView.findViewById(R.id.isChecked);
            holder.text = (TextView) convertView.findViewById(R.id.item_image_grid_text);
            holder.grid_linearlayout = (LinearLayout) convertView.findViewById(R.id.grid_linearlayout);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();

        }
        final ImageItem item = dataList.get(position);

        holder.iv.setTag(item.imagePath);
        //用cache获取图片，并执行回调
        cache.displayBmp(holder.iv, item.thumbnailPath, item.imagePath, callback);

        if (Bimp.drr.contains(item.imagePath)) {
            if (parent.getChildCount() == position) {
                item.isSelected = true;
                //记录上次选中的图片
                set.add(position);
            }
        } else {
            item.isSelected = false;
        }
        holder.grid_linearlayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                check(position, holder, item);
            }
        });
        holder.isChecked.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                check(position, holder, item);
            }
        });
        holder.iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //单独显示这一个的
//                ArrayList<String> urls = new ArrayList<>();
//                urls.add(item.imagePath);
//                ImagePagerActivity.startActivity(act, 0, urls);
                //可以多个选择的

                ImagePagerActivity.startActivityWithHF_NotSelected(act, position, pathList, new ImagePagerActivity.OnSelectedListener() {
                    @Override
                    public void onSelected(boolean ifSelected, String path) {
                        if (ifSelected) {
                            Bimp.drr.add(path);
                        } else {
                            Bimp.drr.remove(path);
                        }
                        updateSelect(ifSelected, path);
                    }
                });
            }

        });
        if (item.isSelected) {
            holder.isChecked.setChecked(true);
            showFrame(holder);
//            holder.selected.setVisibility(View.VISIBLE);
//            holder.selected.setImageResource(R.drawable.correct);
            holder.text.setBackgroundResource(R.drawable.shape_green_bg);
            if (!map.containsKey(item.imagePath)) {
                map.put(item.imagePath, item.imagePath);
                textcallback.onListen(Bimp.drr.size());
            }

        } else {
//            holder.selected.setVisibility(View.GONE);
            holder.text.setBackgroundColor(0x00000000);
            holder.isChecked.setChecked(false);
            hideFrame(holder);
        }
        return convertView;
    }

    private void check(int position, Holder holder, ImageItem item) {
        String path = dataList.get(position).imagePath;
        if (Bimp.drr.size() < maxPic && selectTotal < maxPic) {
            item.isSelected = !item.isSelected;
            if (item.isSelected) {
//                        holder.selected.setVisibility(View.VISIBLE);
//                        holder.selected
//                                .setImageResource(R.drawable.correct);
                holder.isChecked.setChecked(true);
                showFrame(holder);
                holder.text.setBackgroundResource(R.drawable.shape_green_bg);
                selectTotal++;
                map.put(path, path);
                Bimp.drr.add(path);
                if (textcallback != null) textcallback.onListen(Bimp.drr.size());
            } else {
//                        holder.selected.setVisibility(View.GONE);
                holder.text.setBackgroundColor(0x00000000);
                holder.isChecked.setChecked(false);
                hideFrame(holder);
                selectTotal--;
                map.remove(path);
                Bimp.drr.remove(path);
                if (textcallback != null) textcallback.onListen(Bimp.drr.size());
            }
        } else {
            if (item.isSelected == true) {
                item.isSelected = !item.isSelected;
//                        holder.selected.setVisibility(View.GONE);
                holder.isChecked.setChecked(false);
                hideFrame(holder);
                selectTotal--;
                map.remove(path);
                Bimp.drr.remove(path);
                if (textcallback != null) textcallback.onListen(Bimp.drr.size());
            } else {
                UIHelper.ToastMessage(act, "最多选" + maxPic + "张");
                holder.isChecked.setChecked(false);
                hideFrame(holder);
            }
        }
    }

    private void hideFrame(Holder holder) {
        holder.frame.setVisibility(View.GONE);
    }

    private void showFrame(Holder holder) {
        holder.frame.setVisibility(View.VISIBLE);
    }
}
