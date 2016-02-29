package com.zzia.graduation.ui;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzia.graduation.adapters.ProjectAdapter;
import com.zzia.graduation.bean.Project;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/1
 */
public class ProjectFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProjectAdapter adapter;
    private ArrayList<Project> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project_layout, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData();
        initView(view);

    }

    private void initData() {
        list = new ArrayList<>();
        Project project1 = new Project();
        project1.setName("Test");
        list.add(project1);
        Project project2 = new Project();
        project2.setName("Test2");
        list.add(project2);

    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.project_layout_recycleview);
        recyclerView.setHasFixedSize(false);//没有固定大小的recycleView
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);//设置为2列的瀑布流效果
        MyItemDecoration myItemDecrotion = new MyItemDecoration(40);
        recyclerView.addItemDecoration(myItemDecrotion);//设置每个item的间距
        adapter = new ProjectAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public MyItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;
        }
    }
}
