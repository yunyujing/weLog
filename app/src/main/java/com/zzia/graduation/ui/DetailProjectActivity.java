
package com.zzia.graduation.ui;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zzia.graduation.adapters.DetailProjectAdapter;
import com.zzia.graduation.bean.Project;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/21
 */
public class DetailProjectActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private TextView projectDes;
    private DetailProjectAdapter detailProjectAdapter;
    private ArrayList<BaseBean> list;
    private RecyclerView mRecyceView;

    private String name;
    private String info;

    public static void startDetailProjectActivity(Context context, BaseBean baseBean) {
        Intent intent = new Intent(context, DetailProjectActivity.class);
        intent.putExtra("project_id", baseBean.getInt("project_id"));
        intent.putExtra("project_name", baseBean.getStr("project_name"));
        intent.putExtra("project_info", baseBean.getStr("project_info"));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_project_layout);
        initData();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    private void initData() {
        list = new ArrayList<>();
        Intent intent = getIntent();
        name = intent.getStringExtra("project_name");
        info = intent.getStringExtra("project_info");
        int id=intent.getIntExtra("project_id",0);

        list= Project.getTasks(getApplicationContext(),id);

//        String s="依依修改了任务";
//        for (int i=0;i<10;i++){
//            list.add(s);
//        }

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
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_toolbar_edit) {//编辑项目
                    EditProjectActivity.startEditProjectActivity(DetailProjectActivity.this, Common.DETAIL_PROJECT);
                }
                return true;
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.detail_project_layout_collapsing);
        mCollapsingToolbarLayout.setTitle(name);//通过CollapsingToolbarLayout设置标题
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        projectDes = (TextView) findViewById(R.id.detail_project_layout_content_about);
        projectDes.setText(info);


        mRecyceView = (RecyclerView) findViewById(R.id.detail_project_layout_content_recycle);
        mRecyceView.setHasFixedSize(false);//没有固定大小的recycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyceView.setLayoutManager(layoutManager);
        MyListItemDecoration myListItemDirector = new MyListItemDecoration(40);
        mRecyceView.addItemDecoration(myListItemDirector);
        detailProjectAdapter = new DetailProjectAdapter(this, list);
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
        }
    }

}
