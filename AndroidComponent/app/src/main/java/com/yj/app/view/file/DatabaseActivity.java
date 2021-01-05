package com.yj.app.view.file;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.app.MyApplication;
import com.yj.app.R;
import com.yj.app.databinding.ActivityDatabaseBinding;
import com.yj.app.db.MyRoomDatabase;
import com.yj.app.domain.Address;
import com.yj.app.domain.Library;
import com.yj.app.domain.Student;
import com.yj.app.domain.User;

import java.util.Objects;

public class DatabaseActivity extends AppCompatActivity {

    private final String TAG  ="DatabaseActivity";

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
            MyRoomDatabase database = MyRoomDatabase.getDatabase(getApplicationContext());
            database.getUserDao().insertAll(user);
            Log.e(TAG,user.uid+"");
           saveStudent();
            saveUserLibrary();
        });
    }

    private void saveStudent() {
        MyRoomDatabase database = MyRoomDatabase.getDatabase(getApplicationContext());
        Student student = new Student();
        student.name="yangjian";
        student.age = 28;
        student.gender="男";
        Address address = new Address();
        address.street= "米市巷街道";
        address.city = "杭州";
        address.state = "浙江";
        address.postCode = 100330;
        student.address = address;
        database.getStudentDao().insertAll(student);
    }

    private void saveUserLibrary(){
        MyRoomDatabase database = MyRoomDatabase.getDatabase(getApplicationContext());
        Library library = new Library();
        library.name = "library1";
        library.sid = 1;
        database.getLibraryDao().insertAll(library);

    }
}
