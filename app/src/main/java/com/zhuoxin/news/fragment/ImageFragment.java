package com.zhuoxin.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuoxin.news.R;
import com.zhuoxin.news.adapter.ImageAdapter;
import com.zhuoxin.news.entity.GirlsInfo;
import com.zhuoxin.news.interfaces.GirlsInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ImageFragment extends Fragment {
    Context context;
    @InjectView(R.id.rv_image)
    RecyclerView rv_image;
    //数据和adapter
    List<GirlsInfo.ResultsBean> resultsBeanList = new ArrayList<>();
    ImageAdapter imageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.inject(this, view);
        this.context = getActivity();
        imageAdapter = new ImageAdapter(context);
        imageAdapter.setResultsBeanList(resultsBeanList);
        //创建LayoutManager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_image.setLayoutManager(layoutManager);
        rv_image.setAdapter(imageAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getJson();
    }

    public void getJson() {
        //2、创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //3、获取接口对象
        GirlsInterface girlsInterface = retrofit.create(GirlsInterface.class);
        //4、加入队列进行数据解析
        Call<GirlsInfo> call = girlsInterface.getGirls();
        call.enqueue(new Callback<GirlsInfo>() {
            @Override
            public void onResponse(Call<GirlsInfo> call, Response<GirlsInfo> response) {
                GirlsInfo girlsInfo = response.body();
                resultsBeanList = girlsInfo.getResults();
                imageAdapter.setResultsBeanList(resultsBeanList);
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GirlsInfo> call, Throwable t) {
                System.out.print(t.getMessage());
            }
        });
    }
}
