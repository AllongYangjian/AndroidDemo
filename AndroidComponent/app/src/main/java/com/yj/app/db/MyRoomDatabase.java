package com.yj.app.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yj.app.dao.UserDao;
import com.yj.app.domain.User;

@Database(entities = {User.class}, version = 1, exportSchema = true)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static final String TAG = "MyRoomDatabase";

    private static MyRoomDatabase database;

    public abstract UserDao getUserDao();

    public static MyRoomDatabase getDatabase(Context context) {
        if (database == null) {
            synchronized (MyRoomDatabase.class) {
                if (database == null) {
                    RoomDatabase.Builder<MyRoomDatabase> builder = Room.databaseBuilder(context, MyRoomDatabase.class, "room-db");
                    builder.addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            System.err.println("database created");
                        }
                    });
                    database = builder.allowMainThreadQueries().build();
                }
            }
        }
        return database;
    }
}
