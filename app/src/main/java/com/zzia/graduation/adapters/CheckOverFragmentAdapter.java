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
 * Created by yunyujing on 16/5/22.
 */
public class CheckOverFragmentAdapter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<BaseBean> list;

    public CheckOverFragmentAdapter(Context applicationContext, ArrayList<BaseBean> list) {
        this.context = applicationContext;
        this.list = list;

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
        planListItemViewHolder.title.setText(checkState);
        planListItemViewHolder.time.setText(baseBean.getStr("check_checktime"));
        planListItemViewHolder.content.setText(baseBean.getStr("over_content"));

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
            content = (TextView) itemView.findViewById(R.id.plan_list_recycle_item_content);
            view = itemView.findViewById(R.id.plan_list_layout_recycle_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            CheckOverTimeActivity.startCheckOverTimeActivity(context, list.get(position));
        }
    }
}
