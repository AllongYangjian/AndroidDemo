package com.yj.app.view.layoutcomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yj.app.R;
import com.yj.app.databinding.ActivityContextModeBinding;

import java.util.Objects;

/**
 * 操作模式式上下文菜单
 * 显示在ActionBar的位置
 */
public class ContextModeActivity extends AppCompatActivity {
    private ActivityContextModeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_context_mode);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_test_list_data, android.R.layout.simple_list_item_1);
        binding.listView.setAdapter(adapter);

        binding.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        binding.listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        Toast.makeText(ContextModeActivity.this, "Search", Toast.LENGTH_LONG).show();
                        mode.finish();//关闭菜单
                        return true;
                    case R.id.help:
                        Toast.makeText(ContextModeActivity.this, "Help", Toast.LENGTH_LONG).show();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }
}
