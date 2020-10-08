package com.yj.app.view.animator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import com.yj.app.R;
import com.yj.app.databinding.ActivitySpringBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

/**
 * 弹簧动画
 */
public class SpringActivity extends AppCompatActivity {

    private final String TAG = "SpringActivity";

    private ActivitySpringBinding binding;
    private VelocityTracker velocityTracker;
    private float downX, downY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_spring);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        velocityTracker = VelocityTracker.obtain();
        binding.imgContent.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    velocityTracker.addMovement(event);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    binding.imgContent.setTranslationX(event.getX()-downX);
                    binding.imgContent.setTranslationY(event.getY()-downY);
                    velocityTracker.addMovement(event);
                    return true;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    velocityTracker.computeCurrentVelocity(1000);
                    if (binding.imgContent.getTranslationX() >= binding.imgContent.getTranslationY()) {
                        SpringAnimation animX = new SpringAnimation(v, SpringAnimation.TRANSLATION_X, 0);
                        animX.getSpring().setStiffness(getStiffnesSeekbar());
                        animX.getSpring().setDampingRatio(getDampingSeekbar());
                        animX.setStartVelocity(velocityTracker.getXVelocity());
                        animX.start();
                    } else {
                        SpringAnimation animY = new SpringAnimation(v, SpringAnimation.TRANSLATION_Y, 0);
                        animY.getSpring().setStiffness(getStiffnesSeekbar());
                        animY.getSpring().setDampingRatio(getDampingSeekbar());
                        animY.setStartVelocity(velocityTracker.getXVelocity());
                        animY.start();
                    }
                    return true;
                default:
                    return false;
            }
        });
    }

    private float getDampingSeekbar() {
        return Math.max(binding.seekbarDamping.getProgress(), 1f);
    }

    private float getStiffnesSeekbar() {
        return binding.seekbarDamping.getProgress() / 100f;
    }

    private void initAnimation() {
        SpringAnimation animation = new SpringAnimation(binding.imgContent, DynamicAnimation.TRANSLATION_X, 0);
        animation.addUpdateListener((animation1, value, velocity) -> {
            Log.e(TAG, "弹力:" + value);
            animation.animateToFinalPosition(value);
        });

        animation.addEndListener((animation12, canceled, value, velocity) -> {
            animation.animateToFinalPosition(value);
        });
        animation.setStartValue(0f);
        animation.setMinValue(0);
        animation.setMaxValue(1000);
        animation.setStartVelocity(100);
        animation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        animation.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        animation.start();
    }
}
