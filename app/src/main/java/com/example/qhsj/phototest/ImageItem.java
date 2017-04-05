package com.example.qhsj.phototest;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 *@author: zhangxin
 *@time: 2016/7/22 17:57
 *@description: 图片信息
 *
 */
public class ImageItem implements Serializable{

    private String imageId;
    private String thumbnailPath;
    private String imagePath;
    private String imageNetPath;
    private Bitmap bitmap;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageNetPath() {
        return imageNetPath;
    }

    public void setImageNetPath(String imageNetPath) {
        this.imageNetPath = imageNetPath;
    }

    public Bitmap getBitmap() {
        if (bitmap == null) {
            try {
                bitmap = BitmapHelp.revitionImageSize(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
