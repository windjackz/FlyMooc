package com.recker.flymooc.fragments;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.recker.flymooc.R;
import com.recker.flymooc.adapters.CourseCommentAdapter;
import com.recker.flymooc.base.BaseFragment;
import com.recker.flymooc.datas.CourseCommentData;
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
 *
 * 课程评论
 *
 */
public class CourseCommentFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    @Bind(R.id.listview)
    ListView mListView;


    private List<CourseCommentData> listDatas = new ArrayList<>();

    private CourseCommentAdapter mAdapter;

    private int mPage = 1;

    private int mId;

    public void setId(int id) {
        mId = id;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_comment;
    }

    @Override
    protected void init() {

        mAdapter = new CourseCommentAdapter(getActivity(), listDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        new CommentAsyncTask().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void analysisJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                JSONArray array = object.getJSONArray("data");

                for (int i = 0; i < array.length(); i++) {
                    object = array.getJSONObject(i);
                    CourseCommentData data = new CourseCommentData();

                    data.setId(object.getString("id"));
                    data.setUid(object.getString("uid"));
                    data.setNickname(object.getString("nickname"));
                    data.setImg(object.getString("img"));
                    data.setDescription(object.getString("description"));
                    data.setCreateTime(object.getString("create_time"));
                    data.setSupportNum(object.getString("support_num"));
                    data.setIsSupport(object.getInt("is_support"));
                    data.setMediaId(object.getString("media_id"));
                    data.setChapterSeq(object.getString("chapter_seq"));
                    data.setMediaTitle(object.getString("media_title"));
                    data.setMediaSeq(object.getString("media_seq"));

//                    debug(data.toString());
                    listDatas.add(data);
                }
                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class CommentAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getCourseCommentList();
            Map<String, String> params = HttpUrl.getInstance()
                    .getCourseCommentListParams(mId+"", mPage);

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            analysisJsonData(s);
        }
    }


    private void debug(String str) {
        Log.d(CourseCommentFragment.class.getSimpleName(), str);
    }

}
