package com.allong.coolweather;

import android.content.Context;

/**
 * @author yj on  2018/3/1 09:52
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class App extends org.litepal.LitePalApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
