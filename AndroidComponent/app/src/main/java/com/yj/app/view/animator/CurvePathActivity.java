package com.yj.app.view.animator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityCurvePathBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

public class CurvePathActivity extends AppCompatActivity {

    private ActivityCurvePathBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_curve_path);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.btnCurvePath.setOnClickListener(v->initView());
        binding.btnCurvePath2.setOnClickListener(v->initView2());
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
}
