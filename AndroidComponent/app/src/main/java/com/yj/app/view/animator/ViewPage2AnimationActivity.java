package com.yj.app.view.animator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityViewPage2Binding;
import com.yj.app.databinding.ActivityViewPageBinding;

import java.util.Objects;

/**
 * ViewPage2切换动画
 */
public class ViewPage2AnimationActivity extends AppCompatActivity {

    private ActivityViewPage2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_page2);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView(){

    }
}
