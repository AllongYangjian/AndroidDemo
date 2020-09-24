package org.example.yj.customview.activity.textwall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.example.yj.customview.R;
import org.example.yj.customview.widget.customview.textwall.TextItem;
import org.example.yj.customview.widget.customview.textwall.TextWall;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextWallActivty extends AppCompatActivity {
    final String[] texts = {"聪明", "活泼", "可爱", "善解人意", "美丽", "大方", "帅气", "稳重", "乐观", "多愁善感", "果断勇敢"};
    @BindView(R.id.textwall)
    TextWall textwall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_wall);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        textwall.post(new Runnable() {
            @Override
            public void run() {
                List<TextItem> textItems = new ArrayList<TextItem>();
                for (int i = 0; i < 30; i++) {
                    TextItem item = new TextItem();
                    item.setIndex(10);
                    item.setValue(texts[i % 10]);
                    textItems.add(item);
                }
                textwall.setData(textItems, TextWallActivty.this);
                Log.d("aaaaa", textwall.getWidth() + "dp");

            }
        });
    }
}
