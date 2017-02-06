package com.zhuoxin.news.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhuoxin.news.R;
import com.zhuoxin.news.adapter.NewsCollectAdapter;
import com.zhuoxin.news.entity.NewsInfo;
import com.zhuoxin.news.utils.DBUtil;
import com.zhuoxin.news.utils.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewsCollectFragment extends Fragment {
    Context context;
    @InjectView(R.id.rv_news_collect)
    RecyclerView rv_news_collect;
    List<NewsInfo> newsInfoList = new ArrayList<>();
    NewsCollectAdapter newsCollectAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_collect, container, false);
        ButterKnife.inject(this, view);
        context = getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyOpenHelper myOpenHelper = new MyOpenHelper(context, "News.db", null, 1);
        SQLiteDatabase database = myOpenHelper.getWritableDatabase();
        newsInfoList = DBUtil.queryAllNews(database);
        newsCollectAdapter = new NewsCollectAdapter(context);
        newsCollectAdapter.setNewsInfoList(newsInfoList);
        //设置rv的布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rv_news_collect.setLayoutManager(linearLayoutManager);
        rv_news_collect.setAdapter(newsCollectAdapter);
    }
}
