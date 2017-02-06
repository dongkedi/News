package com.zhuoxin.news.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuoxin.news.R;
import com.zhuoxin.news.adapter.NewsPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/12/9.
 */

public class NewsPagerFragment extends Fragment {
    //对于for循环中的数据，如果每次的数据都不一样，则建立一个数据，存储每次需要的数据
    String types[] = new String[]{NewsFragment.TYPE_TOP, NewsFragment.TYPE_GUOJI, NewsFragment.TYPE_KEJI, NewsFragment.TYPE_SHEHUI};
    String typesCN[] = new String[]{"头条", "国际", "科技", "社会"};
    //还可以用EventBus
    @InjectView(R.id.vp_news_pager)
    ViewPager vp_news_pager;
    @InjectView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    NewsPagerAdapter newsPagerAdapter;
    List<NewsFragment> newsFragmentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_news_pager, container, false);
        ButterKnife.inject(this, fragmentView);
        newsPagerAdapter = new NewsPagerAdapter(getActivity().getSupportFragmentManager());
        newsPagerAdapter.setNewsFragmentList(newsFragmentList);
        vp_news_pager.setAdapter(newsPagerAdapter);
        initIndicator();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取数据，对数据进行初始化
        for (int i = 0; i < types.length; i++) {
            NewsFragment newsFragment = new NewsFragment(types[i]);
            newsFragmentList.add(newsFragment);
        }
        //设置Adapter中，并进行刷新操作
        newsPagerAdapter.setNewsFragmentList(newsFragmentList);
        newsPagerAdapter.notifyDataSetChanged();
    }

    public void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return types.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.WHITE);
                colorTransitionPagerTitleView.setSelectedColor(Color.LTGRAY);
                colorTransitionPagerTitleView.setTextSize(20);
                colorTransitionPagerTitleView.setText(typesCN[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp_news_pager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(0xffffffff);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, vp_news_pager);
    }
}
