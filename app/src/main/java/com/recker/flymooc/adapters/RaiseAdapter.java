package com.recker.flymooc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recker.flymooc.R;
import com.recker.flymooc.datas.RaiseData;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by recker on 16/5/26.
 */
public class RaiseAdapter extends RecyclerView.Adapter<RaiseAdapter.ViewHolder> {

    private List<RaiseData> listDatas;

    private Context mContext;

    public RaiseAdapter(Context context, List<RaiseData> list) {
        this.listDatas = list;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_raise_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RaiseData data = listDatas.get(position);
        Picasso.with(mContext).load(data.getPathPicFmt()).into(holder.image);
        holder.title.setText(data.getName()+"");
        holder.course.setText(data.getCourses()+"门课程");
        holder.number.setText(data.getStudyPersons()+"人在学");
    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        TextView title;

        TextView course;

        TextView number;


        public ViewHolder(final View itemView) {
            super(itemView);

            image = ButterKnife.findById(itemView, R.id.img);
            title = ButterKnife.findById(itemView, R.id.tv_title);
            course = ButterKnife.findById(itemView, R.id.tv_course);
            number = ButterKnife.findById(itemView, R.id.tv_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
