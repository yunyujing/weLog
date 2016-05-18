package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzia.graduation.adapters.TaskFragmentAdapter;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/17
 */
public class AllTaskFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<BaseBean> tasks;
    private TaskFragmentAdapter taskFragmentAdapter;

    private View addTask;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_viewpager_fragment1, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData();
        initView(view);
    }

    private void initData() {
        tasks=new ArrayList<>();
//        Task task1=new Task();
//        task1.setTitle("小潘 完成了任务");
//        task1.setDetail("后台的数据处理");
//        task1.setEndTime("2015-12-18");
//        tasks.add(task1);
//        Task task2=new Task();
//        task2.setTitle("大师 完成了任务");
//        task2.setDetail("web前端");
//        task2.setEndTime("2015-12-17");
//        tasks.add(task2);
//        Task task3=new Task();
//        task3.setTitle("小胖 完成了任务");
//        task3.setDetail("后台的接口");
//        task3.setEndTime("2015-12-10");
//        tasks.add(task3);
//        Task task4=new Task();
//        task4.setTitle("依依完成了任务");
//        task4.setDetail("测试调整");
//        task4.setEndTime("2015-12-10");
//        tasks.add(task4);
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.task_viewpager_fragment1_recycleview);
        recyclerView.setHasFixedSize(true);//有固定大小的recycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DetailProjectActivity.MyListItemDecoration(30));
        taskFragmentAdapter =new TaskFragmentAdapter(getContext(),tasks);
        recyclerView.setAdapter(taskFragmentAdapter);

        addTask=view.findViewById(R.id.task_viewpager_fragment1_add);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskActivity.startAddTaskActivity(getContext(),"task");
            }
        });
    }
}
