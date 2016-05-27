package com.recker.flymooc.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.recker.flymooc.R;
import com.recker.flymooc.adapters.RaiseAdapter;
import com.recker.flymooc.base.BaseFragment;
import com.recker.flymooc.datas.RaiseData;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/26.
 */
public class RaiseFragment extends BaseFragment implements RaiseAdapter.OnItemClickListener {


    @Bind(R.id.progress)
    ProgressBar mProgressBar;


    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    private RaiseAdapter mAdapter;

    private List<RaiseData> listDatas;

    private int mCurrentPage = 1;

    private String mMark = null;

    public void setMark(String mark) {
        mMark = mark;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_raise;
    }

    @Override
    protected void init() {
        initView();
        new RaiseAsyncTask().execute();
    }


    private void initView() {
        listDatas = new ArrayList<>();
        mAdapter = new RaiseAdapter(getActivity(), listDatas);
        mAdapter.setOnItemClickListener(this);
        GridLayoutManager lm = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void analysisJsonDatas(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    object = array.getJSONObject(i);
                    RaiseData data = new RaiseData();

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setStudyPersons(object.getInt("study_persons"));
                    data.setDescription(object.getString("description"));
                    data.setCourses(object.getInt("courses"));
                    data.setStatus(object.getInt("status"));
                    data.setPathPicFmt(object.getString("path_pic_fmt"));
                    data.setShare(object.getString("share"));

                    listDatas.add(data);
                }

                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private class RaiseAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getRaiseweaponUrl();
            Map<String, String> params = HttpUrl.getInstance()
                    .getRaiseweaponParams(mCurrentPage, mMark);


            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mProgressBar.setVisibility(View.GONE);
            analysisJsonDatas(s);
        }
    }

    private void debug(String string) {
        Log.d(RaiseFragment.class.getSimpleName(), string);
    }
}
