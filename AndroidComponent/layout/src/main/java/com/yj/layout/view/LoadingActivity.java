package com.yj.layout.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.yj.layout.R;

public class LoadingActivity extends AppCompatActivity {

    private String[] names;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        names = getResources().getStringArray(R.array.channel_name);
        createNotificationChannel();
        createBackStackActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createBackStackActivity();
                        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                        startActivity(intent);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();

                    }
                });
            }
        }).start();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;

            String[] desc = getResources().getStringArray(R.array.channel_desc);
            int importance = NotificationManager.IMPORTANCE_LOW;
            for (int x = 0; x < names.length; x++) {
                NotificationChannel channel = new NotificationChannel(names[x], names[x], importance + x);
                channel.setDescription(desc[x]);
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void createBackStackActivity(){
        NotificationManagerCompat compat = NotificationManagerCompat.from(this);

        Intent intent = new Intent(this,NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pi = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,names[1]);
        Notification notification = builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText("返回堆栈式通知")
                .setContentTitle("通知消息")
                .setContentIntent(pi)
                .build();

        compat.notify(100,notification);

    }

}
