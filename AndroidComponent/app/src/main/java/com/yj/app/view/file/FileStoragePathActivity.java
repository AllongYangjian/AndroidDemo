package com.yj.app.view.file;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.R;
import com.yj.app.databinding.ActivityFileStoragePathBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileStoragePathActivity extends AppCompatActivity {

    private ActivityFileStoragePathBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file_storage_path);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        initView();
        initListView();
    }

    @SuppressLint("NewApi")
    private void initListView() {
        List<Map<String, String>> listMap = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("name", "内部存储路径");
        map.put("value", "");
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "根目录");
        map.put("value", getDataDir().getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "文件目录");
        map.put("value", getFilesDir().getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "缓存目录");
        map.put("value", getCacheDir().getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "代码缓存");
        map.put("value", getCodeCacheDir().getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "数据库");
        map.put("value", getDatabasePath("test.db").getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "外部存储路径");
        map.put("value","");
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "缓存");
        map.put("value", Objects.requireNonNull(getExternalCacheDir()).getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "DIRECTORY_MUSIC");
        map.put("value",Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_MUSIC)).getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "DIRECTORY_PODCASTS");
        map.put("value",Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_PODCASTS)).getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "DIRECTORY_RINGTONES");
        map.put("value",Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_RINGTONES)).getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "DIRECTORY_ALARMS");
        map.put("value",Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_ALARMS)).getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "DIRECTORY_NOTIFICATIONS");
        map.put("value",Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)).getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "DIRECTORY_MOVIES");
        map.put("value",Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_MOVIES)).getAbsolutePath());
        listMap.add(map);

        map = new HashMap<>();
        map.put("name", "DIRECTORY_PICTURES");
        map.put("value",Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath());
        listMap.add(map);

        File[] file = getExternalMediaDirs();
        if (file != null) {
            for (File item : file) {
                map = new HashMap<>();
                map.put("name", item.getName());
                map.put("value",item.getAbsolutePath());
            }
        }



        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listMap,
                android.R.layout.simple_list_item_2, new String[]{"name", "value"},
                new int[]{android.R.id.text1, android.R.id.text2});
        binding.pathList.setAdapter(adapter);
    }

    @SuppressLint("NewApi")
    private void initView() {

        StringBuilder sb = new StringBuilder();
        sb.append("内部存储路径").append("\r\n");
        sb.append("根目录").append(getDataDir().getAbsolutePath()).append("\r\n");
        sb.append("文件目录").append(getFilesDir().getAbsolutePath()).append("\r\n");
        sb.append("缓存目录").append(getCacheDir().getAbsolutePath()).append("\r\n");
        sb.append("代码缓存").append(getCodeCacheDir().getAbsolutePath()).append("\r\n");
        sb.append("数据库").append(getDatabasePath("test.db").getAbsolutePath()).append("\r\n");

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            sb.append("外部存储路径").append("\r\n");
            sb.append("缓存").append(Objects.requireNonNull(getExternalCacheDir()).getAbsolutePath()).append("\r\n");
            sb.append("DIRECTORY_MUSIC:").append(Objects.requireNonNull(getExternalFilesDir(null)).getAbsolutePath()).append("\r\n");
            sb.append("DIRECTORY_PODCASTS:").append(Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_MUSIC)).getAbsolutePath()).append("\r\n");
            sb.append("DIRECTORY_RINGTONES:").append(Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_PODCASTS)).getAbsolutePath()).append("\r\n");
            sb.append("DIRECTORY_RINGTONES:").append(Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_RINGTONES)).getAbsolutePath()).append("\r\n");
            sb.append("DIRECTORY_ALARMS:").append(Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_ALARMS)).getAbsolutePath()).append("\r\n");
            sb.append("DIRECTORY_NOTIFICATIONS:").append(Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)).getAbsolutePath()).append("\r\n");
            sb.append("DIRECTORY_PICTURES:").append(Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath()).append("\r\n");
            sb.append("DIRECTORY_MOVIES:").append(Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_MOVIES)).getAbsolutePath()).append("\r\n");
            File[] file = getExternalMediaDirs();
            if (file != null) {
                for (File item : file) {
                    sb.append("文件").append(Objects.requireNonNull(item).getAbsolutePath()).append("\r\n");
                }
            }
        }
        binding.tvPath.setText(sb.toString());

    }
}
