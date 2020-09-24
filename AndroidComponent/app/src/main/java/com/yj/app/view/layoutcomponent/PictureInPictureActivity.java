package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.PictureInPictureParams;
import android.os.Build;
import android.os.Bundle;

import com.yj.app.R;
import com.yj.app.databinding.ActivityPictureInPictureBinding;

/**
 * 画中画模式
 */
public class PictureInPictureActivity extends AppCompatActivity {

    private ActivityPictureInPictureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picture_in_picture);
        setSupportActionBar(binding.toolbar);
        binding.btnPictureInPicture.setOnClickListener(v -> {
            onUserLeaveHint();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterPictureInPictureMode(new PictureInPictureParams.Builder().build());
            }
        });
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            enterPictureInPictureMode(new PictureInPictureParams.Builder().build());
//        }
    }
}
