package com.zzia.graduation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.ui.CreateClaimActivity;
import com.zzia.graduation.ui.CreateHolidayActivity;
import com.zzia.graduation.ui.CreateOverActivity;
import com.zzia.graduation.ui.CreatePlanActivity;
import com.zzia.graduation.ui.CreateSummaryActivity;
import com.zzia.graduation.ui.DetailProjectActivity;
import com.zzia.graduation.ui.DetailTaskActivity;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/24.
 */
public class SearchFragmentAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<BaseBean> list;

    public SearchFragmentAdapter(Context context, ArrayList<BaseBean> arrayList) {

        this.context = context;
        this.list = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_list_recycle_item, null);
        return new PlanListItemViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlanListItemViewHOlder planListItemViewHolder = (PlanListItemViewHOlder) holder;
        BaseBean baseBean = list.get(position);
        planListItemViewHolder.position = position;
        int state = baseBean.getInt("check_state");
        String checkState = null;
        switch (state) {
            case 0:
                checkState = "待审核";
                break;
            case 1:
                checkState = "通过";
                break;
            case 2:
                checkState = "驳回";
                break;
        }

        if (list.get(position).containKey("plan_title")) {
            planListItemViewHolder.title.setText(checkState);
            planListItemViewHolder.time.setText("#工作计划#");
            planListItemViewHolder.content.setText(baseBean.getStr("plan_title"));
        } else if (list.get(position).containKey("summary_title")) {
            planListItemViewHolder.title.setText(checkState);
            planListItemViewHolder.time.setText("#工作总结#");
            planListItemViewHolder.content.setText(baseBean.getStr("summary_title"));
        } else if (list.get(position).containKey("over_content")) {
            planListItemViewHolder.title.setText(checkState);
            planListItemViewHolder.time.setText("#加班认定#");
            planListItemViewHolder.content.setText(baseBean.getStr("over_content"));
        } else if (list.get(position).containKey("leave_reason")) {
            planListItemViewHolder.title.setText(checkState);
            planListItemViewHolder.time.setText("#休假申请#");
            planListItemViewHolder.content.setText(baseBean.getStr("leave_reason"));
        } else if (list.get(position).containKey("claim_reason")) {
            planListItemViewHolder.title.setText(checkState);
            planListItemViewHolder.time.setText("#报销审批#");
            planListItemViewHolder.content.setText(baseBean.getStr("claim_reason"));
        } else if (list.get(position).containKey("task_name")) {
            planListItemViewHolder.title.setText(baseBean.getStr("task_state"));
            planListItemViewHolder.time.setText("#任务#");
            planListItemViewHolder.content.setText(baseBean.getStr("task_name"));
        } else if (list.get(position).containKey("project_name")) {
            planListItemViewHolder.title.setText("");
            planListItemViewHolder.time.setText("#项目#");
            planListItemViewHolder.content.setText(baseBean.getStr("project_name"));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PlanListItemViewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, time, content;
        private View view;
        private int position;

        public PlanListItemViewHOlder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.plan_list_recycle_item_title);
            time = (TextView) itemView.findViewById(R.id.plan_list_recycle_item_time);
            time.setTextColor(itemView.getResources().getColor(R.color.titlebar_background));
            content = (TextView) itemView.findViewById(R.id.plan_list_recycle_item_content);
            view = itemView.findViewById(R.id.plan_list_layout_recycle_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (list.get(position).containKey("plan_title")) {
                CreatePlanActivity.startCreatePlanActivity(context, list.get(position));
            } else if (list.get(position).containKey("summary_title")) {
                CreateSummaryActivity.startCreateSummaryActivity(context, list.get(position));
            } else if (list.get(position).containKey("over_content")) {
                CreateOverActivity.startCreateOverActivity(context, list.get(position));
            } else if (list.get(position).containKey("leave_reason")) {
                CreateHolidayActivity.startCreateHolidayActivity(context, list.get(position));
            } else if (list.get(position).containKey("claim_reason")) {
                CreateClaimActivity.startCreateClaimActivity(context, list.get(position));
            } else if (list.get(position).containKey("task_name")) {
                DetailTaskActivity.startDetailTaskActivity(context, list.get(position));
            } else if (list.get(position).containKey("project_name")) {
                DetailProjectActivity.startDetailProjectActivity(context, list.get(position));
            }
        }
    }
}
