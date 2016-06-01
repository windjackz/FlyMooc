package com.recker.flymooc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.recker.flymooc.R;
import com.recker.flymooc.datas.CpData;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/6/1.
 */
public class CpAdapter extends BaseAdapter {

    private List<CpData> listDatas;

    private LayoutInflater inflater;

    private Context mContext;

    public CpAdapter(Context context, List<CpData> list) {
        this.listDatas = list;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }


    @Override
    public int getCount() {

        return listDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return listDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (listDatas.get(position).isTitle())
            return 0;

        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CpData data = listDatas.get(i);

        if (getItemViewType(i) == 0)
            return getTitleView(view, data);

        return getContentView(view, data);
    }


    private View getTitleView(View view, CpData data) {

        ViewHolderTitle holder = null;

        if (view == null) {
            holder = new ViewHolderTitle();
            view = inflater.inflate(R.layout.fragment_cp_title, null);

            holder.title = ButterKnife.findById(view, R.id.tv_title);

            view.setTag(holder);
        } else {
            holder = (ViewHolderTitle) view.getTag();
        }

        holder.title.setText(data.getChapterName()+"");

        return view;
    }

    private View getContentView(View view, CpData data) {

        ViewHolderContent holder = null;

        if (view == null) {
            holder = new ViewHolderContent();
            view = inflater.inflate(R.layout.fragment_cp_content, null);

            holder.title = ButterKnife.findById(view, R.id.tv_content);
            holder.image = ButterKnife.findById(view, R.id.iv_play);
            holder.time = ButterKnife.findById(view, R.id.tv_time);

            view.setTag(holder);
        } else {
            holder = (ViewHolderContent) view.getTag();
        }

        holder.title.setText(data.getChapterSeq()+"-"+data.getMediaSeq()
                +"   "+data.getName()+"");
        holder.time.setText(sec2time(data.getDuration()));
        if (data.isSeleted()) {
            holder.image.setImageResource(R.drawable.cp_play_press);
            holder.title.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.image.setImageResource(R.drawable.cp_play_normal);
            holder.title.setTextColor(mContext.getResources().getColor(R.color.cp_second_text));
            holder.time.setTextColor(mContext.getResources().getColor(R.color.cp_second_text));
        }


        return view;
    }



    private static class ViewHolderTitle {
        TextView title;
    }

    private static class ViewHolderContent {
        ImageView image;
        TextView title;
        TextView time;
    }

    /**
     * 秒转化为常见格式
     * @param time
     * @return
     */
    private String sec2time(long time) {
        //初始化Formatter的转换格式。
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String hms = formatter.format(time);

        return hms;
    }

}
