package com.recker.flymooc.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.recker.flymooc.R;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/25.
 *
 * 求职路线计划详情
 *
 */
public class JobLineDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobline_detail);
        ButterKnife.bind(this);

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_none, R.anim.slide_out_left);
    }

}
