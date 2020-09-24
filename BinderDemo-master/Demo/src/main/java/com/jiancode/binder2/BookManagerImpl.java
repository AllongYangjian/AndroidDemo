package com.jiancode.binder2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BookManagerImpl extends Binder implements IBookManager {

    private List<Book> books;

    public BookManagerImpl() {
        books = new ArrayList<>();
        this.attachInterface(this, DESCRIPTOR);
    }

    @Override
    public List<Book> getBookList() {
        return books;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    public static IBookManager asInterface(IBinder obj){
        if(obj == null){
            return null;
        }

        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if((iin!=null) && (iin instanceof IBookManager)){
            return ((IBookManager)iin);
        }
        return new BookManagerImpl.Proxy(obj);
    }

    @Override
    protected boolean onTransact(int code,  Parcel data,Parcel reply, int flags) throws RemoteException {
        switch (code){
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_getBookList:
                data.enforceInterface(DESCRIPTOR);
                List<Book> result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            case TRANSACTION_addBook:
                data.enforceInterface(DESCRIPTOR);
                Book arg0;
                if((0!=data.readInt())){
                    arg0 = Book.CREATOR.createFromParcel(data);
                }else {
                    arg0 =null;
                }
                this.addBook(arg0);
                reply.writeNoException();
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    public static class Proxy implements IBookManager {

        private IBinder mRemote;

        public Proxy(IBinder mRemote) {
            Log.e("Proxy","Proxy");
            this.mRemote = mRemote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<Book> result;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList,data,reply,0);
                reply.readException();
                result = reply.createTypedArrayList(Book.CREATOR);
            }finally {
                reply.recycle();
                data.recycle();
            }
            return result;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);

                if (book != null) {
                    data.writeInt(1);
                    book.writeToParcel(data, 0);
                } else {
                    data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBook, data, replay, 0);
                replay.readException();
            } finally {
                replay.recycle();
                data.recycle();
            }

        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }


}
