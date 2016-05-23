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
 * 休假审核
 * Created by yunyujing on 16/5/8.
 */
public class CheckHolidayActivity extends AppCompatActivity{

    private MyActionBar myActionBar;
    private TextView createrText, checkerText, createtimeText, checktimeText, reasonText;
    private TextView startTimeText,endTimeText;
    private int checkId;
    private String creater, checker, createtime, checktime, reason;
    private String startTime,endTime;
    private int state;
    private Spinner stateSpinner;

    public static void startCheckHolidayActivity(Context context, BaseBean baseBean) {

        Intent intent=new Intent(context,CheckHolidayActivity.class);
        intent.putExtra("check_id", baseBean.getInt("check_id"));
        intent.putExtra("check_creater", baseBean.getStr("check_creater"));
        intent.putExtra("check_createtime", baseBean.getStr("check_createtime"));
        intent.putExtra("check_checker", baseBean.getStr("check_checker"));
        intent.putExtra("check_checktime", baseBean.getStr("check_checktime"));
        intent.putExtra("leave_starttime",baseBean.getStr("leave_starttime"));
        intent.putExtra("leave_endtime", baseBean.getStr("leave_endtime"));
        intent.putExtra("leave_reason", baseBean.getStr("leave_reason"));
        intent.putExtra("check_state", baseBean.getInt("check_state"));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_holiday_layout);
        Intent intent=getIntent();
        checkId = intent.getIntExtra("check_id", 0);
        creater = intent.getStringExtra("check_creater");
        createtime = intent.getStringExtra("check_createtime");
        checker = intent.getStringExtra("check_checker");
        checktime = intent.getStringExtra("check_checktime");
        reason = intent.getStringExtra("leave_reason");
        startTime = intent.getStringExtra("leave_starttime");
        endTime=intent.getStringExtra("leave_endtime");
        state = intent.getIntExtra("check_state", 0);

        initView();

    }

    private void initView() {
        myActionBar= (MyActionBar) findViewById(R.id.check_holiday_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
        myActionBar.setRight2Action(new MyActionBar.Action() {
            @Override
            public int getDrawable() {
                return R.mipmap.actionbar_edit_complete;
            }

            @Override
            public int getText() {
                return 0;
            }

            @Override
            public void performAction(View view) {
                String stateString = stateSpinner.getSelectedItem().toString();
                int stateInt = 0;
                if (stateString.equals("通过")) {
                    stateInt = 1;
                } else if (stateString.equals("驳回")) {
                    stateInt = 2;
                }
                addDataToSql(checkId, stateInt);

            }
        });

        createrText = (TextView) findViewById(R.id.check_holiday_layout_creater);
        createrText.setText(creater);
        createtimeText = (TextView) findViewById(R.id.check_holiday_layout_createtime);
        createtimeText.setText(createtime);
        checkerText = (TextView) findViewById(R.id.check_holiday_layout_checker);
        checkerText.setText(checker);
        checktimeText = (TextView) findViewById(R.id.check_holiday_layout_checktime);
        checktimeText.setText(checktime);
        reasonText = (TextView) findViewById(R.id.check_holiday_layout_reason);
        reasonText.setText(reason);
        startTimeText = (TextView) findViewById(R.id.check_holiday_layout_starttime);
        startTimeText.setText(startTime);
        endTimeText = (TextView) findViewById(R.id.check_holiday_layout_endtime);
        endTimeText.setText(endTime);
        stateSpinner = (Spinner) findViewById(R.id.check_holiday_layout_state);
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
    private void addDataToSql(int checkId, int stateId) {
        if (stateId == this.state ) {
            ToastUtils.show(getApplicationContext(), "没有审核，不能进行提交");
        } else {

            SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();
            if (stateId != this.state) {

                sqLiteDatabase.execSQL("update checkwork set check_state=? where check_id=? ;",
                        new String[]{String.valueOf(stateId), String.valueOf(checkId)});
            }

            finish();
        }
    }

}
