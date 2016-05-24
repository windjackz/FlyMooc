package com.recker.flymooc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by recker on 16/4/23.
 */
public class HttpRequest {

    private static HttpRequest instance;

    private OkHttpClient client;


    private HttpRequest() {
        client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public static HttpRequest getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new HttpRequest();
                }
            }
        }
        return instance;
    }


    public String GET(String url, Map<String, String> params) {

        StringBuffer sb = new StringBuffer();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            sb.append("?");

            for (Map.Entry<String, String> entry : entrySet) {
                sb.append(entry.getKey());
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        Request request = new Request.Builder().url(url+sb.toString()).get().build();
        Call call = client.newCall(request);

        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String POST(String url, Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder().url(url).post(formBodyBuilder.build()).build();
        Call call = client.newCall(request);

        try {
            return  call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


//    public static String POST(String url) {
//
//        HttpURLConnection conn = null;
//
//        try {
//            URL mURL = new URL(url);
//
//            conn = (HttpURLConnection) mURL.openConnection();
//
//            conn.setRequestMethod("POST");
//            conn.setReadTimeout(5000);//设置读取超时为5秒
//            conn.setConnectTimeout(10000);//设置连接网络超时为10秒
//            conn.setDoOutput(true);//设置允许向服务器输出内容
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode == 200) {
//                InputStream is = conn.getInputStream();
//                String data = readStream(is);
//                return data;
//            }
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                conn.disconnect();//关闭连接
//            }
//        }
//
//        return "";
//    }
//
//
//    public static String GET(String url) {
//
//        HttpURLConnection conn = null;
//
//        try {
//            URL mURL = new URL(url);
//
//            conn = (HttpURLConnection) mURL.openConnection();
//
//            conn.setRequestMethod("GET");
//            conn.setReadTimeout(5000);
//            conn.setConnectTimeout(10000);
//            conn.setRequestProperty("Charset", "utf-8");
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode == 200) {
//                InputStream is = conn.getInputStream();
//                String data = readStream(is);
//                return data;
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                conn.disconnect();//关闭连接
//            }
//        }
//
//        return "";
//    }
//
//    private static String readStream(InputStream is) {
//        InputStreamReader isr;
//        String result = "";
//        try {
//            String line = "";
//            isr = new InputStreamReader(is, "utf-8");
//            BufferedReader br = new BufferedReader(isr);
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }


}
