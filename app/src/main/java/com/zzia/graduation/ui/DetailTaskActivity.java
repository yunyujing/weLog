package com.zzia.graduation.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzia.graduation.welog.R;

/**
 * Author: yunyujing
 * Date: 2015/12/21
 */
public class DetailTaskActivity extends Activity {

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

    }

}
