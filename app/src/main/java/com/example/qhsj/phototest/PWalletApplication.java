package com.example.qhsj.phototest;

import android.app.Application;

import com.example.qhsj.utils.LocalImageHelper;

/**
 * Created by Chris on 2017/4/5.
 */
public class PWalletApplication extends Application {
    private static PWalletApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //本地图片辅助类初始化
        LocalImageHelper.init(this);
    }

    public static synchronized PWalletApplication getInstance() {
        return sInstance;
    }
}
