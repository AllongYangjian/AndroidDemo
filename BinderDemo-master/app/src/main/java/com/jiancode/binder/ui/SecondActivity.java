package com.jiancode.binder.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.jiancode.binder.R;
import com.jiancode.binder.bean.Book;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = SecondActivity.class.getSimpleName();
    private Button btnServer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btnServer = findViewById(R.id.btn_server);
        getData();
    }

    private void getData(){
        Bundle bundle = getIntent().getExtras();
        Book book = bundle.getParcelable("book");
        Log.e(TAG,book.toString());
    }
}
