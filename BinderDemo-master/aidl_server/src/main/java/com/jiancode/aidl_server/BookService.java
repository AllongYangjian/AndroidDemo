package com.jiancode.aidl_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookService extends Service {

    private static final String TAG = BookService.class.getSimpleName();

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    //由于IOnNewBookArrivedListener是跨进程传递的，会导致无法匹配,因此采用专门用来进程间通信的
    //的RemoteCallbackList

//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> listeners = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedListener> listeners  = new RemoteCallbackList<>();

    private boolean isDestroy = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
        isDestroy = false;
        mBookList.add(new Book(1, "杨建"));
        mBookList.add(new Book(2, "李文瀚"));

        new Thread(new ServiceWoker()).start();
    }

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
            Log.e(TAG, "Receive Client MSG:" + book.toString());
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!listeners.contains(listener)) {
//                listeners.add(listener);
//                Log.e(TAG,"register successed");
//            } else {
//                Log.e(TAG, "this listener already exists");
//            }
            listeners.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(listeners.contains(listener)){
//                listeners.remove(listener);
//                Log.e(TAG,"register listener succeed");
//            }else {
//                Log.e(TAG,"not found ,cannot register");
//            }
//            Log.e(TAG,"current size:"+listeners.size());
            listeners.unregister(listener);
        }
    };

    private void OnNewBookArrived(Book book) throws RemoteException {
//        for (int x = 0; x < listeners.size(); x++) {
//            IOnNewBookArrivedListener listener = listeners.get(x);
//            listener.onNewBookArrived(book);
//        }
        final int N = listeners.beginBroadcast();
        for(int x = 0;x<N;x++){
            IOnNewBookArrivedListener listener = listeners.getBroadcastItem(x);
            if(listener!=null){
                listener.onNewBookArrived(book);
            }
        }
        listeners.finishBroadcast();
    }

    private class ServiceWoker implements Runnable {

        @Override
        public void run() {
            while (!isDestroy) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int bookId = mBookList.size() + 1;

                Book book = new Book(bookId, "new book#" + bookId);
                mBookList.add(book);
                try {
                    OnNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }
}
