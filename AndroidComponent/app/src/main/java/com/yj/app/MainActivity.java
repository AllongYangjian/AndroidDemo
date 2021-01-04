package com.yj.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.yj.app.databinding.ActivityMainBinding;
import com.yj.app.view.animator.AnimatorActivity;
import com.yj.app.view.file.FilePathActivity;
import com.yj.app.view.layoutcomponent.CopyAndPasteActivity;
import com.yj.app.view.layoutcomponent.DialogActivity;
import com.yj.app.view.layoutcomponent.DragActivity;
import com.yj.app.view.layoutcomponent.LayoutActivity;
import com.yj.app.view.layoutcomponent.MenuActivity;
import com.yj.app.view.layoutcomponent.PictureInPictureActivity;
import com.yj.app.view.layoutcomponent.SearchDialogActivity;
import com.yj.app.view.layoutcomponent.SearchViewActivity;
import com.yj.app.view.layoutcomponent.SettingActivity;
import com.yj.app.view.media.MediaActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        initView();
    }

    private void initView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.component_list,
                android.R.layout.simple_list_item_1);
        binding.listComponent.setAdapter(adapter);
        binding.listComponent.setOnItemClickListener((parent, view, position, id) -> gotoActivity(position));
    }

    private void gotoActivity(int index) {
        switch (index) {
            case 0:
                doGotoActivity(LayoutActivity.class);
                break;
            case 1:
                doGotoActivity(AnimatorActivity.class);
                break;
            case 2:
//                doGotoActivity(DialogActivity.class);
                break;
            case 3:
                doGotoActivity(MediaActivity.class);
                break;
            case 4:
                doGotoActivity(FilePathActivity.class);
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
