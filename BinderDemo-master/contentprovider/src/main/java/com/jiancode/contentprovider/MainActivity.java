package com.jiancode.contentprovider;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jiancode.contentprovider.bean.Book;
import com.jiancode.contentprovider.bean.User;
import com.jiancode.contentprovider.db.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ContentObserver mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mObserver = new MyContentObserver(handler);

        findViewById(R.id.tv_test)
                .setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            testContentProvider2();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void testContentProvider() {
        Uri uri = Uri.parse("content://com.jiancode.contentprovider_bookprovidver");
        getContentResolver().query(uri, null, null, null);
    }

    private void testContentProvider2() {
        Uri bookUri = BookProvider.book_uri;
        getContentResolver().registerContentObserver(bookUri, true, mObserver);
        ContentValues values = new ContentValues();
        values.put("_id", getRandomInt());
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);

        Uri userUri = BookProvider.user_uri;
        getContentResolver().registerContentObserver(userUri, true, mObserver);
        ContentValues values1 = new ContentValues();
        values1.put("_id", getRandomInt());
        values.put("name", "yj");
        values.put("sex", getRandomInt());
        getContentResolver().insert(userUri, values);

    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(1000);
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2: {
                    List<User> list = (List<User>) msg.obj;
                    Log.e(TAG, list.toString());
                }
                break;
                case 1: {
                    List<Book> list = (List<Book>) msg.obj;
                    Log.e(TAG, list.toString());
                }
                break;
            }

        }
    };

    private class MyContentObserver extends ContentObserver {

        private Handler mHandler;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
            this.mHandler = handler;
        }


        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Log.e(TAG, selfChange+","+uri.toString());
            loadDataByUri(uri);
        }
    }

    private void loadDataByUri(Uri uri) {
        int index = uri.toString().lastIndexOf("/");
        String table = uri.toString().substring(index+1);
        Log.e(TAG,table);
        switch (table) {
            case DbOpenHelper.BOOK_TABLE_NAME:
                queryBook(uri);
                break;
            case DbOpenHelper.USER_TABLE_NAME:
                queryUser(uri);
                break;
            default:
                break;
        }

    }

    private void queryBook(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, new String[]{"_id", "name"}, null, null, null);
        List<Book> bookList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setBookId(cursor.getInt(0));
            book.setBookName(cursor.getString(1));
            bookList.add(book);
        }
        cursor.close();
        handler.obtainMessage(1, bookList).sendToTarget();
    }

    private void queryUser(Uri uri) {
        Cursor userCursor = getContentResolver().query(uri, new String[]{"_id", "name", "sex"}, null
                , null, null);
        List<User> userList = new ArrayList<>();
        while (userCursor.moveToNext()) {
            User user = new User();
            user.setUserId(userCursor.getInt(0));
            user.setUserName(userCursor.getString(1));
            user.setSex(userCursor.getInt(2));
            userList.add(user);
        }
        userCursor.close();
        handler.obtainMessage(2, userList).sendToTarget();
    }
}
