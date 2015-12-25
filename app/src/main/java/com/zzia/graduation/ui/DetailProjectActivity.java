package com.zzia.graduation.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.zzia.graduation.adapters.DetailProjectAdapter;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/21
 */
public class DetailProjectActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private DetailProjectAdapter detailProjectAdapter;
    private ArrayList<String> list;
    private RecyclerView mRecyceView;
    public static void startDetailProjectActivity(Context context){
        Intent intent=new Intent(context,DetailProjectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_project_layout);
        initData();
        initView();
    }

    private void initData() {
        list=new ArrayList<>();
        String s="依依修改了任务";
        for (int i=0;i<10;i++){
            list.add(s);
        }

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.detail_project_layout_collapsing_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.detail_project_layout_collapsing);
        mCollapsingToolbarLayout.setTitle("项目名称");//通过CollapsingToolbarLayout设置标题
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        mRecyceView= (RecyclerView) findViewById(R.id.detail_project_layout_content_recycle);
        mRecyceView.setHasFixedSize(false);//没有固定大小的recycleView
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyceView.setLayoutManager(layoutManager);
        MyListItemDecoration myListItemDirector=new MyListItemDecoration(30);
        mRecyceView.addItemDecoration(myListItemDirector);
        detailProjectAdapter=new DetailProjectAdapter(this,list);
        mRecyceView.setAdapter(detailProjectAdapter);



    }

    static class MyListItemDecoration extends RecyclerView.ItemDecoration {
         private int space;

         public MyListItemDecoration(int space) {
             this.space = space;
         }

         @Override
         public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
             super.onDraw(c, parent, state);
         }

         @Override
         public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
             super.getItemOffsets(outRect, view, parent, state);
             outRect.bottom = space;
             outRect.top = space;
         }
     }

}
