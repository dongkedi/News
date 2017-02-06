package com.zhuoxin.news.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhuoxin.news.R;
import com.zhuoxin.news.activity.NewsActivity;
import com.zhuoxin.news.entity.NewsInfo;
import com.zhuoxin.news.utils.BitmapUtil;
import com.zhuoxin.news.utils.DBUtil;
import com.zhuoxin.news.utils.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewsCollectAdapter extends RecyclerView.Adapter<NewsCollectAdapter.MyViewHolder> {
    //数据
    List<NewsInfo> newsInfoList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public NewsCollectAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public List<NewsInfo> getNewsInfoList() {
        return newsInfoList;
    }

    public void setNewsInfoList(List<NewsInfo> newsInfoList) {
        this.newsInfoList = newsInfoList;
    }

    /**
     * 1、建立MyViewHolder，继承自RecyclerView中的ViewHoder
     * 创建构造函数，构造函数中会传递进来一个itemView，此itemView就是类似与在ListView中填充的布局
     * 其他的用法，和之前写的ViewHolder一样
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_news;
        TextView tv_news_title;
        TextView tv_news_type;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_news = (ImageView) itemView.findViewById(R.id.iv_news);
            tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
            tv_news_type = (TextView) itemView.findViewById(R.id.tv_news_type);
        }
    }

    @Override
    public int getItemCount() {
        return newsInfoList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    /**
     * 在最后的方法中设置单击事件
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewsInfo newsInfo = newsInfoList.get(position);
        final int posi = position;
        //取出整个item的布局
        View itemView = holder.itemView;
        //对整个布局进行单击时间的设置
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsActivity.class);
                String url = newsInfo.getUrl();
                String largeImageURL = newsInfo.getLargeImageURL();
                String title = newsInfo.getTitle();
                intent.putExtra("url", url);
                intent.putExtra("largeImageURL", largeImageURL);
                intent.putExtra("title", title);
                context.startActivity(intent);
            }
        });
        //对整个item进行长按的事件设置
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("您确定要取消收藏？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //1、从数据库中删除
                                MyOpenHelper myOpenHelper = new MyOpenHelper(context, "News.db", null, 1);
                                DBUtil.delete(myOpenHelper.getReadableDatabase(), newsInfo);
                                //2、从Adapter中的newsInfoList中删除
                                newsInfoList.remove(newsInfo);
                                //3、界面刷新
                                //notifyDataSetChanged();
                                notifyItemRemoved(posi);
                                notifyItemRangeRemoved(posi, newsInfoList.size());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create();
                alertDialog.show();
                return false;
            }
        });
        //设置图标和文字
//        holder.iv_news.setTag(newsInfo.getImageURL());
//        BitmapUtil.setBitmap(context, newsInfo.getImageURL(), holder.iv_news);
        Picasso.with(context)
                .load(newsInfo.getImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.shape_button_red)
                .into(holder.iv_news);
        holder.tv_news_title.setText(newsInfo.getTitle());
        holder.tv_news_type.setText(newsInfo.getType());
    }
}
