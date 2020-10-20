package com.yj.app.view.animator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityTemplateBinding;
import com.yj.app.databinding.ActivityViewPageBinding;

import java.util.Objects;

/**
 * ViewPage切换动画
 */
public class ViewPageAnimationActivity extends AppCompatActivity {

    private ActivityViewPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_page);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView(){

    }
}
