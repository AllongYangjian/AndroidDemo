package com.jiancode.drawabledemo;

import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.tv_transition);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable drawable = (TransitionDrawable) textView.getBackground();
                index++;
                if (index % 2 == 0) {
                    drawable.startTransition(1000);
                } else {
                    drawable.reverseTransition(1000);
                }
            }
        });

        TextView textView1 = findViewById(R.id.tv_custom_drawable);
        CustomDrawable customDrawable = new CustomDrawable(Color.RED);
        textView1.setBackground(customDrawable);
    }
}
