package com.yj.app;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static MyApplication getInstance() {
        return instance;
    }


}
