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
 * Created by yunyujing on 16/5/22.
 */
public class CreateOverActivity extends AppCompatActivity{

    private MyActionBar myActionBar;
    private TextView createrText, checkerText, createtimeText, checktimeText, reasonText;
    private TextView startTimeText,endTimeText;
    private int checkId;
    private String creater, checker, createtime, checktime, reason;
    private String startTime,endTime;
    private int state;
    private TextView stateSpinner;

    public static void startCreateOverActivity(Context context,BaseBean baseBean){
        Intent intent=new Intent(context,CreateOverActivity.class);
        intent.putExtra("create_id", baseBean.getInt("create_id"));
        intent.putExtra("create_creater", baseBean.getStr("create_creater"));
        intent.putExtra("create_createtime", baseBean.getStr("create_createtime"));
        intent.putExtra("create_checker", baseBean.getStr("create_checker"));
        intent.putExtra("create_checktime", baseBean.getStr("create_checktime"));
        intent.putExtra("over_content",baseBean.getStr("over_content"));
        intent.putExtra("over_starttime", baseBean.getStr("over_starttime"));
        intent.putExtra("over_endtime", baseBean.getStr("over_endtime"));
        intent.putExtra("create_state", baseBean.getInt("create_state"));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_over_layout);
        Intent intent=getIntent();
        checkId = intent.getIntExtra("create_id", 0);
        creater = intent.getStringExtra("create_creater");
        createtime = intent.getStringExtra("create_createtime");
        checker = intent.getStringExtra("create_checker");
        checktime = intent.getStringExtra("create_checktime");
        reason = intent.getStringExtra("over_content");
        startTime = intent.getStringExtra("over_starttime");
        endTime=intent.getStringExtra("over_endtime");
        state = intent.getIntExtra("create_state", 0);

        initView();
    }

    private void initView() {
        myActionBar= (MyActionBar) findViewById(R.id.create_overtime_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
        

        createrText = (TextView) findViewById(R.id.create_overtime_layout_creater);
        createrText.setText(creater);
        createtimeText = (TextView) findViewById(R.id.create_overtime_layout_createtime);
        createtimeText.setText(createtime);
        checkerText= (TextView) findViewById(R.id.create_overtime_layout_checker);
        checkerText.setText(checker);
        checktimeText= (TextView) findViewById(R.id.create_overtime_layout_checktime);
        checktimeText.setText(checktime);
        reasonText = (TextView) findViewById(R.id.create_overtime_layout_reason);
        reasonText.setText(reason);
        startTimeText = (TextView) findViewById(R.id.create_overtime_layout_starttime);
        startTimeText.setText(startTime);
        endTimeText= (TextView) findViewById(R.id.create_overtime_layout_endtime);
        endTimeText.setText(endTime);
        stateSpinner = (TextView) findViewById(R.id.create_over_layout_state);

        if(state==0){
            stateSpinner.setText("待审核");
        }else if(state==1){
            stateSpinner.setText("通过");
        }else {
            stateSpinner.setText("驳回");
        }

//        stateSpinner.setSelection(state);
//        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                stateSpinner.setSelection(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                stateSpinner.setSelection(state);
//
//            }
//        });

    }
}
