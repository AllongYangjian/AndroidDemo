package com.jiancode.contentprovider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";


    public DbOpenHelper(Context context){
        super(context,DB_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " +
                BOOK_TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY," +
                "name TEXT" +
                ")";

        db.execSQL(sql);
        sql = "create table if not exists " + USER_TABLE_NAME
                + "(" +
                "_id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "sex INT" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
