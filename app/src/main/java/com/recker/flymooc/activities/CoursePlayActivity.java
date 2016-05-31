package com.recker.flymooc.activities;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.recker.flymooc.R;
import com.recker.flymooc.base.BaseActivity;
import com.recker.flymooc.base.BasePlayActivity;
import com.recker.flymooc.datas.MediaData;
import com.recker.flymooc.fragments.CourseCommentFragment;
import com.recker.flymooc.fragments.CourseIntroFragment;
import com.recker.flymooc.fragments.CpFragment;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by recker on 16/5/31.
 */
public class CoursePlayActivity extends BasePlayActivity {

    @Bind(R.id.tv_title)
    TextView mTextView;

    @Bind(R.id.videoView)
    VideoView mVideoView;

    @Bind(R.id.tablayout)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private int mId;

    private String mTitle;

    private MediaData mMediaData;

    private String[] mTitles = {"章节", "评论", "详情"};

    private List<Fragment> mFragments;

    private CpFragment mCpFragment;

    private CourseCommentFragment mCommentFragmet;

    private CourseIntroFragment mIntroFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_play;
    }

    @Override
    protected void init() {
        mId = getIntent().getIntExtra("id", 0);
        mTitle = getIntent().getStringExtra("title");

        mTextView.setText(mTitle+"");

        new MediaAsyncTask().execute();
        setupViewPager();
    }

    private void analysisJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                mMediaData = new MediaData();
                object = object.getJSONObject("data");
                JSONObject object1 = object.getJSONObject("course");
                object = object.getJSONObject("media");

                mMediaData.setMediaId(object.getInt("id"));
                mMediaData.setName(object.getString("name"));
                mMediaData.setType(object.getInt("type"));
                mMediaData.setShareUrl(object.getString("share_url"));
                mMediaData.setChapterId(object.getInt("chapter_id"));
                mMediaData.setChapterSeq(object.getInt("chapter_seq"));
                mMediaData.setMediaSeq(object.getInt("media_seq"));
                mMediaData.setLastTime(object.getInt("last_time"));
                mMediaData.setLastDate(object.getInt("last_date"));
                mMediaData.setStatus(object.getInt("status"));
                mMediaData.setHaveQues(object.getInt("have_ques"));
                mMediaData.setDuration(object.getInt("duration"));
                mMediaData.setMediaSize(object.getLong("media_size"));
                mMediaData.setMediaDownSize(object.getLong("media_down_size"));
                mMediaData.setMediaUrl(object.getString("media_url"));
                mMediaData.setMediaDownUrl(object.getString("media_down_url"));
                mMediaData.setCourseId(object1.getInt("id"));
                mMediaData.setIsFollow(object1.getInt("is_follow"));

//                debug(mMediaData.toString());
                setupPlay();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setupPlay() {

        if (mMediaData == null) return;

        //设置为画面拉伸,全屏
//        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
//        mMediaController = new MediaController(this);
//        mVideoView.setMediaController(mMediaController);
//        mVideoView.requestFocus();
        mVideoView.setVideoPath(mMediaData.getMediaUrl());
    }

    private void setupViewPager() {
        addFragments();

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        };
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void addFragments() {
        mFragments = new ArrayList<>();

        mCpFragment = new CpFragment();
        mCommentFragmet = new CourseCommentFragment();
        mIntroFragment = new CourseIntroFragment();

        mFragments.add(mCpFragment);
        mFragments.add(mCommentFragmet);
        mFragments.add(mIntroFragment);
    }



    private class MediaAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getMediaInfo();
            Map<String, String> params = HttpUrl.getInstance().getMediaInfoParams(mId+"");

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisJsonData(s);
        }
    }


    @OnClick(R.id.iv_back) void onClick() {
        finish();
    }


    private void debug(String str) {
        Log.d(CoursePlayActivity.class.getSimpleName(), str);
    }

}
