package org.example.yj.draggridtag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.example.yj.draggridtag.adapter.DragGridAdapter;
import org.example.yj.draggridtag.widget.DragGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DragGridView dragGridView;
    List<String> list;
    private DragGridAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int x = 0; x < 12; x++) {
            list.add("grid_" + x);
        }

    }

    private void initView() {
        dragGridView = findViewById(R.id.drag_grid);
        adapter = new DragGridAdapter(this,list);
        dragGridView.setAdapter(adapter);
    }
}
