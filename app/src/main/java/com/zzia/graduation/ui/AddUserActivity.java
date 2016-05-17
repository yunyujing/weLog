package com.zzia.graduation.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.zzia.graduation.bean.Dept;
import com.zzia.graduation.common.util.Isvalid;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 添加新员工
 * Created by yunyujing on 16/5/15.
 */
public class AddUserActivity extends AppCompatActivity {

    private MyActionBar myActionBar;
    private EditText userName, userEmail, userpassword, userSurePassword;
    private Spinner deptSpinner, identitySpinner;
    private Button addBtn;
    private HashMap<Integer, String> depts;
    private ArrayList<Integer> deptId;
    private ArrayList<String> deptName;
    private ArrayAdapter<String> mySpinnerAdapter;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_layout);
        sqLiteDatabase=new MySQLiteOpenHelper(this).getWritableDatabase();
        initView();
    }

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.add_user_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
        userName = (EditText) findViewById(R.id.add_user_layout_name);
        userEmail = (EditText) findViewById(R.id.add_user_layout_email);
        userpassword = (EditText) findViewById(R.id.add_user_layout_password);
        userSurePassword = (EditText) findViewById(R.id.add_user_layout_sure_password);
        addBtn = (Button) findViewById(R.id.add_user_layout_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString();
                String email = userEmail.getText().toString();
                String password = userpassword.getText().toString();
                String surePasswprd = userSurePassword.getText().toString();

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(name)) {
                    userName.setError(getString(R.string.error_field_required));
                    focusView = userName;
                    cancel = true;
                }

                if (TextUtils.isEmpty(email)) {
                    userEmail.setError(getString(R.string.error_field_required));
                    focusView = userEmail;
                    cancel = true;
                } else if (!Isvalid.isEmail(email)) {
                    userEmail.setError(getString(R.string.error_invalid_email));
                    focusView = userEmail;
                    cancel = true;
                } else if (ifEmailExists(email)) {
                    userEmail.setError(getString(R.string.error_exists_email));
                    focusView = userEmail;
                    cancel = true;
                }

                if (TextUtils.isEmpty(password)) {
                    userpassword.setError(getString(R.string.error_field_required));
                    focusView = userpassword;
                    cancel = true;
                } else if (!isPasswordValid(password)) {
                    userpassword.setError(getString(R.string.error_invalid_password));
                    focusView = userpassword;
                    cancel = true;
                } else if (TextUtils.isEmpty(surePasswprd)) {
                    userSurePassword.setError(getString(R.string.error_field_required));
                    focusView = userSurePassword;
                    cancel = true;
                } else if (!password.equals(surePasswprd)) {
                    userSurePassword.setError(getString(R.string.error_password_not_match));
                    focusView = userSurePassword;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {

                    addDataToSql(name, email, password);

                }
            }
        });

        deptSpinner = (Spinner) findViewById(R.id.add_user_layout_dept);
        depts = new HashMap<>();
        deptId = new ArrayList<>();
        deptName = new ArrayList<>();

        depts = Dept.getDepts(getApplicationContext());

        Iterator iter = depts.keySet().iterator();
        while (iter.hasNext()) {
            Integer key = (Integer) iter.next();
            deptId.add(key);
        }

        for (int i = 0; i < deptId.size(); i++) {
            String value = depts.get(deptId.get(i));
            deptName.add(value);
        }

        mySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, deptName);
        deptSpinner.setAdapter(mySpinnerAdapter);
        deptSpinner.setSelection(0);
        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deptSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                deptSpinner.setSelection(1);
            }
        });

        identitySpinner = (Spinner) findViewById(R.id.add_user_layout_identity);
        identitySpinner.setSelection(0);
        identitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                identitySpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                identitySpinner.setSelection(0);
            }
        });
    }

    /**
     * 将数据添加到数据库
     * @param name
     * @param email
     * @param password
     */
    private void addDataToSql(String name, String email, String password) {

        String selectDeptName = deptSpinner.getSelectedItem().toString();
        int selectDeptid=0;
        for (int i = 0; i < deptName.size(); i++) {

            if(selectDeptName.equals(deptName.get(i))){
                selectDeptid=deptId.get(i);
            }
        }
        String identity = identitySpinner.getSelectedItem().toString();
        int isManager=0;
        if(identity.equals("超级管理员")){
            isManager=2;
        }else if (identity.equals("部门经理")){
            isManager=1;
        }
        //将数据插入到数据库
        sqLiteDatabase.execSQL("insert into user (dept_id,user_email,user_password,user_name,user_ismanager) values" +
                "("+selectDeptid+",'"+email+ "',"+password+",'"+name+"',"+isManager+");");



    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }


    /**
     * 用户邮箱是否已存在
     *
     * @param email
     * @return
     */
    private boolean ifEmailExists(String email) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from user where user_email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }
}