package com.jiancode.remoteview;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private Button btnSendNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    private void initView() {
        btnSendNotification = findViewById(R.id.btn_send_notification);
        btnSendNotification.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_send_notification:
                    sendNotification2();
                    break;
                default:

                    break;
            }
        }
    };

    private void sendNotification2() {
        Notification notification = new Notification();
        notification.icon = R.mipmap.ic_launcher_round;
        notification.tickerText = "this is a notification";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        Intent intent = new Intent(this, FirstActivity.class);
        notification.contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remote_view1);
        remoteViews.setTextViewText(R.id.tv_title, "remote view title");
        remoteViews.setTextViewText(R.id.btn_intent, "到第二个界面");
        notification.contentView = remoteViews;

        Intent intent2 = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.btn_intent, pendingIntent2);
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        manager.notify(2, notification);
    }
}
