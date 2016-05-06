package com.zzia.graduation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.ui.CheckPlanActivity;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 工作计划列表适配器
 * Created by yunyujing on 16/5/4.
 */
public class PlanListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<BaseBean> list;
    public PlanListAdapter(Context applicationContext, ArrayList<BaseBean> list) {
        this.context=applicationContext;
        this.list=list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.plan_list_recycle_item,null);
        return new PlanListItemViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        PlanListItemViewHOlder planListItemViewHolder= (PlanListItemViewHOlder) holder;
        BaseBean baseBean=list.get(position);
        planListItemViewHolder.title.setText(baseBean.getStr("title"));
        planListItemViewHolder.time.setText(baseBean.getStr("time"));
        planListItemViewHolder.content.setText(baseBean.getStr("content"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PlanListItemViewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title,time,content;
        private View view;

        public PlanListItemViewHOlder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.plan_list_recycle_item_title);
            time= (TextView) itemView.findViewById(R.id.plan_list_recycle_item_time);
            content= (TextView) itemView.findViewById(R.id.plan_list_recycle_item_content);
            view=itemView.findViewById(R.id.plan_list_layout_recycle_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("click","PlanListAcvitity click");
            CheckPlanActivity.startCheckPlanActivity(context);
        }
    }
}
