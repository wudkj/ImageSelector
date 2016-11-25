package net.sumile.sumileimagechooser.util;

import android.content.Context;

import net.sumile.sumileimagechooser.bean.ImageBucket;
import net.sumile.sumileimagechooser.utils.AlbumHelper;

import java.util.List;

/**
 * Created by sumile on 2016/11/19.
 */

public class ImageBucketProvider {
    public static List<ImageBucket> getImageBuckets(Context context) {
        AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(context);
        List<ImageBucket> contentList = helper.getImagesBucketList(true);
        helper.clearHelper();
        return contentList;
    }
}
