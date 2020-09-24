package com.yj.app.view.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityBasicUseBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

/**
 * 动画的基本使用
 */
public class BasicUseActivity extends AppCompatActivity {

    private ActivityBasicUseBinding binding;
    private float startX = 0f;
    private float intervalDistance = 100f;
    private int width = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basic_use);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        binding.btn1.setOnClickListener(v -> {
            startAnimator(0);
        });
        binding.btn2.setOnClickListener(v -> {
            startAnimator(1);
        });

        binding.btn3.setOnClickListener(v -> changWidth());

        binding.btn4.setOnClickListener(v -> together());
        binding.btn5.setOnClickListener(v -> xmlAnimator());
        binding.btn6.setOnClickListener(v -> xmlAnimator2());
    }

    private void xmlAnimator2() {
        ValueAnimator animator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            binding.view.setTranslationX(value);
        });
        animator.start();
    }

    private void xmlAnimator() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.property_animator);
        set.setTarget(binding.view);
        set.start();
    }

    private void together() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(binding.view, "translationX", 100f);
        translationX.setDuration(1000);

        ObjectAnimator translationY = ObjectAnimator.ofFloat(binding.view, "translationY", 100f);
        translationY.setDuration(1000);

        ObjectAnimator animator = ObjectAnimator.ofInt(binding.view, "width", width);
        animator.addUpdateListener(animation -> {
//            binding.view.setWidth((Integer) animation.getAnimatedValue()); 这中方式是无效的
            binding.view.getLayoutParams().width = (int) animation.getAnimatedValue();
        });
        animator.setDuration(1000);

        AnimatorSet set = new AnimatorSet();
        set.play(translationX).with(translationY).with(animator);
        set.start();
    }

    private void changWidth() {
        width += 50;
//        binding.view.setWidth(width);
        ObjectAnimator animator = ObjectAnimator.ofInt(binding.view, "width", width);
        animator.addUpdateListener(animation -> {
//            binding.view.setWidth((Integer) animation.getAnimatedValue()); 这中方式是无效的
            binding.view.getLayoutParams().width = (int) animation.getAnimatedValue();
        });
        animator.setDuration(1000);
        animator.start();
    }

    private void startAnimator(int type) {
        ValueAnimator animator = ValueAnimator.ofFloat(startX, intervalDistance);
        animator.addUpdateListener(animation -> {
            float animatorValue = (float) animator.getAnimatedValue();
            if (type == 0) {
                binding.view.setTranslationX(animatorValue);
            } else if (type == 1) {
                binding.view.setTranslationY(animatorValue);
            }

        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startX += 100f;
                intervalDistance += 100f;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(1000);
        animator.start();
    }
}
