package com.yj.app.view.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityShowHideBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

public class ShowHideActivity extends AppCompatActivity {

    private ActivityShowHideBinding binding;
    private int shortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_hide);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
//        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        shortAnimationDuration = 2000;
        //淡入 视图，透明性设置为0
        binding.content.setVisibility(View.VISIBLE);
        binding.content.setAlpha(0f);

        binding.content.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        //淡出视图
        binding.loadingSpinner.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.loadingSpinner.setVisibility(View.GONE);
                    }
                });
    }
}
