package com.allong.databases.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.allong.databases.dao.UserDao;
import com.allong.databases.entity.User;

@Database(entities = {User.class},exportSchema = true,version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();


    public static  AppDatabase instance;


    public static AppDatabase getInstance(Context context){
        if(instance ==null){
            synchronized (AppDatabase.class){
                if(instance ==null){
                    instance = Room.databaseBuilder(context,AppDatabase.class,"room-db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }



}
