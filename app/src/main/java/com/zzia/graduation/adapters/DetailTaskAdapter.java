package com.zzia.graduation.adapters;

import android.content.Context;
import android.net.Uri;
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
    private ArrayList<Uri> imageList = new ArrayList<>();

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
        detailTaskAdapterViewHolder.headIcon.setImageURI(Uri.parse(baseBean.getStr("icon")));
        detailTaskAdapterViewHolder.name.setText(baseBean.getStr("name"));
        detailTaskAdapterViewHolder.comment.setText(baseBean.getStr("comment"));
        detailTaskAdapterViewHolder.time.setText(baseBean.getStr("time"));
        if (baseBean.getInt("imageNum") > 0) {

            detailTaskAdapterViewHolder.gridView.setVisibility(View.VISIBLE);
            for (int i = 0; i < baseBean.getInt("imageNum"); i++) {

                imageList.add(Uri.parse(baseBean.getStr("image" + i)));
            }
            detailTaskAdapterViewHolder.gridView.setAdapter(new CheckLargeImageGridAdapter(context, imageList));
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
