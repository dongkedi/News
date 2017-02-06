package com.zhuoxin.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuoxin.news.R;
import com.zhuoxin.news.base.MyBaseAdapter;
import com.zhuoxin.news.entity.NewsInfo;
import com.zhuoxin.news.utils.BitmapUtil;
import com.zhuoxin.news.utils.HttpClientUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/30.
 */

public class NewsAdapter extends MyBaseAdapter<NewsInfo> {
    public NewsAdapter(Context context) {
        super(context);
    }

    public boolean isFlying = false;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_news, null);
            holder = new ViewHolder();
            holder.iv_news = (ImageView) convertView.findViewById(R.id.iv_news);
            holder.tv_news_title = (TextView) convertView.findViewById(R.id.tv_news_title);
            holder.tv_news_type = (TextView) convertView.findViewById(R.id.tv_news_type);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        URL url = null;
        try {
            url = new URL(getItem(position).getImageURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //添加tag标记
//        holder.iv_news.setTag(getItem(position).getImageURL());
//        //根据滑动状态，进行图片的加载
//        holder.iv_news.setImageResource(R.mipmap.ic_launcher);
//        BitmapUtil.setBitmap(context, getItem(position).getImageURL(), holder.iv_news);
        Picasso.with(context)
                .load(getItem(position).getImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.shape_button_red)
                .into(holder.iv_news);


        holder.tv_news_title.setText(getItem(position).getTitle());
        holder.tv_news_type.setText(getItem(position).getType());
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_news;
        TextView tv_news_title;
        TextView tv_news_type;
    }
}
