package com.jiancode.device;

import android.app.Application;
import android.content.Context;
import android.os.Process;

/**
 * author：yangjian
 * email：yj@allong.net
 * data：2019-06-2723:01
 * desc：
 * version：1.0
 */
public class AppContext extends Application {

    private Context context;

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
    }

    public static AppContext getInstance() {
        return instance;
    }

    public void exit() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
