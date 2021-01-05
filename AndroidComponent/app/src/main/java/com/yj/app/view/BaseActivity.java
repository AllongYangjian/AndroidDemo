package com.yj.app.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yj.app.db.MyRoomDatabase;

public
abstract class BaseActivity extends AppCompatActivity {
    protected MyRoomDatabase appDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = MyRoomDatabase.getDatabase(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appDatabase.close();
    }
}
