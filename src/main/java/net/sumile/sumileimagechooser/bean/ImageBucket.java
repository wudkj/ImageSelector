package net.sumile.sumileimagechooser.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 一个目录的相册对象
 *
 * @author Administrator
 */
public class ImageBucket implements Serializable {
    public int count = 0;
    public String bucketName;
    public String path;  //本相册目录的 其中一个照片路径
    public List<ImageItem> imageList;

    public ImageBucket() {
    }

    public ImageBucket(int count, String bucketName, String path, List<ImageItem> imageList) {
        this.count = count;
        this.bucketName = bucketName;
        this.path = path;
        this.imageList = imageList;
    }
}
