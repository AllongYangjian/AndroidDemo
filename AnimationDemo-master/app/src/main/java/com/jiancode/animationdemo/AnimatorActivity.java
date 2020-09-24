package com.jiancode.animationdemo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnimatorActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;

    private View viewTarget;

    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        viewTarget = findViewById(R.id.view_target);

        btnTest = findViewById(R.id.btn_test);

        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    startAnimator1();
                    break;
                case R.id.btn2:
                    startAnimator2();
                    break;
                case R.id.btn3:

                    break;
            }
        }
    };

    private void startAnimator1() {
        ValueAnimator animator = ObjectAnimator.ofInt(viewTarget, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatCount(ValueAnimator.REVERSE);
        animator.start();
    }

    private void startAnimator2() {
        ViewWrapper viewWrapper = new ViewWrapper(btnTest);
        ObjectAnimator.ofInt(viewWrapper, "width", 500)
                .setDuration(5000)
                .start();
    }

    private class ViewWrapper {
        private View view;

        public ViewWrapper(View btn) {
            this.view = btn;
        }

        public int getWidth() {
            return view.getLayoutParams().width;
        }

        public void setWidth(int width) {
            view.getLayoutParams().width = width;
            view.requestLayout();
        }
    }
}
