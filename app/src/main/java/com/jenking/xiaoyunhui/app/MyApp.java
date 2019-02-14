package com.jenking.xiaoyunhui.app;

import android.app.Application;

import com.jenking.xiaoyunhui.tools.GlobalExcaption;

import org.xutils.x;

public class MyApp extends Application {
    public static MyApp appOS;
    @Override
    public void onCreate() {
        super.onCreate();
        appOS = this;
        GlobalExcaption.getInstance().init();//初始化全局异常拦截
    }
}
