package com.recker.flymooc.fragments;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.recker.flymooc.R;
import com.recker.flymooc.adapters.CpAdapter;
import com.recker.flymooc.base.BaseFragment;
import com.recker.flymooc.datas.CpData;
import com.recker.flymooc.events.PlayNextVideo;
import com.recker.flymooc.utils.HttpRequest;
import com.recker.flymooc.utils.HttpUrl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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
 * 章节
 *
 */
public class CpFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.listview)
    ListView mListView;

    private CpAdapter mAdapter;

    private List<CpData> listDatas = new ArrayList<>();

    private int mCurrentPosition = 1;//当前播放视频的位置

    private int mId;

    public void setId(int id) {
        this.mId = id;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cp;
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);

        //关闭view的OverScroll
        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        mAdapter = new CpAdapter(getActivity(), listDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        new CpAsyncTask().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void analysisJsonData(String s) {
        try {
            JSONObject object = new JSONObject(s);
            int errorCode = object.getInt("errorCode");

            if (errorCode == 1000) {
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    object = array.getJSONObject(i);
                    JSONObject object1 = object.getJSONObject("chapter");
                    JSONArray array1 = object.getJSONArray("media");
                    CpData data = new CpData();
//                    Chapter chapter = new Chapter();

                    data.setId(object1.getInt("id"));
                    data.setChapterName(object1.getString("name"));
                    data.setCid(object1.getInt("cid"));
                    data.setSeq(object1.getInt("seq"));
                    data.setTitle(true);
//                    debug(data.toString());
                    listDatas.add(data);

                    for (int j = 0; j < array1.length(); j++) {
                        object = array1.getJSONObject(j);
                        CpData data1 = new CpData();

                        data1.setMediaId(object.getInt("id"));
                        data1.setName(object.getString("name"));
                        data1.setType(object.getInt("type"));
                        data1.setChapterSeq(object.getInt("chapter_seq"));
                        data1.setChapterId(object.getInt("chapter_id"));
                        data1.setMediaSeq(object.getInt("media_seq"));
                        data1.setMediaUrl(object.getString("media_url"));
                        data1.setMediaDownUrl(object.getString("media_down_url"));
                        data1.setDuration(object.getInt("duration"));
                        data1.setLastTime(object.getInt("last_time"));
                        data1.setLastDate(object.getLong("last_date"));
                        data1.setShareUrl(object.getString("share_url"));
                        data1.setHaveQues(object.getInt("have_ques"));
                        data1.setMediaSize(object.getLong("media_size"));
                        data1.setMediaDownSize(object.getLong("media_down_size"));
                        data1.setStatus(object.getInt("status"));

                        if (i == 0 && j == 0) {
                            data1.setSeleted(true);
                        }
//                        debug(data1.toString());
                        listDatas.add(data1);
                    }//for end
                }//for end
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CpData data = listDatas.get(i);
        mCurrentPosition = i;
        if (!data.isTitle()) {
            clearListSelected();
            data.setSeleted(true);
            mAdapter.notifyDataSetChanged();
            if (mListener != null) {
                mListener.onPlayVideo(data.getMediaUrl());
            }
        }
    }

    @Subscribe
    public void onEvent(PlayNextVideo event) {
//        debug("*************");
        for (int i = mCurrentPosition; i < listDatas.size(); i++) {
            CpData data = listDatas.get(i);
            if (!data.isTitle()){//不是标题数据
                if (i > mCurrentPosition) {//跳到下一个视频
                    clearListSelected();
                    data.setSeleted(true);
                    mAdapter.notifyDataSetChanged();
                    if (mListener != null) {
                        mListener.onPlayVideo(data.getMediaUrl());
                    }
                    mCurrentPosition = i;
                    break;
                }
            }
        }

    }

    private void clearListSelected() {
        for (CpData data : listDatas)
            data.setSeleted(false);
    }

    private class CpAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String url = HttpUrl.getInstance().getCpInfo();
            Map<String,String> params = HttpUrl.getInstance().getCpInfoParams(mId+"");

            return HttpRequest.getInstance().POST(url, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            analysisJsonData(s);
        }
    }

    private PlayVideoListener mListener;

    public void setPlayVideoListener(PlayVideoListener listener) {
        mListener = listener;
    }

    public interface PlayVideoListener {
        //点击播放视频
        void onPlayVideo(String url);
    }


    private void debug(String str) {
        Log.d(CpFragment.class.getSimpleName(), str);
    }
}
