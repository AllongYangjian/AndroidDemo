package com.jiancode.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.jiancode.binder.bean.Book;
import com.jiancode.binder.bean.IBookManager;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {

    private final String TAG = MyService.class.getSimpleName();

    private List<Book> books;

    private IBinder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e(TAG,book.toString());
            books.add(book);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
        books = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

}
