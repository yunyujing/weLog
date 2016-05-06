package com.zzia.graduation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.ui.CheckSummaryActivity;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 工作总结列表适配器
 * Created by yunyujing on 16/5/6.
 */
public class SummaryListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<BaseBean> list;
    public SummaryListAdapter(Context context, ArrayList<BaseBean> list){

        this.context=context;
        this.list=list;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.summary_list_recycle_item,null);
        return new SummaryListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SummaryListAdapterViewHolder summaryListAdapterViewHolder= (SummaryListAdapterViewHolder) holder;
        BaseBean baseBean=list.get(position);
        summaryListAdapterViewHolder.title.setText(baseBean.getStr("title"));
        summaryListAdapterViewHolder.time.setText(baseBean.getStr("time"));
        summaryListAdapterViewHolder.content.setText(baseBean.getStr("content"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SummaryListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title,time,content;
        private View view;

        public SummaryListAdapterViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.summary_list_recycle_item_title);
            time= (TextView) itemView.findViewById(R.id.summary_list_recycle_item_time);
            content= (TextView) itemView.findViewById(R.id.summary_list_recycle_item_content);
            view=itemView.findViewById(R.id.summary_list_layout_recycle_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("click","CheckListAcvitity click");
            CheckSummaryActivity.startCheckSummaryActivity(context);
        }
    }
}
