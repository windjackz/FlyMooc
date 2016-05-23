package com.recker.flymooc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recker.flymooc.R;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/23.
 */
public class CommunityFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
