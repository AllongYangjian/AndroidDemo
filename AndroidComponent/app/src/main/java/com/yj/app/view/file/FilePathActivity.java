package com.yj.app.view.file;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityFilePathBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

public class FilePathActivity extends AppCompatActivity {

    private ActivityFilePathBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file_path);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.file_list, android.R.layout.simple_list_item_1);
        binding.rvFilePath.setAdapter(adapter);
        binding.rvFilePath.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        gotoActivity(FileStoragePathActivity.class);
                        break;
                    case 1:
                        gotoActivity(SharePreferenceActivity.class);
                        break;
                    case 2:
                        gotoActivity(DatabaseActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void gotoActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
