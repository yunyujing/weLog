package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzia.graduation.adapters.PlanListAdapter;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 工作计划列表
 * Created by yunyujing on 4/27/16.
 */
public class PlanListActivity extends AppCompatActivity {

    private MyActionBar myActionBar;

    private RecyclerView recyclerView;
    private ArrayList<BaseBean> list;
    private PlanListAdapter planListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_list_layout);
        initData();
        initView();
    }

    private void initData() {
        list=new ArrayList<>();
        for (int i = 0; i < 12; i++) {

            BaseBean baseBean = new BaseBean();
            baseBean.set("title", "有新评论");
            baseBean.set("time", "2016-05-04");
            baseBean.set("content", "5月工作计划");
            list.add(baseBean);
        }
    }

    private void initView() {

        myActionBar= (MyActionBar) findViewById(R.id.plan_list_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

        recyclerView = (RecyclerView) findViewById(R.id.plan_list_layout_recycle);
        recyclerView.setHasFixedSize(true);//有固定大小的recycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DetailProjectActivity.MyListItemDecoration(30));
        planListAdapter = new PlanListAdapter(this, list);
        recyclerView.setAdapter(planListAdapter);
    }
}