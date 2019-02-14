package com.jenking.xiaoyunhui.tools;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import com.jenking.xiaoyunhui.app.MyApp;
import com.jenking.xiaoyunhui.presenters.AppPresenter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

//全局异常拦截器，用于
public class GlobalExcaption implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "UniException";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // UniException实例
    private static GlobalExcaption INSTANCE = new GlobalExcaption();

    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    /**
     * 保证只有一个实例
     */
    private GlobalExcaption() {
    }

    /**
     * 获取UniException实例 ,单例模式
     */
    public static GlobalExcaption getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalExcaption();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    /**
     * 初始化
     */
    public void init() {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该本类为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e("error : ", e.getMessage());
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
//                Toast.makeText(App.appOS, "很抱歉,程序出现异常,即将退出..", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(MyApp.appOS);
        // 保存日志文件

        StringBuffer log_value = new StringBuffer();
        StringBuffer log_pre = new StringBuffer();
        try {
            log_value.append(">>>>时间：" + StringUtil.getStrTime(StringUtil.getTime(),"yyyy年MM月dd日 HH:mm:ss")+"\n");
            log_value.append("信息：" + ex.getMessage() + "\r\n\n");
            for (int i = 0; i < ex.getStackTrace().length; i++) {
                log_value.append("[第"+(i+1)+"条记录"+"---------------------]\r\n");
                log_value.append("文件：" + ex.getStackTrace()[i].getFileName() + "\r\n");
                log_value.append("类名：" + ex.getStackTrace()[i].getClassName() + "\r\n");
                log_value.append("行数：" + ex.getStackTrace()[i].getLineNumber() + "\r\n");
                log_value.append("方法：" + ex.getStackTrace()[i].getMethodName() + "\r\n\r\n");
            }

            log_pre.append("已崩溃");
            log_value.append("--------------------------------\r\n--------------------------------\r\n--------------------------------\r\n");

//            FileHelper.save(App.getApplication(),FileHelper.GLOBAL_EXCEPTION_FILE,log_value.toString()+"");
//            FileHelper.save(App.getApplication(),FileHelper.GLOBAL_EXCEPTION_FILE_PRE,log_pre.toString()+"");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PackageInfo info = DeviceUtil.getAppInfo(MyApp.appOS);
        AppPresenter appPresenter = new AppPresenter(MyApp.appOS);
        Map<String,String> params = new HashMap<>();
        params.put("create_time",StringUtil.getStrTime(StringUtil.getTime(),"yyyy年MM月dd日 HH:mm:ss"));
        params.put("device","123333");
        params.put("version_code",info.versionCode+"");
        params.put("version_name",info.versionName+"");
        params.put("package_name",info.packageName+"");
        params.put("brand",android.os.Build.BRAND);
        params.put("model", android.os.Build.MODEL);
        params.put("sdk",android.os.Build.VERSION.SDK );
        params.put("device_release",android.os.Build.VERSION.RELEASE);
        params.put("operate_error",log_value.toString());
        appPresenter.addError(params);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
//            Log.e("an error occured when collect package info", e.getMessage());
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.e(field.getName() + " : ", field.get(null) + "");
            } catch (Exception e) {
//                Log.e("an error occured when collect crash info", e.getMessage());
            }
        }
    }
}