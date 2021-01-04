package com.yj.app.view.file;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.MyApplication;
import com.yj.app.R;
import com.yj.app.databinding.ActivityDatabaseBinding;
import com.yj.app.db.MyRoomDatabase;
import com.yj.app.domain.User;

import java.util.Objects;

public class DatabaseActivity extends AppCompatActivity {

    private ActivityDatabaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_database);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        binding.btnSave.setOnClickListener(v -> {
            String firstName = binding.etFirstName.getText().toString().trim();
            String lastName = binding.etLastName.getText().toString().trim();
            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
                Toast.makeText(this, "请输入值", Toast.LENGTH_LONG).show();
                return;
            }
            User user = new User();
            user.firstName = firstName;
            user.lastName = lastName;
            MyRoomDatabase database = MyApplication.getInstance().getDatabase();
            database.getUserDao().insertAll(user);
        });
    }
}
