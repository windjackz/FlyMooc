package com.recker.flymooc.activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.recker.flymooc.MainActivity;
import com.recker.flymooc.R;
import com.recker.flymooc.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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

    private String mUrl;

    private String mTitle;

    private Bitmap mBitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_classify_list;
    }

    @Override
    protected void init() {

        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");


//        Picasso.with(this).load(mUrl).into(mImage);
        mTvTitle.setText(mTitle);
        new BitmapAsyncTask().execute(mUrl);
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


    @OnClick(R.id.iv_back) void back() {
        finish();
    }

}
