package com.zzia.graduation.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzia.graduation.bean.Task;
import com.zzia.graduation.ui.DetailTaskActivity;
import com.zzia.graduation.welog.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/17
 */
public class TaskFragmentAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Task> list;
    public TaskFragmentAdapter(Context context, ArrayList<Task> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.task_viewpager_fragment1_recycleview_item,null);
        return new TaskFragmentViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskFragmentViewHOlder allTaskFragmentViewHOlder= (TaskFragmentViewHOlder) holder;
        allTaskFragmentViewHOlder.position=position;
        Task task=list.get(position);
        if(task.getUser()!=null&&!task.getUser().getIcon().equals("")){
            allTaskFragmentViewHOlder.userImag.setImageURI(Uri.parse(""));
        }
        allTaskFragmentViewHOlder.time.setText(task.getEndTime());
        allTaskFragmentViewHOlder.title.setText(task.getTitle());
        allTaskFragmentViewHOlder.detail.setText(task.getDetail());
//        allTaskFragmentViewHOlder.project.setText(task.getProject().getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TaskFragmentViewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView userImag;
        private TextView time;
        private TextView title;
        private TextView detail;
        private TextView project;
        private View view;
        private int position;

        public TaskFragmentViewHOlder(View itemView) {
            super(itemView);
            userImag= (ImageView) itemView.findViewById(R.id.task_layout_fragment1_recycleview_item_img);
            time= (TextView) itemView.findViewById(R.id.task_layout_fragment1_recycleview_item_text_time);
            title= (TextView) itemView.findViewById(R.id.task_layout_fragment1_recycleview_item_text_title);
            detail= (TextView) itemView.findViewById(R.id.task_layout_fragment1_recycleview_item_text_detail);
            project= (TextView) itemView.findViewById(R.id.task_layout_fragment1_recycleview_item_text_project);
            view=itemView.findViewById(R.id.task_viewpager_fragment1_recycleview_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("onclick","Task click");
            DetailTaskActivity.startDetailTaskActivity(context);
        }
    }
}
