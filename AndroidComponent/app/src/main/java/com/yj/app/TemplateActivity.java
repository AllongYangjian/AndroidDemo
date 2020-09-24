package com.yj.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

public class TemplateActivity extends AppCompatActivity {

    private ActivityTemplateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_template);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView(){

    }
}
