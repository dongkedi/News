package com.zhuoxin.news.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/6.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_NEWS = "create table News(" +
            "url text," +
            "imageURL text," +
            "largeImageURL text," +
            "title text," +
            "type text)";

    /**
     * @param context 上下文
     * @param name    数据库的名称
     * @param factory 不需要外部的游标工厂
     * @param version 数据库版本
     */
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 数据库创建的操作
     *
     * @param db String url;
     *           String imageURL;
     *           String largeImageURL;
     *           String title;
     *           String type;
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //在数据库创建的时候，插入一张数据库
        //create table News(url text,imageURL text,largeImageURL text,title text,type text)
        db.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
