package com.yj.layout.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yj.layout.R;
import com.yj.layout.databinding.ActivityMainBinding;
import com.yj.layout.widget.TimerPickerDialog;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initView();
    }

    private void initView() {
        binding.btnMotionLayout.setOnClickListener(onClickListener);
        binding.btnCardView.setOnClickListener(onClickListener);
        binding.btnCustomEdittext.setOnClickListener(onClickListener);
        binding.btnSpan.setOnClickListener(onClickListener);
        binding.btnPicker.setOnClickListener(onClickListener);
        binding.btnNotification.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btn_motion_layout:
                gotoActivity(MotionLayoutActivity.class);
                break;
            case R.id.btn_card_view:
                gotoActivity(CardViewActivity.class);
                break;
            case R.id.btn_custom_edittext:
                gotoActivity(CustomEditTextActivity.class);
                break;
            case R.id.btn_span:
                gotoActivity(SpanTextActivity.class);
                break;
            case R.id.btn_picker:
                showDialog();
                break;
            case R.id.btn_notification:
                gotoActivity(NotificationActivity.class);
                break;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }


    private void showDialog() {
        TimerPickerDialog dialog = new TimerPickerDialog();
        dialog.show(getSupportFragmentManager(), "TimerPicker");
    }

    private void gotoActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}
