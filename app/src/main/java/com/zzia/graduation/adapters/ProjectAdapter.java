package com.zzia.graduation.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzia.graduation.bean.Project;
import com.zzia.graduation.ui.DetailProjectActivity;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/9
 */
public class ProjectAdapter extends RecyclerView.Adapter{

    private Context context;
    private ArrayList<Project> list;
    public ProjectAdapter(Context context, ArrayList<Project> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_layout_recycleview_item,null);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProjectViewHolder projectViewHolder= (ProjectViewHolder) holder;
        projectViewHolder.position=position;
        Project project=list.get(position);
        if(project.getIcon()!=null&&!project.getIcon().equals("")&&position==0){
//            projectViewHolder.imageView.setImageURI(Uri.parse(project.getIcon()));//使用普通的ImageView控件之恩只能引用本地的图片，使用SimpleDraweeView可以引用网络上的图片
        }
        projectViewHolder.textView.setText(project.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView textView;
        private View view;
        private int position;
        public ProjectViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.project_layout_recycleview_item_img);
            textView= (TextView) itemView.findViewById(R.id.project_layout_recycleview_item_text);
            view=itemView.findViewById(R.id.project_layout_recycleview_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("click","Project click");
            DetailProjectActivity.startDetailProjectActivity(context);
        }
    }
}
