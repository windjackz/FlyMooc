package com.recker.flymooc.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.recker.flymooc.R;
import com.recker.flymooc.adapters.ClassifyAdapter;
import com.recker.flymooc.base.BaseActivity;
import com.recker.flymooc.datas.ClassifyData;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;
import com.recker.flymooc.utils.Loading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/23.
 *
 * 课程分类
 *
 */
public class ClassifyActivity extends BaseActivity implements
        View.OnClickListener, ClassifyAdapter.OnItemClickListener{

    @Bind(R.id.iv_back)
    ImageView mIvBack;

    @Bind(R.id.iv_search)
    ImageView mIvSearch;

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    @Bind(R.id.progress)
    ProgressBar mProgressBar;

    private List<ClassifyData> listDatas;

    private ClassifyAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_classify;
    }

    @Override
    protected void init() {
        initView();
        new ClassifyAsyncTask().execute();
        setupClick();
    }


    private void initView() {
        listDatas = new ArrayList<>();
        mAdapter = new ClassifyAdapter(this, listDatas);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return mAdapter.isSection(position) ? layoutManager.getSpanCount() : 1 ;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
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

    private void analysisJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                ClassifyData data = new ClassifyData();
                object = object.getJSONObject("data");

                data.setId(object.getInt("id"));
                data.setName(object.getString("name"));
                data.setPic(object.getString("pic"));
                data.setNumbers(object.getInt("numbers"));

                listDatas.add(data);

                JSONArray array = object.getJSONArray("categories");
                for (int i = 0; i < array.length(); i++) {
                    ClassifyData data1 = new ClassifyData();
                    object = array.getJSONObject(i);

                    data1.setId(object.getInt("id"));
                    data1.setName(object.getString("name"));
                    data1.setPic("");
                    data1.setNumbers(0);
                    data1.setTitle(true);

                    listDatas.add(data1);
                    JSONArray array1 = object.getJSONArray("skills");
                    for (int j = 0; j < array1.length(); j++) {
                        object = array1.getJSONObject(j);
                        ClassifyData data2 = new ClassifyData();

                        data2.setId(object.getInt("id"));
                        data2.setName(object.getString("name"));
                        data2.setPic(object.getString("pic"));
                        data2.setNumbers(object.getInt("numbers"));

                        listDatas.add(data2);
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        ClassifyData data = listDatas.get(position);
        if (!data.isTitle()) {
            Intent intent = new Intent(this, ClassifyListActivity.class);
            intent.putExtra("title", data.getName()+"");
            intent.putExtra("url", data.getPic());
            intent.putExtra("id", data.getId());
            startActivity(intent);
        }
    }


    private class ClassifyAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getClassifyCourseUrl();
            Map<String, String> params = HttpUrl.getInstance().getClassifyCourseParams();

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mProgressBar.setVisibility(View.GONE);
            analysisJsonData(s);
        }
    }


    private void debug(String str) {
        Log.d(ClassifyActivity.class.getSimpleName(), str);
    }

}
