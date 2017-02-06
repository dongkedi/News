package com.zhuoxin.news.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/30.
 * 网络访问的工具类
 */

public class HttpClientUtil {
    public interface OnJsonGetListener {
        public void jsonGetSuccess(String json);//成功的回调，返回json数据

        public void jsonGetFail(int responseCode);//失败的回调，返回响应码

        public void jsonGetException(Exception e);//异常的回调，返回异常
    }

    /**
     * 从网络获取Json数据
     *
     * @param targetURL 需要从targetURL中获取Json数据
     */
    public static void getJson(URL targetURL, OnJsonGetListener listener) {
        HttpURLConnection httpURLConnection = null;
        //为了效率和空间，用StringBuffer
        StringBuffer json = new StringBuffer();
        try {
            //从URL中打开连接
            httpURLConnection = (HttpURLConnection) targetURL.openConnection();
            //设置连接参数
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            //开启连接
            httpURLConnection.connect();
            //获取响应码，并判断呢状态是否问哦200
            if (httpURLConnection.getResponseCode() == 200) {
                //读取数据
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String msg = null;
                //循环取出数据，并放入json中
                while ((msg = bufferedReader.readLine()) != null) {
                    json.append(msg);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                listener.jsonGetSuccess(json.toString());
            } else {
                //不成功
                listener.jsonGetFail(httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            listener.jsonGetException(e);
        } finally {
            httpURLConnection.disconnect();
        }
    }

    /**
     * 从网络获取图片，设置到对应的ImageView上
     *
     * @param imageURL  图片的网址
     * @param imageView 图片对应的ImageView
     */
    public static void setBitmapToImageView(final Context context, final URL imageURL, final ImageView imageView) {
        final String fileMD5 = MD5Util.getMD5(imageURL.toString());
        AsyncTask<URL, Void, Bitmap> asyncTask = new AsyncTask<URL, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(URL... params) {
                Bitmap bitmap = null;
                URL targetURL = params[0];
                //进行Http操作
                try {
                    HttpURLConnection connection = (HttpURLConnection) targetURL.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                        is.close();
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                //取出Tag进行判断
                String tag = (String) imageView.getTag();
                if (tag.equals(imageURL.toString())) {
                    BitmapUtil.bitmapLruCache.put(fileMD5, bitmap);
                    BitmapUtil.setFileCache(context,imageURL.toString(),bitmap);
                    imageView.setImageBitmap(bitmap);
                }
            }
        };
        //1、只能通过调用execute方法来执行一部任务。2、只能调用一次
        asyncTask.execute(imageURL);
    }
}
