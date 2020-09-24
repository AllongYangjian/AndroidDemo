package com.allong.imms.view.serial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.allong.imms.view.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JNIInterface.init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        JNIInterface.uninit();
    }
}
