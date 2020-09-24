package com.allong.coolweather.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author yj on  2018/3/1 14:19
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class HttpUtil {
    /**
     * 发送Http请求
     * @param address 地址
     * @param callback 回调接口
     */
    public static void sendOkHttpRequest(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }


}
