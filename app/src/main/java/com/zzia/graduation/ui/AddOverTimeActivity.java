package com.zzia.graduation.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zzia.graduation.bean.Company;
import com.zzia.graduation.bean.User;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.common.util.StringUtils;
import com.zzia.graduation.common.util.ToastUtils;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.utils.SharedPreferenceUtils;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 添加加班认定
 * Author: yunyujing
 * Date: 2015/12/29
 */
public class AddOverTimeActivity extends AppCompatActivity implements View.OnClickListener {
    private MyActionBar actionBar;
    private Spinner checker;
    private ArrayList<String> checkList;
    private ArrayList<Integer> checkIdList;
    private TextView startDate, startTime;
    private TextView endDate, endTime;
    private int y, m, d;
    private Calendar calendarNow;
    private int h, s;
    private TextView contentText;

    public static void startAddOverTimeActivity(Context context, String from) {
        Intent intent = new Intent(context, AddOverTimeActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_overtime_layout);
        initDateAndTime();
        initView();
    }

    private void initDateAndTime() {
        calendarNow = Calendar.getInstance();
        y = calendarNow.get(Calendar.YEAR);
        m = calendarNow.get(Calendar.MONTH);
        d = calendarNow.get(Calendar.DAY_OF_MONTH);
        h = calendarNow.get(Calendar.HOUR_OF_DAY);
        s = calendarNow.get(Calendar.SECOND);

        checkIdList = new ArrayList<>();
        checkList = new ArrayList<>();
        ArrayList<BaseBean> arrayList = Company.getUsers(getApplicationContext());
        if (arrayList == null || arrayList.size() <= 0) {//当前还没有建立新项目
            ToastUtils.show(getApplicationContext(), "请先添加员工再指定执行者", Toast.LENGTH_LONG);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                checkIdList.add(arrayList.get(i).getInt("user_id"));
                checkList.add(arrayList.get(i).getStr("user_name"));

            }
        }

    }

    private void initView() {
        actionBar = (MyActionBar) findViewById(R.id.edit_add_overtime_layout_actionbar);
        actionBar.setBackAction(new MyActionBar.BackAction(this));
        actionBar.setRight2Action(new MyActionBar.Action() {
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
                String checkerName = checker.getSelectedItem().toString();
                String start = startDate.getText().toString()+" "+startTime.getText().toString();
                String end = endDate.getText().toString()+" "+endTime.getText().toString();
                String content = contentText.getText().toString();
                if (checkerName != null && !StringUtils.isEmpty(start) && !StringUtils.isEmpty(end) && !StringUtils.isEmpty(content)) {
                    addDataToSql(checkerName, start, end, content);
                    //添加成功之后返回
                    finish();

                } else {
                    ToastUtils.show(getApplicationContext(), "请填写完整的请假表单");
                }

            }
        });
        checker = (Spinner) findViewById(R.id.edit_add_overtime_layout_spinner);
        checker.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, checkList));
        checker.setSelection(0);
        checker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checker.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                checker.setSelection(0);

            }
        });

        startDate = (TextView) findViewById(R.id.edit_add_overtime_layout_start_date);
        startDate.setOnClickListener(this);
        startTime = (TextView) findViewById(R.id.edit_add_overtime_layout_start_time);
        startTime.setOnClickListener(this);

        endDate = (TextView) findViewById(R.id.edit_add_overtime_layout_end_date);
        endDate.setOnClickListener(this);
        endTime = (TextView) findViewById(R.id.edit_add_overtime_layout_end_time);
        endTime.setOnClickListener(this);

        contentText = (TextView) findViewById(R.id.edit_add_overtime_layout_task);
    }

    private void addDataToSql(String checkerName, String start, String end, String content) {
        int checkerId = 0;
        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i).equals(checkerName)) {
                checkerId = checkIdList.get(i);
                break;
            }
        }

        String time = Common.getNowTimeNoSecond();
        SQLiteDatabase sqlDataBase = new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();
        sqlDataBase.execSQL("insert into checkwork (check_creater,check_createtime,check_checker,check_checktime,over_starttime,over_endtime,over_content,check_state,company_id) values (?,?,?,?,?,?,?,?,?);",
                new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0))
                        , time
                        , String.valueOf(checkerId)
                        , time
                        , start
                        , end
                        , content
                        , String.valueOf(0)
                        , String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.companyId, 0))});


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.edit_add_overtime_layout_start_date:
                showDatePickerDialog(0);
                break;
            case R.id.edit_add_overtime_layout_end_date:
                showDatePickerDialog(1);
                break;
            case R.id.edit_add_overtime_layout_start_time:
                showTimePickerDialog(0);
                break;
            case R.id.edit_add_overtime_layout_end_time:
                showTimePickerDialog(1);
            default:
                break;
        }
    }

    /**
     * 弹出日期选择的对话框
     */

    private void showDatePickerDialog(final int flag) {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendarBir = Calendar.getInstance();
                calendarBir.set(year, monthOfYear, dayOfMonth);
                Date date = new Date(calendarBir.getTimeInMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String showDate = simpleDateFormat.format(date);
                if (flag == 0) {
                    startDate.setText(showDate);
                } else {
                    endDate.setText(showDate);
                }
            }
        }, y, m, d).show();
    }

    /**
     * 弹出时间选择的对话框
     */
    private void showTimePickerDialog(final int flag) {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendarBir = Calendar.getInstance();
                calendarBir.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarBir.set(Calendar.MINUTE, minute);
                Date date = new Date(calendarBir.getTimeInMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                String showTime = simpleDateFormat.format(date);
                if (flag == 0) {
                    startTime.setText(showTime);
                } else {

                    endTime.setText(showTime);
                }
            }
        }, h, s, true).show();
    }
}
