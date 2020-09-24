package com.jiancode.binderpool.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.jiancode.binderpool.IBinderPool;
import com.jiancode.binderpool.RemoteService;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    private static final String TAG = BinderPool.class.getSimpleName();

    private CountDownLatch mCountDownLatch;

    public static final int BINDER_NONE = -1;
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY = 1;

    private Context mContext;
    private IBinderPool iBinderPool;
    private static volatile BinderPool instance;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        mCountDownLatch = new CountDownLatch(1);
        connectBindServer();
    }

    public static BinderPool getInstance(Context context) {
        if (instance == null) {
            synchronized (BinderPool.class) {
                if (instance == null) {
                    instance =  new BinderPool(context);
                }
            }
        }
        return instance;
    }

    public IBinder queryBinder(int binderCode) {

        IBinder binder = null;
        if (iBinderPool != null) {
            try {
                binder = iBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

    private synchronized void connectBindServer() {
        Log.e(TAG,"connectBindServer");
        Intent intent = new Intent(mContext, RemoteService.class);
        mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"onServiceConnected");
            iBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                iBinderPool.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                Log.e(TAG,e.getMessage());
            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG,"binderDied");
            iBinderPool.asBinder().unlinkToDeath(this, 0);
            iBinderPool = null;
            connectBindServer();
        }
    };

    public static class BindPoolImpl extends IBinderPool.Stub {

        public BindPoolImpl(){
            super();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder iBinder = null;
            switch (binderCode) {
                case BINDER_COMPUTE:
                    iBinder = new ComputeImpl();
                    break;
                case BINDER_SECURITY:
                    iBinder = new SecurityCenterImpl();
                    break;
                default:
                    break;
            }
            return iBinder;
        }
    }


}
