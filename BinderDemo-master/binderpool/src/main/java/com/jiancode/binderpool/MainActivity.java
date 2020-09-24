package com.jiancode.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jiancode.binderpool.aidl.BinderPool;
import com.jiancode.binderpool.aidl.ComputeImpl;
import com.jiancode.binderpool.aidl.SecurityCenterImpl;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button button;

    private CountDownLatch countDownLatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.bind_server);
//        countDownLatch = new CountDownLatch(1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startWork();
                    }
                }).start();
            }
        });

    }

    private void startWork() {

//        Intent intent = new Intent(this, RemoteService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        BinderPool binderPool = BinderPool.getInstance(MainActivity.this);
        IBinder binder = binderPool.queryBinder(BinderPool.BINDER_SECURITY);
        ISecurityCenter center = SecurityCenterImpl.asInterface(binder);
        String msg = "hello world";
        Log.e(TAG,msg+";");
        try {
            String password = center.encrypt(msg);
            String content = center.decrypt(password);
            Log.e(TAG,content);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        binder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute compute = ComputeImpl.asInterface(binder);
        try {
            int result  =compute.add(1,2);
            Log.e(TAG,result+"");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private IBinderPool mBindPool;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            mBindPool = IBinderPool.Stub.asInterface(service);
            try {
                mBindPool.asBinder().linkToDeath(deathRecipient, 0);


                IBinder binder = mBindPool.queryBinder(BinderPool.BINDER_SECURITY);
                ISecurityCenter center = SecurityCenterImpl.asInterface(binder);
                String msg = "hello world";
                Log.e(TAG, msg + ";");
                try {
                    String password = center.encrypt(msg);
                    String content = center.decrypt(password);
                    Log.e(TAG, content);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                binder = mBindPool.queryBinder(BinderPool.BINDER_COMPUTE);
                ICompute compute = ComputeImpl.asInterface(binder);
                try {
                    int result = compute.add(1, 2);
                    Log.e(TAG, result + "");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBindPool.asBinder().unlinkToDeath(this, 0);
            mBindPool = null;
            startWork();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
