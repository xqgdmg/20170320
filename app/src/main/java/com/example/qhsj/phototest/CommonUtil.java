package com.example.qhsj.phototest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: star
 * @time: 2016/7/9 17:54
 * @description: 公共方法类
 */
public class CommonUtil {

    final private static CommonUtil _instance = new CommonUtil();

    private Context context;

    static public CommonUtil getInstance() {
        return _instance;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 防止图片oom
     *
     * @return
     */
    public static Bitmap getBitmapForImgResources(Context mContext, int imgId) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = mContext.getResources().openRawResource(imgId);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inSampleSize = 1;
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 防止图片oom
     *
     * @return
     */
    public static Bitmap getBitmapForInputStream(String sourcePath) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;
            //这里一定要将其设置回false，因为之前我们将其设置成了true
            //设置inJustDecodeBounds为true后，decodeFile并不分配空间，即，
            // BitmapFactory解码出来的Bitmap为Null,但可计算出原始图片的长度和宽度
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(sourcePath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 字符串强转成数字
     *
     * @param data
     * @return
     */
    public int str2Int(String data) {
        int num = 0;
        if (!TextUtils.isEmpty(data)) {
            try {
                num = Integer.valueOf(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    /**
     * 转化为时间格式
     *
     * @param millis
     * @return
     */
    public String getStringFromLong(long millis, String format) {
        String time = "";
        if (TextUtils.isEmpty(format)) {
            return time;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date dt = new Date(millis);
            time = sdf.format(dt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 限制输入两位小数
     *
     * @param et
     * @param s
     */
    public void setInput(EditText et, CharSequence s) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                et.setText(s);
                et.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            et.setText(s);
            et.setSelection(2);
        }
        if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                String str = s.toString().replaceAll("0", "");
                if (TextUtils.isEmpty(str)) {
                    str = str + "0";
                    et.setText(str);
                } else {
                    et.setText(str);
                }
                et.setSelection(str.length());
            }
        }
    }

    /**
     * 最多保留小数点后六位
     *
     * @param numStr
     * @return
     */
    public String numPointFourFLOOR(String numStr) {
        try {
            double num = Double.parseDouble(numStr);
            DecimalFormat df = new DecimalFormat("0.####");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 最多保留小数点后2位
     *
     * @param numStr
     * @return
     */
    public String numPointTwo(String numStr) {
        try {
            double num = Double.parseDouble(numStr);
            DecimalFormat df = new DecimalFormat("0.##");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 最多保留小数点后2位
     *
     * @param numStr
     * @return
     */
    public String numPointTwos(String numStr) {
        try {
            double num = Double.parseDouble(numStr);
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 最多保留小数点后2位
     *
     * @param num
     * @return
     */
    public String numPointTwos(double num) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 最多保留小数点后2位
     *
     * @param num
     * @return
     */
    public String numPointTwoFromDouble(double num) {
        try {
            DecimalFormat df = new DecimalFormat("0.##");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 最多保留小数点后4位
     *
     * @param numStr
     * @return
     */
    public String numPointFour(String numStr) {
        try {
            double num = Double.parseDouble(numStr);
            DecimalFormat df = new DecimalFormat("0.0000");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 最多保留小数点后4位
     *
     * @param numStr
     * @return
     */
    public String numPointFours(double numStr) {
        try {
            DecimalFormat df = new DecimalFormat("0.0000");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(numStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public String numPoint(String numStr) {
        try {
            double num = Double.parseDouble(numStr);
            DecimalFormat df = new DecimalFormat("0.#");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * 去除科学计数法
     *
     * @param val
     * @return
     */
    public static String NumberFormat(double val) {
        try {
            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            return nf.format(val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * @param val
     * @return String
     * @describe 格式月，日等，如果为 1,2这种，格式化为01,02
     * @method formatMonthOrDay
     */
    public String formatMonthOrDay(int val) {
        String mStr = val + "";
        if (mStr.length() < 2) {
            mStr = "0" + val;
        }
        return mStr;
    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public boolean isEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        Pattern p = Pattern.compile(str);

        Matcher m = p.matcher(email);

        return m.matches();

    }

    /**
     * 获取string资源
     *
     * @param resId
     * @return
     */
    public String getResourcesText(int resId) {
        String resText = context.getResources().getString(resId);
        if (!TextUtils.isEmpty(resText)) {
            return resText;
        }
        return " ";
    }


    /**
     * 获取应用的版本号
     *
     * @return
     */
    public int getLocalVersionCode() {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取应用的版本名称
     *
     * @return
     */
    public String getLocalVersionName() {
        String localVersionName = "1.0.0";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            localVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersionName;
    }


    //压缩保存指定地址图片
    public static void saveAndCompressImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        Bitmap bitmap1 = compressImage(bitmap);//压缩好比例大小后再进行质量压缩
        saveBitmap(srcPath, bitmap1);

    }

    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 保存方法
     */
    private static void saveBitmap(String path, Bitmap bp) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    /**
     * 将图片压缩并转换成jpg格式
     *
     * @param primaryFilePath 原始的文件路径
     * @param jpgFilePath     转换后的jpg文件保存路径
     */
    public static void convertToJpg(String primaryFilePath, String jpgFilePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(primaryFilePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(primaryFilePath, options);

//		Bitmap bitmap = BitmapFactory.decodeFile(pngFilePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(jpgFilePath);
            bos = new BufferedOutputStream(fos);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 计算压缩图片的倍数
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }





}
