package com.yj.app.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yj.app.convert.Converters;
import com.yj.app.dao.LibraryDao;
import com.yj.app.dao.PlayListDao;
import com.yj.app.dao.RecordDao;
import com.yj.app.dao.StudentDao;
import com.yj.app.dao.UserDao;
import com.yj.app.domain.Library;
import com.yj.app.domain.Playlist;
import com.yj.app.domain.Record;
import com.yj.app.domain.Student;
import com.yj.app.domain.User;

@Database(entities = {User.class, Student.class, Library.class,
        Playlist.class, Record.class}, version = 4, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class MyRoomDatabase extends RoomDatabase {

    private static final String TAG = "MyRoomDatabase";

    private static MyRoomDatabase database;

    public abstract UserDao getUserDao();

    public abstract StudentDao getStudentDao();

    public abstract LibraryDao getLibraryDao();

    public abstract PlayListDao getPlayListDao();

    public abstract RecordDao getRecordDao();

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
                    builder.addMigrations(Migration_2_3);
                    builder.addMigrations(Migration_3_4);
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

    public static Migration Migration_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("create table if not EXISTS play_list (id INTEGER primary key autoincrement,play_name TEXT,uid INTEGER)");
        }
    };

    public static Migration Migration_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            System.err.println("Migration_3_4");
            database.execSQL("create table  if not EXISTS record (id INTEGER primary key autoincrement,name TEXT,record_time INTEGER)");
        }
    };
}
