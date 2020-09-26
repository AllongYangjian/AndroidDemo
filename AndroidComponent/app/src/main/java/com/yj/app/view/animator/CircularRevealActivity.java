package com.yj.app.view.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityCircularRevealBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

public class CircularRevealActivity extends AppCompatActivity {

    private ActivityCircularRevealBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_circular_reveal);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.btnShow.setOnClickListener(v -> initView());
    }

    private void initView() {

        int cx = binding.ivBg.getWidth() / 2;
        int cy = binding.ivBg.getHeight() / 2;
        float finalRadius = (float) Math.hypot(cx, cy);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (binding.ivBg.getVisibility() == View.VISIBLE) {
                Animator anim = ViewAnimationUtils.createCircularReveal(binding.ivBg, cx, cy, finalRadius, 0);
                anim.setDuration(500);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        binding.ivBg.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            } else {
                Animator anim = ViewAnimationUtils.createCircularReveal(binding.ivBg, cx, cy, 0f, finalRadius);
                binding.ivBg.setVisibility(View.VISIBLE);
                anim.start();
            }
        }
    }
}
