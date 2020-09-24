package com.jiancode.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View viewTarget;
    private Button btnTranslate;
    private Button btnScale;
    private Button btnRotate;
    private Button btnAlpha;
    private Button btnIndentify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        viewTarget = findViewById(R.id.view_target);

        btnTranslate = findViewById(R.id.btn_translate);
        btnScale = findViewById(R.id.btn_scale);
        btnRotate = findViewById(R.id.btn_rotate);
        btnAlpha = findViewById(R.id.btn_alpha);
        btnIndentify = findViewById(R.id.btn_self_identify);

        btnTranslate.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        btnAlpha.setOnClickListener(this);
        btnIndentify.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_translate:
                startTranslateAnimation();
                break;
            case R.id.btn_scale:

                break;
            case R.id.btn_rotate:

                break;
            case R.id.btn_alpha:

                break;
            case R.id.btn_self_identify:
                startRotate3dAnimation();
                break;
        }
    }

    private void startTranslateAnimation() {
        viewTarget.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        viewTarget.startAnimation(animation);
    }

    private void startScaleAnimation() {

    }

    private void startRotateAnimation() {

    }

    private void startAlphaAnimation() {

    }

    private void startRotate3dAnimation() {
        viewTarget.clearAnimation();
        Rotate3DAnimation animation = new Rotate3DAnimation(0, 180, 100, 100, 100, true);
        viewTarget.startAnimation(animation);
    }
}
