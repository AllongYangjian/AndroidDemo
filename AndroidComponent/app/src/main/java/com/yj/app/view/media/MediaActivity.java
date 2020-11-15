package com.yj.app.view.media;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityMediaBinding;

import java.util.Objects;

public class MediaActivity extends AppCompatActivity {
    private ActivityMediaBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_media);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.media_list,
                android.R.layout.simple_list_item_1);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view, position, id) -> {
            gotoActivity(position);
        });
    }

    private void gotoActivity(final int index) {
        switch (index) {
            case 0:
                doGotoActivity(VideoRecordActivity.class);
                break;
            default:
                break;
        }
    }

    private void doGotoActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);

    }


}
