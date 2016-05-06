package com.zzia.graduation.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 添加休假申请
 * Author: yunyujing
 * Date: 2015/12/29
 */
public class AddHolidayActivity extends AppCompatActivity implements View.OnClickListener {
    private MyActionBar actionBar;
    private TextView startDate, startTime;
    private TextView endDate, endTime;
    private int y, m, d;
    private Calendar calendarNow;
    private int h, s;

    public static void startAddHolidayActivity(Context context, String from) {
        Intent intent = new Intent(context, AddHolidayActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_holiday_layout);
        initView();
        initDateAndTime();
    }

    /**
     * 当前日期和时间
     */
    private void initDateAndTime() {
        calendarNow = Calendar.getInstance();
        y = calendarNow.get(Calendar.YEAR);
        m = calendarNow.get(Calendar.MONTH);
        d = calendarNow.get(Calendar.DAY_OF_MONTH);
        h = calendarNow.get(Calendar.HOUR_OF_DAY);
        s = calendarNow.get(Calendar.SECOND);

    }

    /**
     * 初始化布局
     */
    private void initView() {
        actionBar = (MyActionBar) findViewById(R.id.edit_add_holiday_layout_actionbar);
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

            }
        });

        startDate = (TextView) findViewById(R.id.edit_add_holiday_layout_start_date);
        startDate.setOnClickListener(this);
        startTime = (TextView) findViewById(R.id.edit_add_holiday_layout_start_time);
        startTime.setOnClickListener(this);

        endDate = (TextView) findViewById(R.id.edit_add_holiday_layout_end_date);
        endDate.setOnClickListener(this);
        endTime = (TextView) findViewById(R.id.edit_add_holiday_layout_end_time);
        endTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_add_holiday_layout_start_date:
                showDatePickerDialog(0);
                break;
            case R.id.edit_add_holiday_layout_end_date:
                showDatePickerDialog(1);
                break;
            case R.id.edit_add_holiday_layout_start_time:
                showTimePickerDialog(0);
                break;
            case R.id.edit_add_holiday_layout_end_time:
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
                Date date=new Date(calendarBir.getTimeInMillis());
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String showDate=simpleDateFormat.format(date);
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
                Date date=new Date(calendarBir.getTimeInMillis());
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("hh:mm");
                String showTime=simpleDateFormat.format(date);
                if (flag == 0) {
                    startTime.setText(showTime);
                } else {

                    endTime.setText(showTime);
                }
            }
        }, h, s, true).show();
    }
}
