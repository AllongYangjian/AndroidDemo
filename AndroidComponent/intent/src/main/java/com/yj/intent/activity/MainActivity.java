package com.yj.intent.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.yj.intent.R;
import com.yj.intent.broadcast.MyBroadCast;
import com.yj.intent.databinding.ActivityMainBinding;
import com.yj.intent.service.MyService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MyBroadCast myBroadCast;

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        myBroadCast = new MyBroadCast();
        initBroadCastReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.btnStartActivity.setOnClickListener(onClickListener);
        binding.btnStartBroadcast.setOnClickListener(onClickListener);
        binding.btnStartService.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_start_activity:
                requestAppPermission();
                break;
            case R.id.btn_start_service:
                startService();
                break;
            case R.id.btn_start_broadcast:
                sendMyBroadcast();
                break;
        }
    };

    /**
     * 监听对话框消失事件
     */
    private DialogInterface.OnDismissListener onDismissListener = dialog -> {

    };

    /**
     * 界面跳转
     */
    private void gotoActivity() {
        Intent intent = new Intent(this, CommonIntentActivity.class);
        startActivity(intent);
    }

    private void startService() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    private void sendMyBroadcast() {
        Intent intent = new Intent(this, MyBroadCast.class);
        intent.setAction("com.yj.intent.Test");
        intent.putExtra("key", "this is my broadcast");
        sendBroadcast(intent);
    }

    private void initBroadCastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yj.intent.Test");
        registerReceiver(myBroadCast, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        unregisterReceiver(myBroadCast);
    }

    private void dominantIntent() {

    }

    private void pendingIntent() {
        Intent intent = new Intent(this, MyBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(this, getPackageName())
                .setContentIntent(pendingIntent)
                .setContentInfo("sb")
                .build();
        manager.notify(1, notification);
    }

    /**
     * 请求应用权限
     */
    private void requestAppPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            List<String> permissionList = new ArrayList<>();
            for (String str : permissions) {
                if (ActivityCompat.checkSelfPermission(this, str) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(str);
                }
            }
            if (permissionList.size() > 0) {
                ActivityCompat.requestPermissions(this, permissions, 200);
            } else {
                gotoActivity();
            }
        } else {
            gotoActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            boolean granted = true;
            for (int x = 0; x < grantResults.length; x++) {
                if (grantResults[x] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, permissions[x] + " 权限未通过", Toast.LENGTH_LONG).show();
                    granted = false;
                    break;
                }
            }
            if (granted) {
                gotoActivity();
            }
        }
    }
}
