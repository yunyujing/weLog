
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

import com.zzia.graduation.adapters.DetailProjectAdapter;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
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
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.menu_toolbar_edit){
                    //编辑项目
                    EditProjectActivity.startEditProjectActivity(DetailProjectActivity.this, Common.DETAIL_PROJECT);
                }else if(item.getItemId()==R.id.menu_toolbar_delete){
                    //删除项目
                }
                return true;
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
        MyListItemDecoration myListItemDirector=new MyListItemDecoration(40);
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
         }
     }

}
