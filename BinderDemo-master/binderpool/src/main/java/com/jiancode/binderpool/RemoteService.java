package com.jiancode.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.jiancode.binderpool.aidl.BinderPool;

public class RemoteService extends Service {

    private Binder binderPool = new BinderPool.BindPoolImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("RemoteService","onCreate");
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.e("RemoteService","onBind");
        return binderPool;
    }
}
