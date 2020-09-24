package com.jiancode.binder2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

    private IBookManager binder = new BookManagerImpl();

    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) binder;
    }
}
