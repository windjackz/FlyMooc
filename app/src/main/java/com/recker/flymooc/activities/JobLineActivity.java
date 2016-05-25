package com.recker.flymooc.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.recker.flymooc.R;
import com.recker.flymooc.datas.JobLineData;
import com.recker.flymooc.fragments.JobLineFragment;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;
import com.recker.flymooc.utils.Loading;
import com.recker.flymooc.utils.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by recker on 16/5/25.
 *
 * 求职路线计划
 *
 */
public class JobLineActivity extends AppCompatActivity {


    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private List<JobLineData> listDatas;

    private List<JobLineFragment> mFragments;

    private Loading mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobline);
        ButterKnife.bind(this);

        mLoading = Loading.getInstance(this);
        mLoading.show();
        new JobLineAsyncTask().execute();
    }


    private void analysisJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                listDatas = new ArrayList<>();
                JSONArray array = object.getJSONArray("data");

                for (int i = 0; i< array.length(); i++) {
                    JobLineData data = new JobLineData();
                    object = array.getJSONObject(i);

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setStudyPersons(object.getInt("study_persons"));
                    data.setDescription(object.getString("description"));
                    data.setCourses(object.getInt("courses"));
                    data.setStatus(object.getInt("status"));
                    data.setPathPicFmt(object.getString("path_pic_fmt"));
                    data.setShare(object.getString("share"));

                    listDatas.add(data);
                }
                mLoading.hide();
                setupViewPager();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class JobLineAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getJobLineUrl();
            Map<String, String> params = HttpUrl.getInstance().getJobLineParams();

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisJsonData(s);
        }
    }


    private void setupViewPager() {
        initFragments();

        if (mFragments == null || mFragments.size() < 1) {
            return;
        }


        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
    }

    private void initFragments() {
        if (listDatas == null || listDatas.size() < 1) {
            return;
        }

        mFragments = new ArrayList<>();
        for (JobLineData data : listDatas) {
            JobLineFragment fragment = new JobLineFragment();
            fragment.setData(data);
            mFragments.add(fragment);
        }
    }


    @OnClick(R.id.iv_back) void back() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_none, R.anim.slide_out_left);
    }

    private void debug(String string) {
        Log.d(JobLineActivity.class.getSimpleName(), string);
    }

}
