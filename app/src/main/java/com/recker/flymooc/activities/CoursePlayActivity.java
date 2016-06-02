package com.recker.flymooc.activities;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.recker.flymooc.R;
import com.recker.flymooc.base.BasePlayActivity;
import com.recker.flymooc.datas.MediaData;
import com.recker.flymooc.events.PlayNextVideo;
import com.recker.flymooc.fragments.CourseCommentFragment;
import com.recker.flymooc.fragments.CourseIntroFragment;
import com.recker.flymooc.fragments.CpFragment;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by recker on 16/5/31.
 */
public class CoursePlayActivity extends BasePlayActivity implements CpFragment.PlayVideoListener {

    private static final int VIDEO_PALY = 0;

    private static final int VIDEO_BG = 1;

    private static final int SEC = 1000;//一秒钟时间

    private static final int HIDE_TIME = 3500;//隐藏控制栏时间

    @Bind(R.id.tv_title)
    TextView mTextView;

    @Bind(R.id.videoView)
    VideoView mVideoView;

    @Bind(R.id.tablayout)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.progress_start)
    ProgressBar mProgressBarStart;

    @Bind(R.id.video_bg)
    RelativeLayout mVideoBg;

    @Bind(R.id.play_control)
    ImageView mPlayControl;

    @Bind(R.id.seekbar)
    SeekBar mSeekBar;

    @Bind(R.id.tv_time)
    TextView mTvTime;

    @Bind(R.id.iv_fullscreen)
    ImageView mIvFullScreen;

    private int mId;

    private String mTitle;

    private MediaData mMediaData;

    private String[] mTitles = {"章节", "评论", "详情"};

    private List<Fragment> mFragments;

    private CpFragment mCpFragment;

    private CourseCommentFragment mCommentFragmet;

    private CourseIntroFragment mIntroFragment;

    //总时长字符串
    private String mTotalDurationStr;
    //总时长
    private long mTotalDuration;
    //开始时长
    private long startDuration = 0;
    //是否在滑动拖块
    private boolean mIsSlide = false;
    //是否播放结束
    private boolean mIsPlayEnd = false;

    /**
     * 设置时间控制
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == VIDEO_PALY) {//设置时间控制
                startDuration += SEC;
                //设置时间显示
                if (startDuration >= mTotalDuration) {
                    startDuration = mTotalDuration;
                }
                mTvTime.setText(sec2time(startDuration) + "/" + mTotalDurationStr);
                //设置SeekBar的进度
                if (!mIsSlide) {
                    int progress = (int) ((startDuration * 1.0 / mTotalDuration) * 1000);
                    mSeekBar.setProgress(progress);
//                    mSeekBar.setSecondaryProgress(progress+100);
                }
                //继续轮回
                mHandler.sendEmptyMessageDelayed(VIDEO_PALY, SEC);
            }
            if (msg.what == VIDEO_BG) {//隐藏控制栏
                mVideoBg.setVisibility(View.GONE);
            }
        }
    };

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
        setupSeekBarChanged();
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
        setupPlayListener();
    }


    private void setupPlayListener() {
        //预处理完成调用
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //视频信息加载完成，把开始加载条隐藏
                mProgressBarStart.setVisibility(View.GONE);
                //视频控制器变为播放
                mPlayControl.setImageResource(R.drawable.video_pause);
                //设置播放时长
                mTotalDuration =  mp.getDuration();
                mTotalDurationStr = sec2time(mTotalDuration);
                //开始播放
                startVideo();
            }
        });
        //视频缓冲调用
        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        if (mVideoView.isPlaying()) {
                            mVideoView.pause();
                        }
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mVideoView.start();
                        break;
                }

                return true;
            }
        });
        //视频播放完成
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //暂停播放
                pauseVideo();
                mIsPlayEnd = true;
                EventBus.getDefault().post(new PlayNextVideo());
            }
        });

        mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                debug("buffer-->"+mp.getBufferProgress());
            }
        });

    }

    private void setupSeekBarChanged() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始滑动拖块，不隐藏
                mHandler.removeMessages(VIDEO_BG);
                //将值设置为真，此时不会自动改变滑动位置
                mIsSlide = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //滑动完后设置为假
                mIsSlide = false;
                startDuration = (long) ((seekBar.getProgress()*1.0 / 1000) * mTotalDuration);
                mTvTime.setText(sec2time(startDuration) + "/" + mTotalDurationStr);
                mVideoView.seekTo(startDuration);

                if (mIsPlayEnd) {
                    mIsPlayEnd = false;
                    startVideo();
                } else {
                    mHandler.sendEmptyMessageDelayed(VIDEO_BG, HIDE_TIME);
                }
            }
        });
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
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void addFragments() {
        mFragments = new ArrayList<>();

        mCpFragment = new CpFragment();
        mCommentFragmet = new CourseCommentFragment();
        mIntroFragment = new CourseIntroFragment();

        mCpFragment.setId(mId);
        mCommentFragmet.setId(mId);
        mIntroFragment.setId(mId);

        mCpFragment.setPlayVideoListener(this);

        mFragments.add(mCpFragment);
        mFragments.add(mCommentFragmet);
        mFragments.add(mIntroFragment);
    }

    @Override
    public void onPlayVideo(String url) {
        mVideoView.setVideoPath(url);

        pauseVideo();
        startDuration = 0;
        mTvTime.setText(sec2time(startDuration) + "/" + mTotalDurationStr);
        mSeekBar.setProgress(0);
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



    //视频播放控制按钮
    @OnClick(R.id.play_control) void setupPlayClick() {
        if (mVideoView != null) {
            if (mVideoView.isPlaying()) {
                pauseVideo();
            } else {
                startVideo();
            }
        }
    }

    //全屏键
    @OnClick(R.id.iv_fullscreen) void fullScreen() {

    }

    //显示视频控制器
    @OnClick(R.id.main_view) void showVideoControl() {
        if (mVideoBg.getVisibility() == View.GONE) {
            mVideoBg.setVisibility(View.VISIBLE);
            if (mVideoView.isPlaying()) {
                //显示之后过3秒隐藏
                mHandler.sendEmptyMessageDelayed(VIDEO_BG, HIDE_TIME);
            }
        } else {
            mVideoBg.setVisibility(View.GONE);
        }
    }

    //返回键
    @OnClick(R.id.iv_back) void onBack() {
        finish();
    }


    /**
     * 开始视频播放
     */
    private void startVideo() {
        mPlayControl.setImageResource(R.drawable.video_pause);
        mVideoView.start();
        mHandler.sendEmptyMessageDelayed(VIDEO_PALY, SEC);
        if (mVideoBg.getVisibility() == View.VISIBLE && mVideoView.isPlaying()) {
            //准备隐藏控制栏
            mHandler.sendEmptyMessageDelayed(VIDEO_BG, HIDE_TIME);
        }
    }

    /**
     * 暂停视频播放
     */
    private void pauseVideo() {
        mPlayControl.setImageResource(R.drawable.video_play);
        mVideoView.pause();
        mHandler.removeMessages(VIDEO_PALY);
        mHandler.removeMessages(VIDEO_BG);
        //显示控制栏
        mVideoBg.setVisibility(View.VISIBLE);
    }

    /**
     * 秒转化为常见格式
     * @param time
     * @return
     */
    private String sec2time(long time) {
        //转换格式
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String hms = formatter.format(time);

        return hms;
    }

    private void debug(String str) {
        Log.d(CoursePlayActivity.class.getSimpleName(), str);
    }

}
