package com.allong.databases;

import android.app.Application;

import com.allong.databases.db.AppDatabase;

public
class App extends Application {
    private static App instance;

    public static  App getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(getApplicationContext());
    }
}
