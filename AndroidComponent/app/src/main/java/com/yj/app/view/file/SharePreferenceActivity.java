package com.yj.app.view.file;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;

import com.yj.app.R;
import com.yj.app.databinding.ActivitySharePreferenceBinding;
import com.yj.app.databinding.ActivityTemplateBinding;

import java.util.Objects;

/**
 * 在界面中获取getPreferences（mode）得到的是 view.file.ActivityName.xml（view.file.SharePreferenceActivity.xml）
 * 通过 PreferenceManager.getDefaultSharedPreferences(context)产生的是 应用id_preferences.xml 文件
 */
public class SharePreferenceActivity extends AppCompatActivity {

    private ActivitySharePreferenceBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_preference);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView(){
        binding.btnSave.setOnClickListener(v->{
            String key = binding.etKey.getText().toString().trim();
            String value = binding.etValue.getText().toString().trim();
            if(TextUtils.isEmpty(key) || TextUtils.isEmpty(value)){
                Toast.makeText(getApplicationContext(),"请输入key或value", Toast.LENGTH_SHORT).show();
                return;
            }
            getPreferences(MODE_PRIVATE).edit().putString(key,value).apply();
            sharedPreferences.edit().putString(key,value).apply();
            Toast.makeText(getApplicationContext(),"保存成功", Toast.LENGTH_SHORT).show();
        });

        binding.btnGet.setOnClickListener(v->{
            String key = binding.etKey1.getText().toString().trim();
            if(TextUtils.isEmpty(key)){
                Toast.makeText(getApplicationContext(),"请输入key",Toast.LENGTH_LONG).show();
                return;
            }
            String value = getPreferences(MODE_PRIVATE).getString(key,"");
            binding.etValue1.setText(value);
            Toast.makeText(getApplicationContext(),"获取成功", Toast.LENGTH_SHORT).show();
        });


    }
}
