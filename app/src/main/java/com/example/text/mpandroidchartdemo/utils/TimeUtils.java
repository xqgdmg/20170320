package com.example.text.mpandroidchartdemo.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具
 * Created by yangle on 2016/12/2.
 */
public class TimeUtils {

    public static String dateFormat_day = "HH:mm";
    public static String dateFormat_month = "MM-dd";

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param time 时间
     */
    public static String dateToString(long time) {
        return dateToString(time, "yyyy.MM.dd HH:mm");
    }

    /**
     * 时间转换成字符串,指定格式
     *
     * @param millis   时间
     * @param format 时间格式
     */
    public static String dateToString(long millis, String format) {
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

}
