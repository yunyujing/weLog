package com.zzia.graduation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.ui.CheckHolidayActivity;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/8.
 */
public class HolidayListAdapter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<BaseBean> list;
    public HolidayListAdapter(Context context, ArrayList<BaseBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.holiday_list_recycle_item,null);
        return new HolidayListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolidayListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title,time,content;
        private View view;

        public HolidayListAdapterViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.holiday_list_recycle_item_title);
            time= (TextView) itemView.findViewById(R.id.holiday_list_recycle_item_time);
            content= (TextView) itemView.findViewById(R.id.holiday_list_recycle_item_content);
            view=itemView.findViewById(R.id.holiday_list_layout_recycle_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            CheckHolidayActivity.startCheckHolidayActivity(context,"HolidayListAdapter");
        }
    }
}
