package com.allong.databases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.text.TextUtils;

import com.allong.databases.databinding.ActivityMainBinding;
import com.allong.databases.db.AppDatabase;
import com.allong.databases.entity.User;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.btnSave.setOnClickListener(v->{
            String firstName = binding.etFirstName.getText().toString();
            String lastName = binding.etLastName.getText().toString();
            if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)){
                Snackbar.make(binding.btnSave,"请输入值",Snackbar.LENGTH_LONG).show();
                return;
            }
            User user = new User();
            user.firstName = firstName;
            user.lastName = lastName;
            AppDatabase database = App.getInstance().getDatabase();
            database.getUserDao().saveUser(user);
        });
    }
}