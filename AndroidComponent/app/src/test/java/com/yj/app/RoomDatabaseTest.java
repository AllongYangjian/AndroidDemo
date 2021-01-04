package com.yj.app;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.yj.app.db.MyRoomDatabase;
import com.yj.app.domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
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
