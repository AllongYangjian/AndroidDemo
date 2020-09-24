package com.jiancode.binder.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jiancode.binder.MyService;
import com.jiancode.binder.R;
import com.jiancode.binder.bean.Book;
import com.jiancode.binder.bean.IBookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button btnClient;

    private IBookManager iBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        btnClient = findViewById(R.id.btn_client);
        btnClient.setOnClickListener(onClickListener);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startService(intent);

            startSencondActivity();

        }
    };

    private void startSencondActivity() {
        Book book = new Book(1,"2");
        Bundle bundle = new Bundle();
        bundle.putParcelable("book",book);
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);

            try {
                iBookManager.addBook(new Book(1,"sb"));
                //建立binder断裂监听
                service.linkToDeath(deathRecipient,0);

                List<Book> bookList = iBookManager.getBookList();
                Log.e(TAG,bookList.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iBookManager = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    /**
     * 当服务端挂掉，binder断裂时，调用该方法，
     */
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(iBookManager==null){
                return;
            }
            iBookManager.asBinder().unlinkToDeath(this,0);
            iBookManager = null;
            Intent intent = new Intent(MainActivity.this, MyService.class);
            bindService(intent,connection, Context.BIND_AUTO_CREATE);
        }
    };
}
