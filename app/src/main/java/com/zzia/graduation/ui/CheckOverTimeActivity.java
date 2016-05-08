package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * 加班审核
 * Created by yunyujing on 16/5/8.
 */
public class CheckOverTimeActivity extends AppCompatActivity {
    private MyActionBar myActionBar;

    public static void startCheckOverTimeActivity(Context context,String from){
        Intent intent=new Intent(context,CheckOverTimeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_overtime_layout);
        initView();
    }

    private void initView() {
        myActionBar= (MyActionBar) findViewById(R.id.check_overtime_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
    }
}
