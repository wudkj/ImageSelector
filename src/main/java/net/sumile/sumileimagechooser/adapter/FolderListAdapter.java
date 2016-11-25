package net.sumile.sumileimagechooser.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.sumile.sumileimagechooser.R;
import net.sumile.sumileimagechooser.bean.ImageBucket;
import net.sumile.sumileimagechooser.bean.ImageItem;
import net.sumile.sumileimagechooser.utils.BitmapCache;

import java.util.List;


/**
 * Created by sumile on 2016/11/19.
 */

public class FolderListAdapter extends BaseAdapter {
    private final BitmapCache cache;
    private Context mContext;
    private List<ImageBucket> mFolderList;

    public FolderListAdapter(Context context, List<ImageBucket> folderList) {
        this.mContext = context;
        this.mFolderList = folderList;
        cache = new BitmapCache();
    }

    @Override
    public int getCount() {
        return mFolderList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFolderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            holder = new MyViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_folderlist, null);
            holder.folderName = (TextView) convertView.findViewById(R.id.item_folderName);
            holder.count = (TextView) convertView.findViewById(R.id.count);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        ImageBucket data = mFolderList.get(position);
        ImageItem item = data.imageList.get(0);
        holder.imageView.setTag(item.imagePath);
        cache.displayBmp(holder.imageView, item.thumbnailPath, item.imagePath, callback);
        holder.folderName.setText(data.bucketName);
        holder.count.setText("共" + data.imageList.size() + "张");
        return convertView;
    }

    BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, Bitmap bitmap, Object... params) {
            if (imageView != null && bitmap != null) {
                String url = (String) params[0];
                if (url != null && url.equals(imageView.getTag())) {
                    (imageView).setImageBitmap(bitmap);

                } else {
                }
            } else {
            }
        }
    };

    class MyViewHolder {
        public TextView folderName;
        public TextView count;
        public ImageView imageView;
    }
}
