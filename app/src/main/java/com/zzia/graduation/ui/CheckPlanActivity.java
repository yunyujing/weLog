package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import com.zzia.graduation.adapters.CheckLargeImageGridAdapter;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 工作计划内容
 * Created by yunyujing on 16/5/4.
 */
public class CheckPlanActivity extends AppCompatActivity {
    private MyActionBar myActionBar;
    private GridView gridView;
    private CheckLargeImageGridAdapter checkLargeImageGridAdapter;
    private ArrayList<BaseBean> list;

    public static void startCheckPlanActivity(Context context) {
        Intent intent = new Intent(context, CheckPlanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_plan_layout);
        initData();
        initView();
    }

    private void initData() {
        list = new ArrayList<>();
//        BaseBean baseBean = new BaseBean();
//        baseBean.set("url","res:// /"+R.mipmap.iconfont_zhanghao);
//        list.add(baseBean);
    }

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.check_plan_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

        gridView = (GridView) findViewById(R.id.check_plan_layout_grid);
        if (!list.isEmpty()) {
            checkLargeImageGridAdapter = new CheckLargeImageGridAdapter(getApplicationContext(), list);
            gridView.setAdapter(checkLargeImageGridAdapter);
            gridView.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.GONE);
        }

    }

}