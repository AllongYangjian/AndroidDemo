// IBookManager.aidl
package com.jiancode.aidl_server;

// Declare any non-default types here with import statements
import com.jiancode.aidl_server.Book;
import com.jiancode.aidl_server.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);
}
