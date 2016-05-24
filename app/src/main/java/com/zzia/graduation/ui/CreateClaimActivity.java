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
public class CreateClaimActivity extends AppCompatActivity {
    private MyActionBar myActionBar;
    private TextView createrText, checkerText, createtimeText, checktimeText, reasonText;
    private TextView timeText, moneyText;
    private int checkId;
    private String creater, checker, createtime, checktime, reason;
    private String time, money;
    private int state;
    private TextView stateSpinner;

    public static void startCreateClaimActivity(Context context, BaseBean baseBean) {
        Intent intent = new Intent(context, CreateClaimActivity.class);
        intent.putExtra("create_id", baseBean.getInt("create_id"));
        intent.putExtra("check_creater", baseBean.getStr("check_creater"));
        intent.putExtra("check_createtime", baseBean.getStr("check_createtime"));
        intent.putExtra("check_checker", baseBean.getStr("check_checker"));
        intent.putExtra("check_checktime", baseBean.getStr("check_checktime"));
        intent.putExtra("claim_reason", baseBean.getStr("claim_reason"));
        intent.putExtra("claim_time", baseBean.getStr("claim_time"));
        intent.putExtra("claim_money", baseBean.getStr("claim_money"));
        intent.putExtra("create_state", baseBean.getInt("create_state"));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_claim_layout);
        Intent intent = getIntent();
        checkId = intent.getIntExtra("create_id", 0);
        creater = intent.getStringExtra("check_creater");
        createtime = intent.getStringExtra("check_createtime");
        checker = intent.getStringExtra("check_checker");
        checktime = intent.getStringExtra("check_checktime");
        reason = intent.getStringExtra("claim_reason");
        time = intent.getStringExtra("claim_time");
        money = intent.getStringExtra("claim_money");
        state = intent.getIntExtra("create_state", 0);
        initView();
    }

    private void initView() {
        myActionBar= (MyActionBar) findViewById(R.id.create_claim_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));


        createrText = (TextView) findViewById(R.id.create_claim_layout_creater);
        createrText.setText(creater);
        createtimeText = (TextView) findViewById(R.id.create_claim_layout_createtime);
        createtimeText.setText(createtime);
        checkerText= (TextView) findViewById(R.id.create_claim_layout_checker);
        checkerText.setText(checker);
        checktimeText= (TextView) findViewById(R.id.create_claim_layout_checktime);
        checktimeText.setText(checktime);
        reasonText = (TextView) findViewById(R.id.create_claim_layout_reason);
        reasonText.setText(reason);
        timeText = (TextView) findViewById(R.id.create_claim_layout_time);
        timeText.setText(time);
        moneyText= (TextView) findViewById(R.id.create_claim_layout_money);
        moneyText.setText(money);
        stateSpinner = (TextView) findViewById(R.id.create_claim_layout_state);

        if(state==0){
            stateSpinner.setText("待审核");
        }else if(state==1){
            stateSpinner.setText("通过");
        }else {
            stateSpinner.setText("驳回");
        }


    }
}
