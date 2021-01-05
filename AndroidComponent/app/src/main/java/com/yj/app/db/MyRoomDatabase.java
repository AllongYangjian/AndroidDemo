package com.yj.app.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yj.app.dao.LibraryDao;
import com.yj.app.dao.PlayListDao;
import com.yj.app.dao.StudentDao;
import com.yj.app.dao.UserDao;
import com.yj.app.domain.Library;
import com.yj.app.domain.Playlist;
import com.yj.app.domain.Student;
import com.yj.app.domain.User;

@Database(entities = {User.class, Student.class, Library.class, Playlist.class}, version = 2, exportSchema = true)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static final String TAG = "MyRoomDatabase";

    private static MyRoomDatabase database;

    public abstract UserDao getUserDao();

    public abstract StudentDao getStudentDao();

    public abstract LibraryDao getLibraryDao();

    public abstract PlayListDao getPlayListDao();

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
                    builder.addMigrations(Migration_1_2);
                    database = builder.allowMainThreadQueries().build();
                }
            }
        }
        return database;
    }

    public static Migration Migration_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("create table if not EXISTS library (id INTEGER primary key autoincrement,name TEXT,sid INTEGER)");
        }
    };
}
