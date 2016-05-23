package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * Created by yunyujing on 16/5/23.
 */
public class CreateHolidayActivity extends AppCompatActivity{
    private MyActionBar myActionBar;
    private TextView createrText, checkerText, createtimeText, checktimeText, reasonText;
    private TextView startTimeText,endTimeText;
    private int checkId;
    private String creater, checker, createtime, checktime, reason;
    private String startTime,endTime;
    private int state;
    private TextView stateSpinner;

    public static void startCreateHolidayActivity(Context context, BaseBean baseBean) {
        Intent intent=new Intent(context,CreateHolidayActivity.class);
        intent.putExtra("create_id", baseBean.getInt("create_id"));
        intent.putExtra("check_creater", baseBean.getStr("check_creater"));
        intent.putExtra("check_createtime", baseBean.getStr("check_createtime"));
        intent.putExtra("check_checker", baseBean.getStr("check_checker"));
        intent.putExtra("check_checktime", baseBean.getStr("check_checktime"));
        intent.putExtra("leave_starttime",baseBean.getStr("leave_starttime"));
        intent.putExtra("leave_endtime", baseBean.getStr("leave_endtime"));
        intent.putExtra("leave_reason", baseBean.getStr("leave_reason"));
        intent.putExtra("create_state", baseBean.getInt("create_state"));
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_holiday_layout);
        Intent intent=getIntent();
        checkId = intent.getIntExtra("create_id", 0);
        creater = intent.getStringExtra("create_creater");
        createtime = intent.getStringExtra("create_createtime");
        checker = intent.getStringExtra("create_checker");
        checktime = intent.getStringExtra("create_checktime");
        reason = intent.getStringExtra("leave_reason");
        startTime = intent.getStringExtra("leave_starttime");
        endTime=intent.getStringExtra("leave_endtime");
        state = intent.getIntExtra("create_state", 0);

        initView();
    }

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.create_holiday_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));


        createrText = (TextView) findViewById(R.id.create_holiday_layout_creater);
        createrText.setText(creater);
        createtimeText = (TextView) findViewById(R.id.create_holiday_layout_createtime);
        createtimeText.setText(createtime);
        checkerText = (TextView) findViewById(R.id.create_holiday_layout_checker);
        checkerText.setText(checker);
        checktimeText = (TextView) findViewById(R.id.create_holiday_layout_checktime);
        checktimeText.setText(checktime);
        reasonText = (TextView) findViewById(R.id.create_holiday_layout_reason);
        reasonText.setText(reason);
        startTimeText = (TextView) findViewById(R.id.create_holiday_layout_starttime);
        startTimeText.setText(startTime);
        endTimeText = (TextView) findViewById(R.id.create_holiday_layout_endtime);
        endTimeText.setText(endTime);
        stateSpinner = (TextView) findViewById(R.id.create_holiday_layout_state);

        if (state == 0) {
            stateSpinner.setText("待审核");
        } else if (state == 1) {
            stateSpinner.setText("通过");
        } else {
            stateSpinner.setText("驳回");
        }
    }
}
