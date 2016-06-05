package com.recker.flymooc.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.recker.flymooc.R;

import butterknife.ButterKnife;
import io.vov.vitamio.Vitamio;

/**
 * Created by recker on 16/5/31.
 */
public abstract class BasePlayActivity extends AppCompatActivity {

    protected abstract int getLayoutId();

    protected abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        init();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_none, R.anim.slide_out_left);
    }

}
