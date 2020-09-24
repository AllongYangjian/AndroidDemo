package com.jiancode.animationdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button btnViewAnimation;
    private Button btnAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnViewAnimation = findViewById(R.id.btn_view);
        btnAnimator = findViewById(R.id.btn_animator);
        btnAnimator.setOnClickListener(onClickListener);
        btnViewAnimation.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_view:
                    startMainActivity();
                    break;
                case R.id.btn_animator:
                    startAnimatorActivity();
                    break;
            }
        }
    };

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startAnimatorActivity() {
        Intent intent = new Intent(this, AnimatorActivity.class);
        startActivity(intent);
    }
}
