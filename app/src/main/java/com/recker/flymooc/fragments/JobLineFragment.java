package com.recker.flymooc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recker.flymooc.R;
import com.recker.flymooc.activities.ClassifyActivity;
import com.recker.flymooc.activities.JobLineDetailActivity;
import com.recker.flymooc.base.BaseFragment;
import com.recker.flymooc.datas.JobLineData;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by recker on 16/5/25.
 */
public class JobLineFragment extends BaseFragment {

    @Bind(R.id.img)
    ImageView mImage;

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.tv_course)
    TextView mTvCourse;

    @Bind(R.id.tv_number)
    TextView mTvNumber;

    private JobLineData mData;

    public void setData(JobLineData data) {
        mData = data;
    }

    @OnClick(R.id.lin) void onClick() {
        if (mData != null) {
            Intent intent = new Intent(getActivity(), JobLineDetailActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_none);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_jobline;
    }

    @Override
    protected void init() {
        Picasso.with(getActivity()).load(mData.getPathPicFmt()).into(mImage);
        mTvTitle.setText(mData.getName()+"");
        mTvCourse.setText(mData.getCourses()+"门课程");
        mTvNumber.setText(mData.getStudyPersons()+"人在学");
    }

}
