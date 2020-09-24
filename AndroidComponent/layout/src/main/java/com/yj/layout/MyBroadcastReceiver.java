package com.yj.layout;

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.yj.layout.common.Constant;

/**
 * 广播接收者
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = "MyBroadcastReceiver";

    private NotificationManagerCompat managerCompat;

    public MyBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        managerCompat = NotificationManagerCompat.from(context);
        String action = intent.getAction();
        int id = intent.getIntExtra("id", -1);
        Log.e(TAG,id+"");
        if (action.equals(Constant.ACTION_1)) {
            Log.e(TAG, Constant.ACTION_1);
        } else if (action.equals(Constant.ACTION_2)) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                Bundle bundle = RemoteInput.getResultsFromIntent(intent);
                if (bundle != null) {
                    String str = bundle.getCharSequence(Constant.ACTION_REPLY_KEY).toString();
                    Log.e(TAG, str);
                }
            }
        }
        managerCompat.cancel(id);
    }
}
