package com.yj.app.view.animator;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityTemplateBinding;
import com.yj.app.databinding.ActivityViewUpdateBinding;

import java.util.Objects;

/**
 * 界面更新动画
 * animateLayoutChanges
 */
public class ViewUpdateActivity extends AppCompatActivity {

    private ActivityViewUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_update);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {

        binding.btnAdd.setOnClickListener(v -> addChildView());

    }

    private void addChildView() {
        Context context = binding.container.getContext();
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText("HHH");
        textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        binding.container.addView(textView);
    }
}
