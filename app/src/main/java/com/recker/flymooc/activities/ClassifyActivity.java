package com.recker.flymooc.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.recker.flymooc.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/23.
 */
public class ClassifyActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.iv_back)
    ImageView mIvBack;

    @Bind(R.id.iv_search)
    ImageView mIvSearch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);

        setupClick();
    }

    private void setupClick() {
        mIvBack.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:

                break;
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_none, R.anim.slide_out_left);
    }
}
