package com.yj.intent.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 自定义广播
 */
public class MyBroadCast extends BroadcastReceiver {

    private final String TAG = "MyBroadCast";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals("com.yj.intent.Test")){
            String msg = intent.getStringExtra("key");
            Log.e(TAG,msg);
        }
    }
}
