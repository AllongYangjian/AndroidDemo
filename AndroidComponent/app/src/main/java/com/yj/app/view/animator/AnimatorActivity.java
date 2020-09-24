package com.yj.app.view.animator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityAnimatorBinding;
import com.yj.app.databinding.ActivityLayoutBinding;
import com.yj.app.view.layoutcomponent.CopyAndPasteActivity;
import com.yj.app.view.layoutcomponent.DialogActivity;
import com.yj.app.view.layoutcomponent.DragActivity;
import com.yj.app.view.layoutcomponent.MenuActivity;
import com.yj.app.view.layoutcomponent.PictureInPictureActivity;
import com.yj.app.view.layoutcomponent.ScrollRefreshActivity;
import com.yj.app.view.layoutcomponent.SearchDialogActivity;
import com.yj.app.view.layoutcomponent.SearchViewActivity;
import com.yj.app.view.layoutcomponent.SettingActivity;
import com.yj.app.view.layoutcomponent.ToastActivity;

import java.util.Objects;

/**
 * 动画相关组件和属性
 * 1、
 *
 */
public class AnimatorActivity extends AppCompatActivity {

    private ActivityAnimatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animator);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.animator_list,
                android.R.layout.simple_list_item_1);
        binding.rvLayoutComponentList.setAdapter(adapter);
        binding.rvLayoutComponentList.setOnItemClickListener((parent, view, position, id) -> gotoActivity(position));
    }

    private void gotoActivity(int index) {
        switch (index) {
            case 0:
                doGotoActivity(BasicUseActivity.class);
                break;
            case 1:
                doGotoActivity(DrawableAnimatorActivity.class);
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
