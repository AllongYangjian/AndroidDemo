package com.yj.app.view.media;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.yj.app.R;
import com.yj.app.databinding.ActivityVideoRecordBinding;
import com.yj.app.view.media.fragment.Camera2VideoFragment;

import java.util.Objects;

public class VideoRecordActivity extends AppCompatActivity {

    private final static String TAG  ="VideoRecordActivity";

    private ActivityVideoRecordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_record);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, Camera2VideoFragment.newInstance())
                .commit();
        Log.e(TAG, "sss");
    }
}
