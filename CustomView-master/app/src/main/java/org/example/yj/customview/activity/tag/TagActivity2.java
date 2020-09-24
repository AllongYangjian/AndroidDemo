package org.example.yj.customview.activity.tag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.example.yj.customview.R;
import org.example.yj.customview.widget.customview.tagview2.OnTagClickListener;
import org.example.yj.customview.widget.customview.tagview2.OnTagDeleteListener;
import org.example.yj.customview.widget.customview.tagview2.Tag;
import org.example.yj.customview.widget.customview.tagview2.TagView2;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagActivity2 extends AppCompatActivity implements OnTagClickListener, OnTagDeleteListener {

    @BindView(R.id.tagview)
    TagView2 tagview;
    @BindView(R.id.button)
    Button button;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag2);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        String[] tags = getResources().getStringArray(R.array.customview_item_title);

        tagview.addTags(tags);
        random = new Random();

        String[] colors = this.getResources().getStringArray(R.array.colors);
        for (int x = 0; x < colors.length; x++) {
            Tag tag = new Tag("Colorful Text");
            tag.isDeletable = true;
            tag.tagTextColor = Color.parseColor(colors[x]);
            tagview.addTags(tag);
        }
    }

    public void initView() {
        tagview.setOnTagClickListener(this);
        tagview.setOnTagDeleteListener(this);

    }

    @Override
    public void onTagClick(Tag tag, int position) {

    }

    @Override
    public void onTagDeleted(Tag tag, int position) {

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        String string = "ADD A TAG";
        Tag tag = new Tag(string);
        int r = random.nextInt(2);
        if (r == 0) tag.isDeletable = true;
        r = random.nextInt(9);
        tag.layoutColor = Color.parseColor(this.getResources().getStringArray(R.array.colors)[r]);
        tagview.addTags(tag);
    }
}
