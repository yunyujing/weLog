package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * 工作总结列表
 * Created by yunyujing on 16/5/4.
 */
public class CheckSummaryActivity extends AppCompatActivity {

    private MyActionBar myActionBar;
    public static void startCheckSummaryActivity(Context context){

        Intent intent=new Intent(context,CheckSummaryActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_summary_layout);
        initView();

    }

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.check_summary_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
    }
}
