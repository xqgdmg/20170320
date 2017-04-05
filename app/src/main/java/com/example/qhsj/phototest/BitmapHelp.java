package com.example.qhsj.phototest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 *@author: zhangxin
 *@time: 2016/7/22 17:57
 *@description: 选择图片和拍照图片帮助类
 *
 */
public class BitmapHelp {
    public static int max = 0;

    public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   //选择的图片的临时列表

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 检查图片是否已经添加
     * @param path
     * @return
     */
    public static boolean isImageSelect(String path) {
        boolean isSelected = false;
        if (tempSelectBitmap.size() > 0 && !tempSelectBitmap.isEmpty()) {
            for (ImageItem imageItem : tempSelectBitmap) {
                if (path.equalsIgnoreCase(imageItem.getImagePath())) {
                    return true;
                }
            }
        }
        return isSelected;
    }
}
