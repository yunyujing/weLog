package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.common.util.ToastUtils;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * 报销审核
 * Created by yunyujing on 16/5/8.
 */
public class CheckClaimActivity extends AppCompatActivity {
    private MyActionBar myActionBar;
    private TextView createrText, checkerText, createtimeText, checktimeText, reasonText;
    private TextView timeText, moneyText;
    private int checkId;
    private String creater, checker, createtime, checktime, reason;
    private String time, money;
    private int state;
    private Spinner stateSpinner;

    public static void startCheckClaimActivity(Context context, BaseBean baseBean) {
        Intent intent = new Intent(context, CheckClaimActivity.class);
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
        setContentView(R.layout.check_claim_layout);

        Intent intent = getIntent();
        checkId = intent.getIntExtra("create_id", 0);
        creater = intent.getStringExtra("check_creater");
        createtime = intent.getStringExtra("check_createtime");
        checker = intent.getStringExtra("check_checker");
        checktime = intent.getStringExtra("check_checktime");
        reason = intent.getStringExtra("claim_reason");
        time = intent.getStringExtra("claim_time");
        money = intent.getStringExtra("claim_monry");
        state = intent.getIntExtra("create_state", 0);
        initView();
    }

    private void initView() {

        myActionBar = (MyActionBar) findViewById(R.id.check_claim_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
        myActionBar.setRight2Action(new MyActionBar.Action() {
            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public int getText() {
                return R.mipmap.actionbar_edit_complete;
            }

            @Override
            public void performAction(View view) {

                int selectedItemPosition = stateSpinner.getSelectedItemPosition();
                if (selectedItemPosition != state) {

                    addDataToSql(selectedItemPosition);
                    finish();
                } else {
                    ToastUtils.show(getApplicationContext(), "审核状态没有改变，无法提交");
                }
            }
        });

        createrText = (TextView) findViewById(R.id.check_claim_layout_creater);
        createrText.setText(creater);
        createtimeText = (TextView) findViewById(R.id.check_claim_layout_createtime);
        createtimeText.setText(createtime);
        checkerText = (TextView) findViewById(R.id.check_claim_layout_checker);
        checkerText.setText(checker);
        checktimeText = (TextView) findViewById(R.id.create_claim_layout_checktime);
        checktimeText.setText(checktime);
        reasonText = (TextView) findViewById(R.id.check_claim_layout_reason);
        reasonText.setText(reason);
        timeText = (TextView) findViewById(R.id.check_claim_layout_time);
        timeText.setText(time);
        moneyText = (TextView) findViewById(R.id.check_claim_layout_money);
        moneyText.setText(money);
        stateSpinner = (Spinner) findViewById(R.id.check_claim_layout_state);
        stateSpinner.setSelection(state);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stateSpinner.setSelection(state);
            }
        });

    }

    private void addDataToSql(int selectedItemPosition) {


        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();

        sqLiteDatabase.execSQL("update checkwork set check_state=? where check_id=? ;",
                new String[]{String.valueOf(selectedItemPosition), String.valueOf(checkId)});


    }


}
