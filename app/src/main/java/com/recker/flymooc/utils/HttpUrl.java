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
    //分类课程列表
    private String mClassifyListUrl = mHostName+"/api3/courselist_ver2";
    //社区文章
    private String mArticleListUrl = mHostName + "/api3/articlelist";
    //文章内容
    private String mArticleContent = mHostName + "/api3/articlecontent";
    //获取视频信息
    private String mMediaInfo = mHostName + "/api3/getmediainfo_ver2";
    //获取视频章节信息
    private String mCpInfo = mHostName + "/api3/getcpinfo_ver2";
    //获取课程评论列表
    private String mCourseCommentList = mHostName + "/api3/coursecommentlist";
    //获取课程简介信息
    private String mCourseIntro = mHostName + "/api3/getcourseintro";
    //获取推荐课程列表
    private String mRelevantCourse = mHostName + "/api3/getrelevantcourse";

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

    public String getClassifListUrl() {
        return mClassifyListUrl;
    }

    public String getArticleListUrl() {
        return mArticleListUrl;
    }

    public String getArticleContent() {
        return mArticleContent;
    }

    public String getMediaInfo() {
        return mMediaInfo;
    }

    public String getCpInfo() {
        return mCpInfo;
    }

    public String getCourseCommentList() {
        return mCourseCommentList;
    }

    public String getCourseIntro() {
        return mCourseIntro;
    }

    public String getRelevantCourse() {
        return mRelevantCourse;
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
        params.put("timestamp", System.currentTimeMillis()+"");
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


    /**
     * 获取分类课程列表参数
     * @param type 1为全部, 2为初级, 3为中级，4为高级
     * @param page
     * @return
     */
    public Map<String, String> getClassifyListParams(int id, int type, int page) {
        Map<String, String> params = new HashMap<>();

        params.put("cat_type", id+"");
        params.put("timestamp", System.currentTimeMillis()+"");
        params.put("uid", "2902109");
        params.put("page", page + "");
        params.put("all_type", "0");
        params.put("easy_type", type+"");
        params.put("token", "933b40289b2a668bb882e2261a759267");

        return params;
    }

    /**
     * 获取文章列表参数
     * @param id 0为全部, 105为前端开发，106为后端开发，107为职场/生活，109为设计
     *           110为移动开发，114为其它类干货
     * @param page
     * @return
     */
    public Map<String, String> getArticleListParams(String id, int page) {
        Map<String, String> params = new HashMap<>();

        params.put("typeid", id);
        params.put("uid", "2902109");
        params.put("page", page + "");
        params.put("type", "0");
        params.put("aid", "0");
        params.put("token", "c0581c2f1a83e264a9bf1d74a0773ebd");

        return params;
    }

    /**
     * 获取文章内容参数
     * @param id
     * @return
     */
    public Map<String, String> getArticleContentParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("typeid", id);
        params.put("uid", "2902109");
        params.put("token", "2076cc47b14f0f8509e2cfd4358bb71e");

        return params;
    }

    /**
     * 获取视频信息参数
     * @param id
     * @return
     */
    public Map<String, String> getMediaInfoParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("token", "ee292734814af1f4804f676c51e3337d");

        return params;
    }

    /**
     * 获取视频章节参数
     * @param id
     * @return
     */
    public Map<String, String> getCpInfoParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("token", "56f37d20c0e34b9980b3cf910e35f342");

        return params;
    }

    /**
     * 获取课程评论列表参数
     * @param id
     * @param page
     * @return
     */
    public Map<String, String> getCourseCommentListParams(String id, int page) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("page", page+"");
        params.put("token", "ec146a4a50030d232784e0e048827e36");

        return params;
    }

    /**
     * 获取课程简介参数
     * @param id
     * @return
     */
    public Map<String, String> getCourseIntroParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("token", "54368c2b6bdb7bd22505ba6666ad5dad");

        return params;
    }

    /**
     * 获取推荐课程列表参数
     * @param id
     * @return
     */
    public Map<String, String> getRelevantCourseParams(String id) {
        Map<String, String> params = new HashMap<>();

        params.put("cid", id);
        params.put("uid", "2902109");
        params.put("token", "88433c772ee11aa5eb997249b650e7c8");

        return params;
    }

}
