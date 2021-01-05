package com.yj.app;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import com.yj.app.db.MyRoomDatabase;
import com.yj.app.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(application = MyApplication.class)
public class RoomDatabaseTest {

    private MyRoomDatabase database;

    @Before
    public void init(){
        Context context = InstrumentationRegistry.getTargetContext();
        database = MyRoomDatabase.getDatabase(context);
    }

    @Test
    public void insert(){
        User user = new User();
        user.firstName = "yang";
        user.lastName = "jian";
        database.getUserDao().insertAll(user);
    }
}
