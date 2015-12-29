package com.zzia.graduation.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zzia.graduation.utils.Common;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * Author: yunyujing
 * Date: 2015/12/21
 */
public class DetailTaskActivity extends Activity {
    private MyActionBar actionBar;

    public static void startDetailTaskActivity(Context context){
        Intent intent=new Intent(context,DetailTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_task_layout);
        initView();
    }

    private void initView() {
        actionBar= (MyActionBar) findViewById(R.id.detail_task_layout_actionbar);
        actionBar.setTitle("任务详情");
        actionBar.setBackAction(new MyActionBar.BackAction(this));
        actionBar.setRightAction(new MyActionBar.Action() {
            @Override
            public int getDrawable() {
                return R.mipmap.actionbar_edit;
            }

            @Override
            public int getText() {
                return 0;
            }

            @Override
            public void performAction(View view) {
                //编辑任务
                EditTaskActivity.startEditTaskActivity(DetailTaskActivity.this, Common.DETAIL_TASK);
            }
        });
        actionBar.setRight2Action(new MyActionBar.Action() {
            @Override
            public int getDrawable() {
                return R.mipmap.actionbar_delete;
            }

            @Override
            public int getText() {
                return 0;
            }

            @Override
            public void performAction(View view) {
                //删除任务
            }
        });
    }

}
