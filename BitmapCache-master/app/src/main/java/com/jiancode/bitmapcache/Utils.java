package com.jiancode.bitmapcache;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.Closeable;
import java.io.IOException;

/**
 * author：yangjian
 * email：yj@allong.net
 * data：2019-06-1323:49
 * desc：
 * version：1.0
 */
public class Utils {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static DisplayMetrics getMetrics(Context context) {
        Context mContext = context.getApplicationContext();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int dp2px(Context context, int val) {
        Context mContext = context.getApplicationContext();
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (val*scale+0.5f);
//        return displayMetrics.density * val + 1;
    }
}
