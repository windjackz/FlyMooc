package com.recker.flymooc.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.recker.flymooc.R;
import com.recker.flymooc.activities.ClassifyActivity;
import com.recker.flymooc.adapters.CourseListAdapter;
import com.recker.flymooc.customviews.FlyBanner;
import com.recker.flymooc.datas.BannerData;
import com.recker.flymooc.datas.CourseListData;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;
import com.recker.flymooc.utils.Loading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/23.
 */
public class CourseFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.iv_classify)
    ImageView mIvClassify;

    @Bind(R.id.iv_scan)
    ImageView mIvScan;

    @Bind(R.id.iv_search)
    ImageView mIvSearch;

    @Bind(R.id.iv_study_latest)
    ImageView mIvStudyLatest;

    @Bind(R.id.listview)
    ListView mListView;

    private CourseListAdapter mAdapter;

    private List<CourseListData> mCourseDatas;

    private List<BannerData> mBannerDatas;

    private View mHeaderView;

    private FlyBanner mBanner;


    private Loading mLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course, container, false);
        ButterKnife.bind(this, view);

        init();
        setupClick();
        new BannerAsyncTask().execute();
        new CourseListAsyncTask().execute();

        return view;
    }


    private void init() {
        mHeaderView = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_course_header, null);
        mListView.addHeaderView(mHeaderView);
        mBanner = ButterKnife.findById(mHeaderView, R.id.banner);

        mCourseDatas = new ArrayList<>();
        mAdapter = new CourseListAdapter(getActivity(), mCourseDatas);
        mListView.setAdapter(mAdapter);
        mLoading = Loading.getInstance(getActivity());
        showLoading();
    }

    /**
     * 设置点击事件
     */
    private void setupClick() {
        mIvClassify.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_classify:
                Intent intent1 = new Intent(getActivity(), ClassifyActivity.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
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
                    data.setLastTime(object.getLong("last_time"));
                    data.setChapterSeq(object.getInt("chapter_seq"));
                    data.setMediaSeq(object.getInt("media_seq"));

//                    debug(data.toString());
                    mCourseDatas.add(data);
                }
                hideLoading();
                mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            Map<String, String> params = HttpUrl.getInstance().getCourseListParams(1);

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

    private void debug(String str) {
        Log.d(CourseFragment.class.getSimpleName(), str);
    }

}
