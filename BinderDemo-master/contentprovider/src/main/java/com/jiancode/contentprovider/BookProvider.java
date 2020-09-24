package com.jiancode.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.jiancode.contentprovider.db.DbOpenHelper;

public class BookProvider extends ContentProvider {
    private static final String TAG = BookProvider.class.getSimpleName();

    private static final String authority = "com.jiancode.contentprovider_bookprovidver";

    public static final Uri book_uri = Uri.parse("content://"+authority+"/book");
    public static final Uri user_uri = Uri.parse("content://"+authority+"/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    private Context mContext;
    private SQLiteDatabase db;

    static {
        matcher.addURI(authority,"book",BOOK_URI_CODE);
        matcher.addURI(authority,"user",USER_URI_CODE);
    }

    private String getTableName(Uri uri){
        String tableName = null;
        switch (matcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        init();
        return false;
    }

    private void init(){
        mContext = getContext();
        initProviderData();
    }

    private void initProviderData(){
        db = new DbOpenHelper(mContext).getWritableDatabase();
        db.execSQL("delete from "+DbOpenHelper.BOOK_TABLE_NAME);
        db.execSQL("insert into book values (3,'Android')");
        db.execSQL("insert into book values(4,'Java')");
        db.execSQL("insert into book values(5,'sb')");

        db.execSQL("delete from "+DbOpenHelper.USER_TABLE_NAME);
        db.execSQL("insert into user values(1,'s',3)");
        db.execSQL("insert into user values(2,'b',2)");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.e(TAG,"current thread:"+Thread.currentThread().getName());
        String table =getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("Unsupport uri:"+uri);
        }

        return db.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Override
    public String getType( Uri uri) {
        Log.e(TAG,"getType");
        return null;
    }

    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        Log.e(TAG,"insert");
        String table =getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("Unsupport uri:"+uri);
        }
        db.insert(table,null,values);
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;

    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        Log.e(TAG,"insert");

        String table =getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("Unsupport uri:"+uri);
        }

        int count =  db.delete(table,selection,selectionArgs);
        if(count>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        Log.e(TAG,"update");

        String table =getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException("Unsupport uri:"+uri);
        }

        int index = db.update(table,values,selection,selectionArgs);
        if(index>0){
            mContext.getContentResolver().notifyChange(uri,null);
        }

        return index;
    }
}
