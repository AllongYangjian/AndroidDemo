package com.jiancode.device.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

/**
 * author：yangjian
 * email：yj@allong.net
 * data：2019-06-2723:06
 * desc：
 * version：1.0
 */
public class Utils {

    public static String getDeviceAllInfo(Context context) {

        return "1. IMEI:\n\t\t" + getIMEI(context)

                + "\n\n2. 设备宽度:\n\t\t" + getDeviceWidth(context)

                + "\n\n3. 设备高度:\n\t\t" + getDeviceHeight(context)

                + "\n\n4. 屏幕像素点:\n\t\t" + getDensity(context)

                + "\n\n5. 像素密度:\n\t\t" + getDpi(context)

                + "\n\n6. 是否有内置SD卡:\n\t\t" + SdcardUtils.isSDCardMount()

                + "\n\n7. RAM 信息:\n\t\t" + SdcardUtils.getRAMInfo(context)

                + "\n\n8. 内部存储信息\n\t\t" + SdcardUtils.getStorageInfo(context, 0)

                + "\n\n9. SD卡 信息:\n\t\t" + SdcardUtils.getStorageInfo(context, 1)

                + "\n\n10. 是否联网:\n\t\t" + Utils.isNetworkConnected(context)

                + "\n\n11. 网络类型:\n\t\t" + Utils.GetNetworkType(context)

                + "\n\n12. 系统默认语言:\n\t\t" + getDeviceDefaultLanguage()

                + "\n\n13. 硬件序列号(设备名):\n\t\t" + android.os.Build.SERIAL

                + "\n\n14. 手机型号:\n\t\t" + android.os.Build.MODEL

                + "\n\n15. 生产厂商:\n\t\t" + android.os.Build.MANUFACTURER

                + "\n\n16. 手机Fingerprint标识:\n\t\t" + android.os.Build.FINGERPRINT

                + "\n\n17. MAC地址:\n\t\t" + getMac(context)

                + "\n\n18. Android 版本:\n\t\t" + android.os.Build.VERSION.RELEASE

                + "\n\n19. Android SDK版本:\n\t\t" + android.os.Build.VERSION.SDK_INT

                + "\n\n20. 安全patch 时间:\n\t\t" + android.os.Build.VERSION.SECURITY_PATCH

                + "\n\n21. 发布时间:\n\t\t" + Utils.Utc2Local(android.os.Build.TIME)

                + "\n\n22. 版本类型:\n\t\t" + android.os.Build.TYPE

                + "\n\n23. 用户名:\n\t\t" + android.os.Build.USER

                + "\n\n24. 产品名:\n\t\t" + android.os.Build.PRODUCT

                + "\n\n25. ID:\n\t\t" + android.os.Build.ID

                + "\n\n26. 显示ID:\n\t\t" + android.os.Build.DISPLAY

                + "\n\n27. 硬件名:\n\t\t" + android.os.Build.HARDWARE

                + "\n\n28. 产品名:\n\t\t" + android.os.Build.DEVICE

                + "\n\n29. Bootloader:\n\t\t" + android.os.Build.BOOTLOADER

                + "\n\n30. 主板名:\n\t\t" + android.os.Build.BOARD

                + "\n\n31. CodeName:\n\t\t" + android.os.Build.VERSION.CODENAME

                + "\n\n32. 语言支持:\n\t\t" + "太多，不显示😊😊😊！！！";

    }

    /**
     * 获取mac地址（适配所有Android版本）
     *
     * @return
     */
    static String getMac(Context context) {
        String mac = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddress();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }

    /**
     * Android 6.0 之前（不包括6.0）获取mac地址
     * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     *
     * @param context * @return
     */
    public static String getMacDefault(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    /**
     * Android 6.0-Android 7.0 获取mac地址
     */
    public static String getMacAddress() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            while (null != str) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();//去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }

        return macSerial;
    }

    /**
     * Android 7.0之后获取Mac地址
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     *
     * @return
     */
    public static String getMacFromHardware() {
        try {
            Enumeration<NetworkInterface> all = NetworkInterface.getNetworkInterfaces();
            while (all.hasMoreElements()) {
                NetworkInterface nif = all.nextElement();
                if (!nif.getName().equalsIgnoreCase("wlan0"))
                    continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null)
                    return "";
                StringBuilder res1 = new StringBuilder();
                for (Byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (!TextUtils.isEmpty(res1)) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 获取设备宽度（px）
     */
    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     */
    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取设备的唯一标识， 需要 “android.permission.READ_Phone_STATE”权限
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "PERMISSION_DENIED";
        } else {
            String deviceId = tm.getDeviceId();
            if (deviceId == null) {
                return "UnKnown";
            } else {
                return deviceId;
            }
        }
    }

    /**
     * 获取当前手机系统语言。
     */
    public static String getDeviceDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String GetNetworkType(Context context) {
        int type = -1;
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if (wifiNetworkInfo != null && wifiNetworkInfo.isAvailable()) {
            type = ConnectivityManager.TYPE_WIFI;
        } else if (mobileNetworkInfo != null && mobileNetworkInfo.isAvailable()) {
            type = ConnectivityManager.TYPE_MOBILE;
        } else {
            type = -1;
        }
        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
                return "WIFI";
            case ConnectivityManager.TYPE_MOBILE:
                return "MOBILE";
            default:
                return "Unknown";
        }
    }

    public static String Utc2Local(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(time));
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     */
    public static String getDeviceSupportLanguage() {
        Log.e("wangjie", "Local:" + Locale.GERMAN);
        Log.e("wangjie", "Local:" + Locale.ENGLISH);
        Log.e("wangjie", "Local:" + Locale.US);
        Log.e("wangjie", "Local:" + Locale.CHINESE);
        Log.e("wangjie", "Local:" + Locale.TAIWAN);
        Log.e("wangjie", "Local:" + Locale.FRANCE);
        Log.e("wangjie", "Local:" + Locale.FRENCH);
        Log.e("wangjie", "Local:" + Locale.GERMANY);
        Log.e("wangjie", "Local:" + Locale.ITALIAN);
        Log.e("wangjie", "Local:" + Locale.JAPAN);
        Log.e("wangjie", "Local:" + Locale.JAPANESE);
        Locale[] locales = Locale.getAvailableLocales();
        StringBuilder sb = new StringBuilder();
        for (Locale locale : locales) {
            sb.append(locale.toString()).append("\n");
        }
        return sb.toString();
    }


}
