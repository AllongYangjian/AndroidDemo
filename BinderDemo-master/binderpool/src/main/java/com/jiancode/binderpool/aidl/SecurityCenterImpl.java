package com.jiancode.binderpool.aidl;

import android.os.IBinder;
import android.os.RemoteException;

import com.jiancode.binderpool.ISecurityCenter;

public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] array = content.toCharArray();
        for(int x = 0;x<array.length;x++){
            array[x] ^= SECRET_CODE;
        }
        return new String(array);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }

}
