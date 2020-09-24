package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.yj.app.R;
import com.yj.app.databinding.ActivityPopupMenuBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 弹出式菜单
 */
public class PopupMenuActivity extends AppCompatActivity {
    private ActivityPopupMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_popup_menu);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        String[] data = getResources().getStringArray(R.array.item_test_list_data);
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(data));
        list.addAll(Arrays.asList(data));
        list.addAll(Arrays.asList(data));
        list.addAll(Arrays.asList(data));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showPopupMenu(view);
            return true;
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.help:
                    Toast.makeText(PopupMenuActivity.this, "Help", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.search:
                    Toast.makeText(PopupMenuActivity.this, "Search", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.add:
//                    if(item.isChecked()){
//                        item.setChecked(false);
//                    }else {
//                        item.setChecked(true);
//                    }
                    return  true;
                default:
                    return false;
            }
        });
        menu.inflate(R.menu.context_menu);
        menu.show();
    }
}
