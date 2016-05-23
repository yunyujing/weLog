package com.zzia.graduation.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
 * 审批报销
 * Author: yunyujing
 * Date: 2015/12/30
 */
public class AddClaimActivity extends Activity {
    private MyActionBar actionBar;
    private Spinner checkerSpinner;
    private ArrayList<Integer> checkerIdList;
    private ArrayList<String> checkerNameList;
    private TextView dateText;
    private RadioGroup radioGroup;
    private String radioString;
    private EditText monryEdit;
    private int y, m, d;
    private Calendar calendarNow;

    public static void startAddClaimActivity(Context context, String from) {
        Intent intent = new Intent(context, AddClaimActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_claim_layout);
        initData();
        initView();
    }

    private void initData() {
        calendarNow = Calendar.getInstance();
        y = calendarNow.get(Calendar.YEAR);
        m = calendarNow.get(Calendar.MONTH);
        d = calendarNow.get(Calendar.DAY_OF_MONTH);

        checkerIdList = new ArrayList<>();
        checkerNameList = new ArrayList<>();
        ArrayList<BaseBean> arrayList = Company.getUsers(getApplicationContext());
        if (arrayList == null || arrayList.size() <= 0) {//当前还没有建立新项目
            ToastUtils.show(getApplicationContext(), "请先添加员工再指定执行者", Toast.LENGTH_LONG);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                checkerIdList.add(arrayList.get(i).getInt("user_id"));
                checkerNameList.add(arrayList.get(i).getStr("user_name"));

            }
        }


    }

    private void initView() {
        actionBar = (MyActionBar) findViewById(R.id.add_claim_layout_actionbar);
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
                String checkName=checkerSpinner.getSelectedItem().toString();
                String date=dateText.getText().toString();
                String money=monryEdit.getText().toString();
                if(!StringUtils.isEmpty(checkName)&&!StringUtils.isEmpty(radioString)&&!StringUtils.isEmpty(money)&&!StringUtils.isEmpty(date)){
                    addDataToSql(checkName,date,radioString,money);
                    finish();
                }else {
                    ToastUtils.show(getApplicationContext(),"请填写完整的报销单");
                }

            }
        });

        checkerSpinner = (Spinner) findViewById(R.id.add_claim_layout_spinner);
        checkerSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, checkerNameList));
        checkerSpinner.setSelection(0);
        checkerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkerSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                checkerSpinner.setSelection(0);

            }
        });
        dateText = (TextView) findViewById(R.id.add_claim_layout_date);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.add_claim_layout_rgp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId=group.getCheckedRadioButtonId();
                RadioButton radioButton= (RadioButton) findViewById(checkedRadioButtonId);
                radioString=radioButton.getText().toString();
            }
        });
        monryEdit = (EditText) findViewById(R.id.add_claim_layout_money);
    }

    private void addDataToSql(String checkerName, String date, String radioString, String money) {
        int checkerId = 0;
        for (int i = 0; i < checkerNameList.size(); i++) {
            if (checkerNameList.get(i).equals(checkerName)) {
                checkerId = checkerIdList.get(i);
                break;
            }
        }

        String time = Common.getNowTimeNoSecond();
        SQLiteDatabase sqlDataBase = new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();
        sqlDataBase.execSQL("insert into checkwork (check_creater,check_createtime,check_checker,check_checktime,claim_time,claim_reason,claim_money,check_state,company_id) values (?,?,?,?,?,?,?,?,?);",
                new String[]{String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0))
                        , time
                        , String.valueOf(checkerId)
                        , time
                        , date
                        , radioString
                        , money
                        , String.valueOf(0)
                        , String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.companyId, 0))});


    }

    /**
     * 弹出日期选择的对话框
     */

    private void setDate() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendarBir = Calendar.getInstance();
                calendarBir.set(year, monthOfYear, dayOfMonth);
                Date date = new Date(calendarBir.getTimeInMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String showDate = simpleDateFormat.format(date);
                dateText.setText(showDate);
            }

        }, y, m, d).show();
    }
}
