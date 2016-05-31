package com.recker.flymooc.fragments;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.recker.flymooc.R;
import com.recker.flymooc.adapters.ClassifyListAdapter;
import com.recker.flymooc.base.BaseFragment;
import com.recker.flymooc.customviews.RefreshListView;
import com.recker.flymooc.datas.ClassifyData;
import com.recker.flymooc.datas.ClassifyListData;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by recker on 16/5/31.
 */
public class ClassifyListFragment extends BaseFragment implements
        RefreshListView.OnRefreshListener {

    @Bind(R.id.listview)
    RefreshListView mListView;

    private ClassifyListAdapter mAdapter;

    private List<ClassifyListData> listDatas = new ArrayList<>();

    private int mPage = 1;

    private int mType;

    private int mId;

    private boolean mIsRefshing = false;

    private boolean mIsLoadingMore = false;

    public void setType(int type) {
        this.mType = type;
    }

    public void setId(int id) {
        mId = id;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify_list;
    }

    @Override
    protected void init() {
        listDatas.clear();
        mAdapter = new ClassifyListAdapter(getActivity(), listDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(this);

        new ListAsycnTask().execute();
    }

    private void analysisJsonData(String s) {

        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");
            mListView.refreshComplete();

            if (errorCode == 1000) {
                JSONArray array = object.getJSONArray("data");

                for (int i = 0; i < array.length(); i++) {
                    object = array.getJSONObject(i);
                    ClassifyListData data = new ClassifyListData();

                    data.setId(object.getInt("id"));
                    data.setName(object.getString("name"));
                    data.setPic(object.getString("pic"));
                    data.setDesc(object.getString("desc"));
                    data.setIsLearned(object.getInt("is_learned"));
                    data.setCompanyId(object.getInt("company_id"));
                    data.setNumbers(object.getInt("numbers"));
                    data.setUpdateTime(object.getLong("update_time"));
                    data.setCoursetype(object.getInt("coursetype"));
                    data.setDuration(object.getInt("duration"));
                    data.setFinished(object.getInt("finished"));
                    data.setIsFollow(object.getInt("is_follow"));
                    data.setMaxChapterSeq(object.getInt("max_chapter_seq"));
                    data.setMaxMediaSeq(object.getInt("max_media_seq"));
//                    data.setLastTime(object.getInt("last_time"));
//                    data.setChapterSeq(object.getInt("chapter_seq"));
//                    data.setMediaSeq(object.getInt("media_seq"));

//                    debug(data.toString());
                    listDatas.add(data);
                }

                mAdapter.notifyDataSetChanged();
                if (mIsRefshing == true) {
                    toast("刷新成功");
                }
                mIsRefshing = false;
                mIsLoadingMore = false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            mListView.refreshComplete();
        }


    }

    @Override
    public void onRefresh() {
        mPage = 1;
        mIsRefshing = true;
        listDatas.clear();
        new ListAsycnTask().execute();
    }

    @Override
    public void onLoadMore() {
        if (!mIsLoadingMore) {
            mPage++;
            mIsLoadingMore = true;
            new ListAsycnTask().execute();
        }
    }

    private class ListAsycnTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = HttpUrl.getInstance().getClassifListUrl();
            Map<String, String> params = HttpUrl.getInstance().getClassifyListParams(mId, mType, mPage);

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisJsonData(s);
        }
    }

    private void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    private void debug(String str) {
        Log.d(ClassifyListFragment.class.getSimpleName(), str);
    }

}
