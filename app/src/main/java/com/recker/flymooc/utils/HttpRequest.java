package com.recker.flymooc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by recker on 16/4/23.
 */
public class HttpRequest {

    public static String POST(String url) {

        HttpURLConnection conn = null;

        try {
            URL mURL = new URL(url);

            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);//设置读取超时为5秒
            conn.setConnectTimeout(10000);//设置连接网络超时为10秒
            conn.setDoOutput(true);//设置允许向服务器输出内容

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String data = readStream(is);
                return data;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();//关闭连接
            }
        }

        return "";
    }


    public static String GET(String url) {

        HttpURLConnection conn = null;

        try {
            URL mURL = new URL(url);

            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("Charset", "utf-8");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String data = readStream(is);
                return data;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();//关闭连接
            }
        }

        return "";
    }

    private static String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
