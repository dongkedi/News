package com.zhuoxin.news;

import com.google.gson.Gson;
import com.zhuoxin.news.entity.NewsOfJuhe;
import com.zhuoxin.news.utils.HttpClientUtil;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getJson() {
        URL targetURL = null;
        try {
            targetURL = new URL("http://v.juhe.cn/toutiao/index?type=top&key=d728ab4e75e137c4f23aec12ed3ee6cd");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpClientUtil.getJson(targetURL, new HttpClientUtil.OnJsonGetListener() {
            @Override
            public void jsonGetSuccess(String json) {
                System.out.println(json);
                //使用Gson解析数据
                Gson gson = new Gson();
                NewsOfJuhe newsData = gson.fromJson(json, NewsOfJuhe.class);
                List<NewsOfJuhe.Data> dataList = newsData.getResult().getData();
                for (NewsOfJuhe.Data data : dataList) {
                    System.out.println(data.getAuthor_name() + "---" + data.getTitle());
                }
            }

            @Override
            public void jsonGetFail(int responseCode) {
                System.out.print("获取数据失败，服务器响应码为：" + responseCode);
            }

            @Override
            public void jsonGetException(Exception e) {
                System.out.print("获取数据异常，异常原因为：" + e.getMessage());
            }
        });
    }
}