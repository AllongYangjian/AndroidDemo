package com.yj.actionbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.yj.actionbar.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        hideSystemBar();
//        hideNavigationBar();
//        hideNavigationBar2();
//        hideNavigationBar3();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);
    }

    /**
     * 隐藏系统状态栏上的图标，但是不会隐藏电池图标
     */
    private void lowerSystemBar() {
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        hideSystemBar();
    }

    /**
     * 将系统状态狼设置成透明
     * 这种情况下回导致系统栏先成白色，然后在消失，会存在界面布局重新调整的问题
     * 目前比较好的方式是在xml文件中指定属性，为全屏，这样就不会出现白框了，因为xml文件的加载优先级
     * 比较高
     */
    public void hideSystemBar() {

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 隐藏系统底部导航栏
     * 向后前倾斜模式
     */
    private void hideNavigationBar() {
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * 沉浸模式
     * 沉浸模式适用于用户将与屏幕进行大量互动的应用
     */
    private void hideNavigationBar2() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    /**
     * 粘性模式
     * 从边缘华滑动会显示状态栏和系统导航栏，
     * 如果一段时间不点击屏幕，则状态栏和导航栏会自动隐藏
     */
    private void hideNavigationBar3() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    /**
     * 全屏
     */
    private void fullScreen() {
        View view = getWindow().getDecorView();
        view.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                System.err.println(visibility);
            } else {
                System.err.println(visibility);
            }
        });
        //android 4.4 版本以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    private void showSystemUI() {
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            fullScreen();
        } else {
            showSystemUI();
        }
    }
}
