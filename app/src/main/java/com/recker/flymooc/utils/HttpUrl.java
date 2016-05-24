package com.recker.flymooc.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by recker on 16/5/24.
 */
public class HttpUrl {


    private static HttpUrl instance;
    //广告栏请求URL
    private String mBannerUrl = "http://www.imooc.com/api3/getadv";
    //课程列表请求URL
    private String mCourseListUrl = "http://www.imooc.com/api3/courselist_ver2";



    private HttpUrl() {

    }

    public static HttpUrl getInstance() {
        if (instance == null) {
            synchronized (HttpRequest.class) {
                if (instance == null) {
                    instance = new HttpUrl();
                }
            }
        }
        return instance;
    }

    public String getBannerUrl() {
        return mBannerUrl;
    }

    public String getCourseListUrl() {
        return mCourseListUrl;
    }


    /**
     * 获取广告轮播图
     * @return
     */
    public Map<String, String> getBannerParams() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "2902109");
        params.put("marking", "banner");
        params.put("token", "e2b26c9b138b1dba163470fb3379b774");

        return params;
    }


    public Map<String, String> getCourseListParams(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("timestamp", "1464082423816");
        params.put("page", page+"");
        params.put("uid", "2902109");
        params.put("token", "933b40289b2a668bb882e2261a759267");

        return params;
    }

}
