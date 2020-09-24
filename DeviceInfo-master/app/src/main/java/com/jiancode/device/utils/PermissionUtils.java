package com.jiancode.device.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请工具类
 */
public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();

    private final int mRequestCode = 200;//权限请求码

    public static boolean showSystemSetting = true;

    private static PermissionUtils instance;

    private AlertDialog mPermissionDialog;

    private PermissionUtils(){

    }

    private IPermissionResult iPermissionResult;

    public static PermissionUtils getInstance(){
        if(instance == null){
            synchronized (PermissionUtils.class){
                if(instance == null){
                    instance = new PermissionUtils();
                }
            }
        }
        return instance;
    }

    public void checkPermissions(Activity context, String[] permissions, @NonNull IPermissionResult result){
        this.iPermissionResult = result;
        if(Build.VERSION.SDK_INT<23){
            iPermissionResult.passPermissons();
            return;
        }

        List<String> mPermissons = new ArrayList<>();
        for(int x = 0;x<permissions.length;x++){
            if(ContextCompat.checkSelfPermission(context,permissions[x])!= PackageManager.PERMISSION_GRANTED){
                mPermissons.add(permissions[x]);
            }
        }
        if(mPermissons.size()>0){
            ActivityCompat.requestPermissions(context,permissions,mRequestCode);
        }else {
            result.passPermissons();
        }
    }

    public void onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;
        if(requestCode ==mRequestCode){
            for(int x = 0;x<permissions.length;x++){
                if(grantResults[x] == -1){
                    hasPermissionDismiss= true;
                }
            }
        }
        if(hasPermissionDismiss){
            if(showSystemSetting){
                showSystemPermissionSettingDialog(context);
            }else {
                iPermissionResult.forbitPermissons();
            }
        }else{
            iPermissionResult.passPermissons();
        }
    }

    /**
     * 显示系统权限设置对话框
     * @param context
     */
    private void showSystemPermissionSettingDialog(final Activity context) {
        final String mPackName = context.getPackageName();
        if(mPermissionDialog == null){
            mPermissionDialog = new AlertDialog.Builder(context)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            Uri packageUri = Uri.parse("package:"+mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,packageUri);
                            context.startActivity(intent);
                            context.finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            iPermissionResult.forbitPermissons();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    private void cancelPermissionDialog(){
        if(mPermissionDialog!=null){
            mPermissionDialog.cancel();
            mPermissionDialog = null;
        }
    }


    /**
     * 动态权限结果返回接口
     */
    public interface IPermissionResult{
        void passPermissons();

        void forbitPermissons();
    }

}
