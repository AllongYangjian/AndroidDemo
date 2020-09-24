package com.allong.imms.view.serial;

/**
 * author：yangjian
 * email：yj@allong.net
 * data：2019-07-1710:29
 * desc：
 * version：1.0
 */
public class JNIInterface {
    public static native  int init();

    public static native int uninit();

    public static native int start(String path,int brate);

    public static native int stop();

    public static native int FreeData(byte[] data);

    public static native byte[] GetMesssage(int len);

    public static native boolean sendDataToDev(byte[] data,int len);

    static{
        System.loadLibrary("native-lib");
    }

}
