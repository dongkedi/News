package com.zhuoxin.news.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhuoxin.news.entity.NewsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 * 数据库工具类，进行对News数据的增删改查
 */

public class DBUtil {
    /**
     * 根据传入的新闻数据，把新闻数据保存到数据库中
     *
     * @param database
     * @param newsInfo
     */
    public static void insert(SQLiteDatabase database, NewsInfo newsInfo) {
        //增加
        ContentValues values = new ContentValues();
        values.put("url", newsInfo.getUrl());
        values.put("imageURL", newsInfo.getImageURL());
        values.put("largeImageURL", newsInfo.getLargeImageURL());
        values.put("title", newsInfo.getTitle());
        values.put("type", newsInfo.getType());
        database.insert("News", null, values);
    }

    /**
     * 数据的清除
     *
     * @param database
     * @param newsInfo
     */
    public static void delete(SQLiteDatabase database, NewsInfo newsInfo) {
        String url = newsInfo.getUrl();
        //delete from News where url = http...
        database.delete("News", "url = ?", new String[]{url});
    }

    /**
     * 查询该条新闻是否存在
     *
     * @param database
     * @param newsInfo
     * @return
     */
    public static boolean isNewsExist(SQLiteDatabase database, NewsInfo newsInfo) {
        Cursor cursor = database.query("News", null, "url = ?", new String[]{newsInfo.getUrl()}, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    /**
     * 查询数据库中所有的新闻数据
     *
     * @param database
     * @return
     */
    public static List<NewsInfo> queryAllNews(SQLiteDatabase database) {
        List<NewsInfo> newsInfoList = new ArrayList<>();
        Cursor cursor = database.query("News", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String imageURL = cursor.getString(cursor.getColumnIndex("imageURL"));
            String largeImageURL = cursor.getString(cursor.getColumnIndex("largeImageURL"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            NewsInfo newsInfo = new NewsInfo(url, imageURL, largeImageURL, title, type);
            newsInfoList.add(newsInfo);
        }
        cursor.close();
        return newsInfoList;
    }
}
