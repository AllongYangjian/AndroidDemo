package com.yj.bubble;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.yj.bubble.common.Const;

public class AppHelper extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initNotification();
    }

    private void initNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            int importance = NotificationManager.IMPORTANCE_LOW;
            String[] desc = Const.CHANNEL_ID;
            for (int i = 0; i < desc.length; i++) {
                NotificationChannel channel = new NotificationChannel(desc[i], desc[i], importance + 1);
                channel.setDescription(desc[i]);
                manager.createNotificationChannel(channel);
            }
        }

    }
}
