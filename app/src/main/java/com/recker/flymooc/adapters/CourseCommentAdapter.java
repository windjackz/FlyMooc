package com.recker.flymooc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.recker.flymooc.R;
import com.recker.flymooc.datas.CourseCommentData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/6/1.
 */
public class CourseCommentAdapter extends BaseAdapter {

    private List<CourseCommentData> listDatas;

    private LayoutInflater inflater;

    private Context mContext;

    public CourseCommentAdapter(Context context, List<CourseCommentData> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        CourseCommentData data = listDatas.get(i);
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.fragment_course_comment_item, null);

            holder.image = ButterKnife.findById(view, R.id.img);
            holder.name = ButterKnife.findById(view, R.id.tv_name);
            holder.time = ButterKnife.findById(view, R.id.tv_time);
            holder.content = ButterKnife.findById(view, R.id.tv_content);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(mContext).load(data.getImg()).into(holder.image);
        holder.name.setText(data.getNickname()+"");
        holder.time.setText(data.getCreateTime()+"");
        holder.content.setText(data.getDescription()+"");

        return view;
    }


    private static class ViewHolder {
        TextView name;
        TextView time;
        TextView content;
        ImageView image;
    }

}
