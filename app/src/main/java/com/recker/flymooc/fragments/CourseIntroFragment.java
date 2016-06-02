package com.recker.flymooc.fragments;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.recker.flymooc.R;
import com.recker.flymooc.adapters.CourseListAdapter;
import com.recker.flymooc.base.BaseFragment;
import com.recker.flymooc.datas.CourseListData;
import com.recker.flymooc.datas.TeacherData;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/31.
 *
 * 课程介绍
 *
 */
public class CourseIntroFragment extends BaseFragment{

    @Bind(R.id.listview)
    ListView mListView;

    private List<CourseListData> listDatas = new ArrayList<>();

    private CourseListAdapter mAdapter;

    private int mId;

    public void setId(int id) {
        mId = id;
    }

    private View mHeaderView;

    private TextView mTvTitle;

    private ImageView mIvIsFinish;

    private TextView mTvContent;

    private ImageView mImage;

    private TextView mTvName;

    private TextView mTvIntro;

    private RelativeLayout mTeachAllCourse;

    private TeacherData mTeachData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_intro;
    }

    @Override
    protected void init() {

        listDatas.clear();
        //关闭view的OverScroll
        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        mAdapter = new CourseListAdapter(getActivity(), listDatas);
        mListView.setAdapter(mAdapter);

        setupHeaderView();
        new HeaderAsyncTask().execute();
        new ContentAsyncTask().execute();
    }

    private void setupHeaderView() {
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_intro_header, null);

        mTvTitle = ButterKnife.findById(mHeaderView, R.id.tv_title);
        mIvIsFinish = ButterKnife.findById(mHeaderView, R.id.iv_finish);
        mTvContent = ButterKnife.findById(mHeaderView, R.id.tv_content);
        mImage = ButterKnife.findById(mHeaderView, R.id.img);
        mTvName = ButterKnife.findById(mHeaderView, R.id.tv_name);
        mTvIntro = ButterKnife.findById(mHeaderView, R.id.tv_intro);
        mTeachAllCourse = ButterKnife.findById(mHeaderView, R.id.teacher_all_course);

        mListView.addHeaderView(mHeaderView);
    }

    private void analysisHeaderJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                JSONArray array = object.getJSONArray("data");
                object = array.getJSONObject(0);

                mTvTitle.setText(object.getString("course_name"));
                mTvContent.setText(object.getString("course_des"));
                //如果课程未完成，隐藏图标
                if (!object.getString("finished").equals("1")){
                    mIvIsFinish.setVisibility(View.GONE);
                }

                array = object.getJSONArray("teacher_list");
                object = array.getJSONObject(0);
                mTeachData = new TeacherData();
                mTeachData.setUid(object.getInt("uid"));
                mTeachData.setNickname(object.getString("nickname"));
                mTeachData.setAboutme(object.getString("aboutme"));
                mTeachData.setPic(object.getString("pic"));
//                mTeachData.setSex(object.getInt("sex"));
                mTeachData.setIsV(object.getInt("is_v"));

                Picasso.with(getActivity()).load(mTeachData.getPic())
                        .into(mImage);
                mTvName.setText(mTeachData.getNickname()+"");
                mTvIntro.setText(mTeachData.getAboutme()+"");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void analysisContentJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                JSONArray array = object.getJSONArray("data");

                for (int i = 0; i < array.length(); i++) {
                    CourseListData data = new CourseListData();
                    object = array.getJSONObject(i);

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setPic(object.getString("pic"));
                    data.setNumbers(object.getInt("numbers"));
                    data.setDuration(object.getLong("duration"));
                    data.setIsFollow(object.getInt("is_follow"));
                    data.setMaxChapterSeq(object.getInt("max_chapter_seq"));
                    data.setMaxMediaSeq(object.getInt("max_media_seq"));

                    listDatas.add(data);
                }

                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class HeaderAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getCourseIntro();
            Map<String, String> params = HttpUrl.getInstance().getCourseIntroParams(mId+"");

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisHeaderJsonData(s);
        }
    }


    private class ContentAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getRelevantCourse();
            Map<String, String> params = HttpUrl.getInstance().getRelevantCourseParams(mId+"");

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisContentJsonData(s);
        }
    }


    private void debug(String str) {
        Log.d(CourseIntroFragment.class.getSimpleName(), str);
    }

}
