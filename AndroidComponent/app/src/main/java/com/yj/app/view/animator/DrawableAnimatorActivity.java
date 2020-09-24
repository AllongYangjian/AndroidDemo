package com.yj.app.view.animator;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityDrawableAnimatorBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

public class DrawableAnimatorActivity extends AppCompatActivity {

    private ActivityDrawableAnimatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawable_animator);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        AnimationDrawable drawable = (AnimationDrawable) binding.imgDrawable.getBackground();
        binding.imgDrawable.setOnClickListener(v -> {
            drawable.start();
        });

        Animatable drawable1 = (Animatable) binding.imgVector.getBackground();
        binding.imgVector.setOnClickListener(v->{
            drawable1.start();
        });
    }
}
