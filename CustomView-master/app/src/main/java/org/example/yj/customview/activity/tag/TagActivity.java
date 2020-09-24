package org.example.yj.customview.activity.tag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.example.yj.customview.R;
import org.example.yj.customview.widget.customview.tagview.TagView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagActivity extends AppCompatActivity {

    @BindView(R.id.tagview_single)
    TagView tagSingle;
    @BindView(R.id.tagview_multi)
    TagView tagMultip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {

    }

    private void initData() {
        List<String> tags1 = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            tags1.add("标签" + x);
        }
        tagSingle.setTags(tags1);

        List<String> tags2 = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            tags2.add("标签" + x);
        }
        tagMultip.setTags(tags2);


    }
}
