package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.yj.app.R;
import com.yj.app.databinding.ActivityLayoutBinding;

import java.util.Objects;

/**
 * 布局相关界面组件
 */
public class LayoutActivity extends AppCompatActivity {

    private ActivityLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_layout);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.function_list,
                android.R.layout.simple_list_item_1);
        binding.rvLayoutComponentList.setAdapter(adapter);
        binding.rvLayoutComponentList.setOnItemClickListener((parent, view, position, id) -> gotoActivity(position));
    }

    private void gotoActivity(int index) {
        switch (index) {
            case 0:
                doGotoActivity(ScrollRefreshActivity.class);
                break;
            case 1:
                doGotoActivity(ToastActivity.class);
                break;
            case 2:
                doGotoActivity(DialogActivity.class);
                break;
            case 3:
                doGotoActivity(MenuActivity.class);
                break;
            case 4:
                doGotoActivity(SettingActivity.class);
                break;
            case 5:
                doGotoActivity(SearchViewActivity.class);
                break;
            case 6:
                doGotoActivity(SearchDialogActivity.class);
                break;
            case 7:
                doGotoActivity(CopyAndPasteActivity.class);
                break;
            case 8:
                doGotoActivity(DragActivity.class);
                break;
            case 9:
                doGotoActivity(PictureInPictureActivity.class);
                break;
        }
    }

    private void doGotoActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
