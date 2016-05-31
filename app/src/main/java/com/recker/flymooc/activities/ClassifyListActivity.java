package com.recker.flymooc.activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.recker.flymooc.MainActivity;
import com.recker.flymooc.R;
import com.recker.flymooc.base.BaseActivity;
import com.recker.flymooc.fragments.ClassifyListFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by recker on 16/5/27.
 */
public class ClassifyListActivity extends BaseActivity {


    @Bind(R.id.img)
    ImageView mImage;

    @Bind(R.id.tablayout)
    TabLayout mTabLayout;

    @Bind(R.id.relative)
    RelativeLayout mRelative;

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private int mId;

    private String mUrl;

    private String mTitle;

    private Bitmap mBitmap;

    private String[] mTitles = {"全部", "初级", "中级", "高级"};

    private List<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classify_list;
    }

    @Override
    protected void init() {

        mId = getIntent().getIntExtra("id", 0);
        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");

        mTvTitle.setText(mTitle);
        new BitmapAsyncTask().execute(mUrl);
        setupViewPager();
    }


    private class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                mBitmap = Picasso.with(ClassifyListActivity.this).load(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return mBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            mImage.setImageBitmap(bitmap);
            getBitmapColor(bitmap);
        }
    }


    private void getBitmapColor(Bitmap bitmap) {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant =
                        palette.getVibrantSwatch();
                if (vibrant != null) {
                    mRelative.setBackgroundColor(vibrant.getRgb());
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
//        mViewPager.setOffscreenPageLimit(mTitles.length);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void addFragments() {
        mFragments = new ArrayList<>();

        for (int i = 0; i < mTitles.length; i++) {
            ClassifyListFragment fragment = new ClassifyListFragment();
            fragment.setType(i);
            fragment.setId(mId);
            mFragments.add(fragment);
        }

    }


    @OnClick(R.id.iv_back) void back() {
        finish();
    }

}
