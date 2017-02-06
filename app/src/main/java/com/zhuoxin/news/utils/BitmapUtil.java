package com.zhuoxin.news.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/2.
 * 关于文件的操作
 */

public class BitmapUtil {
    private static final int LRU_SIZE = (int) (Runtime.getRuntime().maxMemory() / 8192);
    //一级缓存：LruCache
    static LruCache<String, Bitmap> bitmapLruCache = new LruCache<String, Bitmap>(LRU_SIZE) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getHeight() * value.getRowBytes();
        }
    };

    //二级缓存：文件缓存
    public static void setFileCache(Context context, String imageURL, Bitmap bitmap) {
        File cacheFile = context.getCacheDir();//获取手机中的缓存目录
        String fileMD5 = MD5Util.getMD5(imageURL);
        File targetFile = new File(cacheFile, fileMD5 + ".PNG");//构建输出目录
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);//输出图片
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getFileCache(Context context, String imageURL) {
        File cacheFile = context.getCacheDir();//获取手机中的缓存目录
        String fileMD5 = MD5Util.getMD5(imageURL);
        File targetFile = new File(cacheFile, fileMD5 + ".PNG");//构建输出目录
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(targetFile.getAbsolutePath());
        return bitmap;
    }

    //三级缓存
    public static void setBitmap(Context context, String imageURL, ImageView imageView) {
        Bitmap bitmap = null;
        String fileMD5 = MD5Util.getMD5(imageURL);
        //1、使用一级缓存
        bitmap = bitmapLruCache.get(fileMD5);
        if (bitmap == null) {
            //2、一级缓存中没有，执行二级缓存
            bitmap = getFileCache(context, imageURL);
            if (bitmap == null) {
                //三级缓存
                try {
                    URL targetURL = new URL(imageURL);
                    HttpClientUtil.setBitmapToImageView(context, targetURL, imageView);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                //把二级缓存取到的数据，放入以及中
                bitmapLruCache.put(fileMD5, bitmap);
                String tag = (String) imageView.getTag();
                if (tag.equals(imageURL)) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        } else {
            String tag = (String) imageView.getTag();
            if (tag.equals(imageURL)) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
