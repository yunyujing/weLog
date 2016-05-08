package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzia.graduation.adapters.OverTimeListAdapter;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 加班列表
 * Created by yunyujing on 4/27/16.
 */
public class OverTimeListActivity extends AppCompatActivity {

    private MyActionBar myActionBar;

    private RecyclerView recyclerView;
    private OverTimeListAdapter overTimeListAdapter;
    private ArrayList<BaseBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overtime_list_layout);
        initData();
        initView();
    }
    private void initData() {

        list=new ArrayList<>();
        for (int i = 0; i < 8; i++) {

            BaseBean baseBean = new BaseBean();
            baseBean.set("title", "待审核");
            baseBean.set("time", "2016-05-04");
            baseBean.set("content", "加班");
            list.add(baseBean);
        }
    }

    private void initView() {

        myActionBar= (MyActionBar) findViewById(R.id.overtime_list_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

        recyclerView= (RecyclerView) findViewById(R.id.overtime_list_layout_recycle);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DetailProjectActivity.MyListItemDecoration(30));
        overTimeListAdapter=new OverTimeListAdapter(this,list);
        recyclerView.setAdapter(overTimeListAdapter);
    }


}
