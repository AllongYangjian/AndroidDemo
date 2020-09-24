package org.example.yj.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import org.example.yj.flowlayout.widget.FlowLayout;
import org.example.yj.flowlayout.widget.FlowView;

public class MainActivity extends AppCompatActivity {
    private FlowView flowView;
    private FlowLayout flowLayout;

    private String[] str1 = {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button"};

    private String[] str2 = {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView", "Button", "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
            "Android", "Weclome", "ImageView", "TextView"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowView = findViewById(R.id.flow_view);
        flowLayout = findViewById(R.id.flow_layout);
        initData();
    }

    private void initData() {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int x = 0; x < str1.length; x++) {
            TextView textView = (TextView) inflater.inflate(R.layout.tv, flowView, false);
            textView.setText(str1[x]);
            flowView.addView(textView);

        }

        for (int x = 0; x < str2.length; x++) {
            TextView textView1 = (TextView) inflater.inflate(R.layout.tv, flowLayout, false);
            textView1.setText(str2[x]);
            flowLayout.addView(textView1);

        }
    }
}
