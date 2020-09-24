package com.jiancode.remoteviewserver;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;

    private static final String ACTION = "com.allong.yj";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast();
            }
        });
    }

    private void sendBroadcast() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);

        remoteViews.setTextViewText(R.id.tv_title, "msg from process:" + Process.myPid());
        remoteViews.setTextViewText(R.id.tv_sub_title, "这是一条来自其他程序的通知");

        PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0,
                new Intent(this, DemoActivity1.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0,
                new Intent(this, DemoActivity2.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_logo, pendingIntent1);
        remoteViews.setOnClickPendingIntent(R.id.tv_title, pendingIntent2);

        Intent intent = new Intent(ACTION);
        intent.putExtra("remoteview", remoteViews);
        sendBroadcast(intent);
    }
}
