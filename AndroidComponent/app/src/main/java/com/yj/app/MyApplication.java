package com.yj.app;

import android.app.Application;

import androidx.room.Room;

import com.yj.app.db.MyRoomDatabase;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        getDatabase();
    }

    public static MyApplication getInstance() {
        return instance;
    }


    public MyRoomDatabase getDatabase() {
        return MyRoomDatabase.getDatabase(getApplicationContext());
    }
}
