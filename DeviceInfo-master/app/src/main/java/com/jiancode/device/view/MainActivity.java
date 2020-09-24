package com.jiancode.device.view;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jiancode.device.AppContext;
import com.jiancode.device.utils.PermissionUtils;
import com.jiancode.device.R;
import com.jiancode.device.utils.Utils;

import java.security.Permissions;

public class MainActivity extends AppCompatActivity {

    private TextView tvDeviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestSecurity();
        } else {
            loadData();
        }

    }

    private void initView() {
        tvDeviceInfo = findViewById(R.id.tv_deviceinfo);
    }

    private void loadData() {
        tvDeviceInfo.setText(Utils.getDeviceAllInfo(getApplicationContext()));
    }

    private void requestSecurity() {
        String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};

        PermissionUtils.getInstance().checkPermissions(this, permissions, permissionResult);
    }


    private PermissionUtils.IPermissionResult permissionResult = new PermissionUtils.IPermissionResult() {
        @Override
        public void passPermissons() {
            loadData();
        }

        @Override
        public void forbitPermissons() {
            finish();
            AppContext.getInstance().exit();
        }
    };
}
