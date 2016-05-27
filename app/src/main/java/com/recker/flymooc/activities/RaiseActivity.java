package com.recker.flymooc.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.recker.flymooc.R;
import com.recker.flymooc.base.BaseActivity;
import com.recker.flymooc.customviews.FlyTabLayout;
import com.recker.flymooc.fragments.RaiseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by recker on 16/5/26.
 */
public class RaiseActivity extends BaseActivity {


    @Bind(R.id.tablayout)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private List<Fragment> mFragments;

    private String[] mTitles = {"全部", "前端", "后端", "移动", "整站"};

    private String[] mMarks = {null, "fe", "be", "mobile", "fsd"};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_raise;
    }

    @Override
    protected void init() {
        setupViewPager();
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
        for (int i = 0; i < mTitles.length; i++) {
            RaiseFragment fragment = new RaiseFragment();
            fragment.setMark(mMarks[i]);
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
}
