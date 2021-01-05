package com.yj.app.view.file;

import android.annotation.SuppressLint;
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
import com.yj.app.domain.Record;
import com.yj.app.domain.Student;
import com.yj.app.domain.User;
import com.yj.app.utils.DateUtils;
import com.yj.app.view.BaseActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class DatabaseActivity extends BaseActivity {

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
//            MyRoomDatabase database = MyRoomDatabase.getDatabase(getApplicationContext());
            appDatabase.getUserDao().insertAll(user);
            Log.e(TAG,user.uid+"");
           saveStudent();
            saveUserLibrary();
        });

        binding.btnDelete.setOnClickListener(v->{
//            MyRoomDatabase database = MyRoomDatabase.getDatabase(getApplicationContext());
            List<User> userList = appDatabase.getUserDao().getAll();
            if(userList.size()>0){
                appDatabase.getUserDao().delete(userList.get(userList.size()-1));
            }
            saveUser("test","test2");
            saveRecord();
        });

        binding.btnQuery.setOnClickListener(v->{
            List<Record> records = appDatabase.getRecordDao().queryByTime(DateUtils.getWeekStart(),
                    DateUtils.getWeekEnd());
            Log.e(TAG,records.toString());
        });
    }

    @SuppressLint("NewApi")
    private void saveUser(String firstName, String lastName){
        User user = new User();
        user.firstName = firstName;
        user.lastName = lastName;
//        MyRoomDatabase database = MyRoomDatabase.getDatabase(getApplicationContext());
        List<Long> data  =appDatabase.getUserDao().insertAll(user);
        for (Long item:data){
            System.err.println("rowId:"+item);
        }
    }

    private void saveRecord(){
        Record record = new Record();
        record.name = UUID.randomUUID().toString();
        record.recordTime = new Date();
        long rowId = appDatabase.getRecordDao().save(record);
        Toast.makeText(getApplicationContext(),rowId+"",Toast.LENGTH_LONG).show();
    }

    private void saveStudent() {
//        MyRoomDatabase database = MyRoomDatabase.getDatabase(getApplicationContext());
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
        appDatabase.getStudentDao().insertAll(student);
    }

    private void saveUserLibrary(){
//        MyRoomDatabase database = MyRoomDatabase.getDatabase(getApplicationContext());
        Library library = new Library();
        library.name = "library1";
        library.sid = 1;
        appDatabase.getLibraryDao().insertAll(library);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
