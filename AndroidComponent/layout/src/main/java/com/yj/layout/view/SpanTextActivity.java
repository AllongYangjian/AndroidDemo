package com.yj.layout.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.layout.R;
import com.yj.layout.databinding.ActivitySpanBinding;

public class SpanTextActivity extends AppCompatActivity {

    private ActivitySpanBinding binding;
    SpannableStringBuilder sb = new SpannableStringBuilder("this is yangjian speaking");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_span);

        binding.btnSpan1.setOnClickListener(onClickListener);
        binding.btnSpan2.setOnClickListener(onClickListener);
        binding.btnSpan3.setOnClickListener(onClickListener);
        binding.btnSpan4.setOnClickListener(onClickListener);
        binding.btnSpan5.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_span1:
                sb.setSpan(new ForegroundColorSpan(Color.RED), 8, 12, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                binding.text1.setText(sb);
                break;
            case R.id.btn_span2:
                sb.setSpan(new UnderlineSpan(),8,12,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//                sb.setSpan(new UnderlineSpan(),8,12,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                binding.text1.setText(sb);
                break;
            case R.id.btn_span3:
                sb.setSpan(new SuperscriptSpan(),1,2,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                sb.setSpan(new SubscriptSpan(),3,4,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                binding.text1.setText(sb);
                break;
            case R.id.btn_span4:
                sb.setSpan(new RelativeSizeSpan(0.5f),6,8,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                binding.text1.setText(sb);
                break;
            case R.id.btn_span5:
                sb.setSpan(new BackgroundColorSpan(Color.GRAY),13,sb.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                binding.text1.setText(sb);
                break;
        }
    };


}
