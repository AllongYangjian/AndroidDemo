package com.yj.app.view.animator;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;

import com.yj.app.R;
import com.yj.app.databinding.ActivityCurvePathBinding;

import java.util.Objects;

public class CurvePathActivity extends AppCompatActivity {

    private ActivityCurvePathBinding binding;
    private int mTranslationX;
    private int mTranslationY;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_curve_path);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        gestureDetector = new GestureDetector(this,gestureListener);
        mTranslationX = binding.getRoot().getWidth()-binding.ivCircle.getWidth();
        mTranslationY = binding.getRoot().getHeight()-binding.ivCircle.getHeight();
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            mTranslationX = binding.getRoot().getWidth()-binding.ivCircle.getWidth();
            mTranslationY = binding.getRoot().getHeight()-binding.ivCircle.getHeight();
        });

        binding.btnCurvePath.setOnClickListener(v->initView());
        binding.btnCurvePath2.setOnClickListener(v->initView2());
        initView3();
    }

    private void initView(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            Path path = new Path();
            path.arcTo(0f,0f,1000f,1000f,270f,-180f,true);
            ObjectAnimator animator = ObjectAnimator.ofFloat(binding.ivCircle,View.X, View.Y, path);
            animator.setDuration(2000);
            animator.start();
        }
    }

    private void initView2(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
//            Interpolator interpolator =  AnimationUtils.loadInterpolator(this,R.anim.path);
            Interpolator interpolator = AnimationUtils.loadInterpolator(this,android.R.interpolator.fast_out_slow_in);
            ObjectAnimator animator = ObjectAnimator.ofFloat(binding.ivCircle,"translationX", 1000f);
            animator.setInterpolator(interpolator);
            animator.setDuration(1000);
            animator.start();
        }
    }

    private void initView3(){
//        FlingAnimation fling = new FlingAnimation(binding.ivCircle, DynamicAnimation.SCROLL_X);
//        fling.setStartVelocity(0)
//                .setMinValue(0)
//                .setMaxValue(1000)
//                .setFriction(1.1f)
//                .start();
        binding.ivCircle.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                new FlingAnimation(binding.ivCircle,DynamicAnimation.X)
                        .setStartVelocity(velocityX)
                        .setMinValue(0f)
                        .setMaxValue(mTranslationX)
                        .setFriction(1.1f)
                        .start();
            }else {
                new FlingAnimation(binding.ivCircle,DynamicAnimation.Y)
                        .setStartVelocity(velocityY)
                        .setMinValue(0f)
                        .setMaxValue(mTranslationY)
                        .setFriction(1.1f)
                        .start();
            }
            return true;
        }
    };
}
