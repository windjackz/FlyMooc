package com.recker.flymooc;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.recker.flymooc.fragments.CommunityFragment;
import com.recker.flymooc.fragments.CourseFragment;
import com.recker.flymooc.fragments.DownloadFragment;
import com.recker.flymooc.fragments.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.tab_course)
    RelativeLayout mTabCourse;

    @Bind(R.id.tab_download)
    RelativeLayout mTabDownload;

    @Bind(R.id.tab_community)
    RelativeLayout mTabCommunity;

    @Bind(R.id.tab_mine)
    RelativeLayout mTabMine;

    @Bind(R.id.tv_course)
    TextView mTvCourse;

    @Bind(R.id.tv_download)
    TextView mTvDownload;

    @Bind(R.id.tv_community)
    TextView mTVCommunity;

    @Bind(R.id.tv_mine)
    TextView mTvMine;

    private List<Fragment> mFragments;
    private CourseFragment mCourseFragment;
    private CommunityFragment mCoummunityFragment;
    private DownloadFragment mDownloadFragment;
    private MineFragment mMineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupContent();//设置显示内容
        setupTabClick();
    }


    private void setupContent() {
        initFragments();

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
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setOffscreenPageLimit(mFragments.size());
    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position
                , float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            clearTabBackgroundWithTextColor();
            switch (position) {
                case 0:
                    mTabCourse.setBackgroundResource(R.drawable.course_2);
                    mTvCourse.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case 1:
                    mTabDownload.setBackgroundResource(R.drawable.download_2);
                    mTvDownload.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case 2:
                    mTabCommunity.setBackgroundResource(R.drawable.community_2);
                    mTVCommunity.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
                case 3:
                    mTabMine.setBackgroundResource(R.drawable.mine_2);
                    mTvMine.setTextColor(getResources().getColor(R.color.colorPrimary));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void initFragments() {
        mFragments = new ArrayList<>();

        mCourseFragment = new CourseFragment();
        mDownloadFragment = new DownloadFragment();
        mCoummunityFragment = new CommunityFragment();
        mMineFragment = new MineFragment();

        mFragments.add(mCourseFragment);
        mFragments.add(mDownloadFragment);
        mFragments.add(mCoummunityFragment);
        mFragments.add(mMineFragment);
    }


    private void clearTabBackgroundWithTextColor() {
        mTabCourse.setBackgroundResource(R.drawable.course_1);
        mTabDownload.setBackgroundResource(R.drawable.download_1);
        mTabCommunity.setBackgroundResource(R.drawable.community_1);
        mTabMine.setBackgroundResource(R.drawable.mine_1);

        mTvCourse.setTextColor(getResources().getColor(R.color.tab_unselected));
        mTvDownload.setTextColor(getResources().getColor(R.color.tab_unselected));
        mTVCommunity.setTextColor(getResources().getColor(R.color.tab_unselected));
        mTvMine.setTextColor(getResources().getColor(R.color.tab_unselected));
    }

    private void setupTabClick() {
        mTabCourse.setOnClickListener(this);
        mTabDownload.setOnClickListener(this);
        mTabCommunity.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_course:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_download:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab_community:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tab_mine:
                mViewPager.setCurrentItem(3);
                break;
        }
    }
}
