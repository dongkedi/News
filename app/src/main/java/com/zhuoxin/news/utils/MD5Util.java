package com.zhuoxin.news.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/12/2.
 */

public class MD5Util {
    public static String getMD5(String message) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");//Base64
            messageDigest.update(message.getBytes("UTF-8"));//获取信息到数据指纹中
            byte data[] = messageDigest.digest();//取出数据，存到数据中。这个数组是16位长度
            //for循环对data中的数据进行16进制的转换
            for (int i = 0; i < data.length; i++) {
                String str = Integer.toHexString(0xff & data[i]);//把对应设置的字节转换成16进制字符串
                if (str.length() == 1) {
                    sb.append("0").append(str);
                } else {
                    sb.append(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
