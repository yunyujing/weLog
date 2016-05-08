package com.zzia.graduation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.ui.CheckOverTimeActivity;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/8.
 */

public class OverTimeListAdapter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<BaseBean> list;
    public OverTimeListAdapter(Context context, ArrayList<BaseBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.overtime_list_recycle_item,null);
        return new OverTimeListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OverTimeListAdapterViewHolder overTimeListAdapterViewHolder= (OverTimeListAdapterViewHolder) holder;
        BaseBean baseBean=list.get(position);
        overTimeListAdapterViewHolder.title.setText(baseBean.getStr("title"));
        overTimeListAdapterViewHolder.time.setText(baseBean.getStr("time"));
        overTimeListAdapterViewHolder.content.setText(baseBean.getStr("content"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OverTimeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView time,title,content;
        private View view;

        public OverTimeListAdapterViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.overtime_list_recycle_item_title);
            time= (TextView) itemView.findViewById(R.id.overtime_list_recycle_item_time);
            content= (TextView) itemView.findViewById(R.id.overtime_list_recycle_item_content);
            view=itemView.findViewById(R.id.overtime_list_layout_recycle_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            CheckOverTimeActivity.startCheckOverTimeActivity(context,"OverTimeListAdapter");
        }
    }
}
