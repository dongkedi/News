package com.zhuoxin.news;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/12/8.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
