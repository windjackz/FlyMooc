package com.recker.flymooc.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.recker.flymooc.R;
import com.recker.flymooc.activities.ClassifyActivity;
import com.recker.flymooc.activities.CoursePlayActivity;
import com.recker.flymooc.activities.JobLineActivity;
import com.recker.flymooc.activities.RaiseActivity;
import com.recker.flymooc.adapters.CourseListAdapter;
import com.recker.flymooc.base.BaseFragment;
import com.recker.flymooc.customviews.FlyBanner;
import com.recker.flymooc.customviews.RefreshListView;
import com.recker.flymooc.datas.BannerData;
import com.recker.flymooc.datas.CourseListData;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;
import com.recker.flymooc.utils.Loading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/23.
 */
public class CourseFragment extends BaseFragment implements View.OnClickListener
        , RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {

    @Bind(R.id.iv_classify)
    ImageView mIvClassify;

    @Bind(R.id.iv_scan)
    ImageView mIvScan;

    @Bind(R.id.iv_search)
    ImageView mIvSearch;

    @Bind(R.id.iv_study_latest)
    ImageView mIvStudyLatest;

    @Bind(R.id.listview)
    RefreshListView mListView;

    private CourseListAdapter mAdapter;

    private List<CourseListData> mCourseDatas;

    private List<BannerData> mBannerDatas;

    private View mHeaderView;

    private FlyBanner mBanner;

    private LinearLayout mTabOne;//求职路线计划

    private LinearLayout mTabTwo;//加薪利器计划

    private Loading mLoading;

    private int mCurrentPage = 1;//当前页面

    private boolean mIsRefshing = false;//是否正在刷新

    private boolean mIsLoadingMore = false;//是否正在加载更多


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    protected void init() {
        initView();
        setupClick();
        new BannerAsyncTask().execute();
        new CourseListAsyncTask().execute();
    }

    private void initView() {
        mHeaderView = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_course_header, null);
        mListView.addHeaderView(mHeaderView);
        mBanner = ButterKnife.findById(mHeaderView, R.id.banner);
        mTabOne = ButterKnife.findById(mHeaderView, R.id.tab_one);
        mTabTwo = ButterKnife.findById(mHeaderView, R.id.tab_two);

        mCourseDatas = new ArrayList<>();
        mAdapter = new CourseListAdapter(getActivity(), mCourseDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        mLoading = Loading.getInstance(getActivity());
        showLoading();
    }

    /**
     * 设置点击事件
     */
    private void setupClick() {
        mIvClassify.setOnClickListener(this);
        mTabOne.setOnClickListener(this);
        mTabTwo.setOnClickListener(this);
        mIvScan.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
        mIvStudyLatest.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_classify:
                Intent intent1 = new Intent(getActivity(), ClassifyActivity.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
                break;
            case R.id.tab_one:
                Intent intent2 = new Intent(getActivity(), JobLineActivity.class);
                startActivity(intent2);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
                break;
            case R.id.tab_two:
                Intent intent3 = new Intent(getActivity(), RaiseActivity.class);
                startActivity(intent3);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
                break;
            case R.id.iv_scan:

                break;
            case R.id.iv_search:

                break;
            case R.id.iv_study_latest:

                break;
        }
    }

    /**
     * 解析广告栏数据
     * @param s
     */
    private void analysisBannnerJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                mBannerDatas = new ArrayList<>();
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    BannerData data = new BannerData();
                    object = array.getJSONObject(i);

                    data.setId(object.getInt("id"));
                    data.setType(object.getInt("type"));
                    data.setTypeId(object.getInt("type_id"));
                    data.setName(object.getString("name"));
                    data.setPic(object.getString("pic"));
                    data.setLinks(object.getString("links"));

//                    debug(data.toString());
                    mBannerDatas.add(data);
                }
                setBanner();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置广告栏
     */
    private void setBanner() {
        List<String> imgs = new ArrayList<>();
        for (BannerData data : mBannerDatas) {
            imgs.add(data.getPic());
        }
        mBanner.setImagesUrl(imgs);
        mBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }


    /**
     * 解析课程列表数据
     * @param s
     */
    private void analysisCourseListJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");
            mListView.refreshComplete();

            if (errorCode == 1000) {
                JSONArray array = object.getJSONArray("data");

                for (int i = 0; i< array.length(); i++) {
                    CourseListData data = new CourseListData();
                    object = array.getJSONObject(i);

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setPic(object.getString("pic"));
                    data.setDesc(object.getString("desc"));
                    data.setIsLearned(object.getInt("is_learned"));
                    data.setCompanyId(object.getInt("company_id"));
                    data.setNumbers(object.getInt("numbers"));
                    data.setUpdateTime(object.getLong("update_time"));
                    data.setCoursetype(object.getInt("coursetype"));
                    data.setDuration(object.getLong("duration"));
                    data.setFinished(object.getInt("finished"));
                    data.setIsFollow(object.getInt("is_follow"));
                    data.setMaxChapterSeq(object.getInt("max_chapter_seq"));
                    data.setMaxMediaSeq(object.getInt("max_media_seq"));
//                    data.setLastTime(object.getLong("last_time"));
//                    data.setChapterSeq(object.getInt("chapter_seq"));
//                    data.setMediaSeq(object.getInt("media_seq"));

//                    debug(data.toString());
                    mCourseDatas.add(data);
                }
                hideLoading();
                mAdapter.notifyDataSetChanged();

                if (mIsRefshing == true) {
                    toast("刷新成功");
                }
                mIsRefshing = false;
                mIsLoadingMore = false;
            }


        } catch (JSONException e) {
            e.printStackTrace();
            mListView.refreshComplete();
        }
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        mIsRefshing = true;
        mCourseDatas.clear();
        new CourseListAsyncTask().execute();
    }

    @Override
    public void onLoadMore() {
        if (!mIsLoadingMore) {
            mCurrentPage++;
            mIsLoadingMore = true;
            new CourseListAsyncTask().execute();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CourseListData data = mCourseDatas.get(i-2);
        Intent intent = new Intent(getActivity(), CoursePlayActivity.class);
        intent.putExtra("id", data.getId());
        intent.putExtra("title", data.getName());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
    }

    private class BannerAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getBannerUrl();
            Map<String, String> params = HttpUrl.getInstance().getBannerParams();

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisBannnerJsonData(s);
        }
    }


    private class CourseListAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            String url = HttpUrl.getInstance().getCourseListUrl();
            Map<String, String> params = HttpUrl.getInstance().getCourseListParams(mCurrentPage);

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisCourseListJsonData(s);
        }
    }


    private void showLoading() {
        mLoading.show();
        mListView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        mLoading.hide();
        mListView.setVisibility(View.VISIBLE);
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }

    private void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    private void debug(String str) {
        Log.d(CourseFragment.class.getSimpleName(), str);
    }

}
