package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzia.graduation.welog.R;

/**
 * 添加工作计划
 * Created by yunyujing on 16/5/3.
 */
public class AddPlanActivity extends AppCompatActivity {

    public static void startAddProjectActivity(Context context, String from) {
        Intent intent=new Intent(context,AddPlanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plan_layout);
    }
}
