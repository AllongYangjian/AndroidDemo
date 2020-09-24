// IBookManager.aidl
package com.jiancode.binder.bean;

// Declare any non-default types here with import statements
import com.jiancode.binder.bean.Book;

interface IBookManager {


    List<Book> getBookList();

    void addBook(in Book book);
}
