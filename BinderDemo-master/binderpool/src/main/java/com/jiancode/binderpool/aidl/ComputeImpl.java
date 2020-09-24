package com.jiancode.binderpool.aidl;


import android.os.IBinder;
import android.os.RemoteException;

import com.jiancode.binderpool.ICompute;

public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
