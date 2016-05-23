package com.recker.flymooc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.recker.flymooc.R;
import com.recker.flymooc.activities.ClassifyActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/23.
 */
public class CourseFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.iv_classify)
    ImageView mIvClassify;

    @Bind(R.id.iv_scan)
    ImageView mIvScan;

    @Bind(R.id.iv_search)
    ImageView mIvSearch;

    @Bind(R.id.iv_study_latest)
    ImageView mIvStudyLatest;

    @Bind(R.id.listview)
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course, container, false);
        ButterKnife.bind(this, view);

        setupClick();

        return view;
    }

    private void setupClick() {
        mIvClassify.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_classify:
                Intent intent1 = new Intent(getActivity(), ClassifyActivity.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
                break;
        }
    }
}
