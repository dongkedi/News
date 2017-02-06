package com.zhuoxin.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhuoxin.news.fragment.NewsFragment;
import com.zhuoxin.news.fragment.NewsPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {
    //数据
    List<NewsFragment> newsFragmentList = new ArrayList<>();

    public List<NewsFragment> getNewsFragmentList() {
        return newsFragmentList;
    }

    public void setNewsFragmentList(List<NewsFragment> newsFragmentList) {
        this.newsFragmentList = newsFragmentList;
    }

    public NewsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return newsFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return newsFragmentList.get(position);
    }


}
