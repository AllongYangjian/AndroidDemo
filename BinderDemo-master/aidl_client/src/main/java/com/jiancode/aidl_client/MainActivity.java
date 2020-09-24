package com.jiancode.aidl_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jiancode.aidl_server.Book;
import com.jiancode.aidl_server.IBookManager;
import com.jiancode.aidl_server.IOnNewBookArrivedListener;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private IBookManager bookManager;

    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        bindService();
    }

    private void bindService(){
        Intent intent = new Intent();
        intent.setAction("com.jiancode.aidl_server_server");
        intent.setPackage("com.jiancode.aidl_server");
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                bookManager = IBookManager.Stub.asInterface(service);
                bookManager.asBinder().linkToDeath(deathRecipient,0);
                bookManager.registerListener(onNewBookArrivedListener);
                List<Book> books = bookManager.getBookList();
                Log.e(TAG, books.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IOnNewBookArrivedListener onNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            Log.e(TAG, "新书到达：" + book.toString());
        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG,"binderDied");
            if(bookManager!=null){
                bookManager.asBinder().unlinkToDeath(this,0);
            }
            bookManager = null;
            bindService();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bookManager!=null && bookManager.asBinder().isBinderAlive()){
            try {
                bookManager.unregisterListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(connection);
    }

    @Override
    public void onClick(View v) {
        int id = new Random().nextInt(10);
        try {
            bookManager.addBook(new Book(id, "sss"));
            Log.e(TAG, bookManager.getBookList().toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
