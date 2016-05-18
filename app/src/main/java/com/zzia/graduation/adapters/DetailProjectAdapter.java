package com.zzia.graduation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.ui.DetailTaskActivity;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 项目详情页适配器
 * Author: yunyujing
 * Date: 2015/12/22
 */
public class DetailProjectAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<BaseBean> list;
    public DetailProjectAdapter(Context context, ArrayList<BaseBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_project_layout_recycle_item,null);
        return new DetailProjectViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailProjectViewHOlder detailProjectViewHOlder= (DetailProjectViewHOlder) holder;
        BaseBean task=list.get(position);
        detailProjectViewHOlder.title.setText(task.getStr("task_name"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class DetailProjectViewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View view;
        private TextView title;
        private TextView time;
        private TextView detail;
        private int position;

        public DetailProjectViewHOlder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.detail_project_layout_content_recycle_item_title);
            time= (TextView) itemView.findViewById(R.id.detail_project_layout_content_recycle_item_time);
            detail= (TextView) itemView.findViewById(R.id.detail_project_layout_content_recycle_item_detail);
            view=itemView.findViewById(R.id.detail_project_layout_content_recycle_item_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DetailTaskActivity.startDetailTaskActivity(context);
        }
    }
}
