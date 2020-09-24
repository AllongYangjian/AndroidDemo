// IOnNewBookArrivedListener.aidl
package com.jiancode.aidl_server;

// Declare any non-default types here with import statements
import com.jiancode.aidl_server.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
