package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzia.graduation.adapters.ClaimListAdapter;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 报销列表
 * Created by yunyujing on 4/27/16.
 */
public class ClaimListActivity extends AppCompatActivity {

    private MyActionBar myActionBar;
    private RecyclerView recyclerView;
    private ClaimListAdapter claimListAdapter;
    private ArrayList<BaseBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_list_layout);
        initData();
        initView();
    }

    private void initData() {
        list=new ArrayList<>();
        for (int i = 0; i < 12; i++) {

            BaseBean baseBean = new BaseBean();
            baseBean.set("title", "待审核");
            baseBean.set("time", "2016-05-04");
            baseBean.set("content", "购买设备");
            list.add(baseBean);
        }
    }

    private void initView() {
        myActionBar= (MyActionBar) findViewById(R.id.claim_list_layout_actionbar);
        myActionBar= (MyActionBar) findViewById(R.id.claim_list_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

        recyclerView= (RecyclerView) findViewById(R.id.claim_list_layout_recycle);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DetailProjectActivity.MyListItemDecoration(30));
        recyclerView.setHasFixedSize(true);
        claimListAdapter=new ClaimListAdapter(this,list);
        recyclerView.setAdapter(claimListAdapter);
    }
}
