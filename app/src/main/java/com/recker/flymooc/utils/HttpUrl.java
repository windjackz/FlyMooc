package com.recker.flymooc.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by recker on 16/5/24.
 */
public class HttpUrl {

    private static HttpUrl instance;
    //主机地址
    private String mHostName = "http://www.imooc.com";
    //广告栏请求URL
    private String mBannerUrl = mHostName + "/api3/getadv";
    //课程列表请求URL
    private String mCourseListUrl = mHostName + "/api3/courselist_ver2";
    //课程分类列表URL
    private String mClassifyCourseUrl = mHostName + "/api3/newcourseskill";
    //求职路线计划URL
    private String mJobLineUrl = mHostName +"/api3/program";
    //加薪利器URL
    private String mRaiseweaponUrl = mHostName + "/api3/program";

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

    public String getClassifyCourseUrl() {
        return mClassifyCourseUrl;
    }

    public String getJobLineUrl() {
        return mJobLineUrl;
    }

    public String getRaiseweaponUrl() {
        return mRaiseweaponUrl;
    }

    /**
     * 获取广告轮参数
     * @return
     */
    public Map<String, String> getBannerParams() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "2902109");
        params.put("marking", "banner");
        params.put("token", "e2b26c9b138b1dba163470fb3379b774");

        return params;
    }


    /**
     * 获取课程列表参数
     * @param page
     * @return
     */
    public Map<String, String> getCourseListParams(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("timestamp", "1464082423816");
        params.put("page", page+"");
        params.put("uid", "2902109");
        params.put("token", "933b40289b2a668bb882e2261a759267");

        return params;
    }

    /**
     * 获取课程分类列表参数
     * @return
     */
    public Map<String, String> getClassifyCourseParams() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "2902109");
        params.put("token", "0c383865823b8c7b9146b6e67b4e8b03");

        return params;
    }

    /**
     * 求职路线参数
     * @return
     */
    public Map<String, String> getJobLineParams() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", "2902109");
        params.put("typeid", "1");
        params.put("token", "bcfc00be48e38434381aa1149b41e942");

        return params;
    }

    /**
     * 加薪利器参数
     * @param page
     * @param cid 标识(null代表全部， fe代表前段，be代表后端，mobile代表移动，fsd代表整站)
     * @return
     */
    public Map<String, String> getRaiseweaponParams(int page, String cid) {
        Map<String, String> params = new HashMap<>();

        if (cid != null)
            params.put("cid", cid);
        params.put("uid", "2902109");
        params.put("page", page+"");
        params.put("typeid", "2");
        params.put("token", "bcfc00be48e38434381aa1149b41e942");

        return params;
    }

}
