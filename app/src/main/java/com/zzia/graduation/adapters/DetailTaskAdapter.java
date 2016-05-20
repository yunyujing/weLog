package com.zzia.graduation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/8.
 */
public class DetailTaskAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<BaseBean> list;
    private ArrayList<String> imageList = new ArrayList<>();

    public DetailTaskAdapter(Context context, ArrayList<BaseBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_task_layout_comment_recycle_item, null);
        return new DetailTaskAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailTaskAdapterViewHolder detailTaskAdapterViewHolder = (DetailTaskAdapterViewHolder) holder;
        BaseBean baseBean = list.get(position);
        detailTaskAdapterViewHolder.name.setText(baseBean.getStr("comment_creater"));
        detailTaskAdapterViewHolder.time.setText(baseBean.getStr("comment_createtime"));
        detailTaskAdapterViewHolder.comment.setText(baseBean.getStr("comment_content"));
        imageList = (ArrayList<String>) baseBean.get("comment_image");
        if (imageList != null && imageList.size() > 0) {
            detailTaskAdapterViewHolder.gridView.setVisibility(View.VISIBLE);
            detailTaskAdapterViewHolder.gridView.setAdapter(new CheckLargeImageGridAdapter(context, imageList));

        }else {
            detailTaskAdapterViewHolder.gridView.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DetailTaskAdapterViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView headIcon;
        private TextView name;
        private TextView comment;
        private TextView time;
        private GridView gridView;

        public DetailTaskAdapterViewHolder(View itemView) {
            super(itemView);
            headIcon = (SimpleDraweeView) itemView.findViewById(R.id.detail_task_layout_comment_recycle_item_icon);
            name = (TextView) itemView.findViewById(R.id.detail_task_layout_comment_recycle_item_name);
            comment = (TextView) itemView.findViewById(R.id.detail_task_layout_comment_recycle_item_content);
            time = (TextView) itemView.findViewById(R.id.detail_task_layout_comment_recycle_item_time);
            gridView = (GridView) itemView.findViewById(R.id.detail_task_layout_commetn_recycle_item_grid);

        }
    }
}
