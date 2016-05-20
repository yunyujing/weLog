package com.zzia.graduation.welog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.zzia.graduation.bean.User;
import com.zzia.graduation.common.util.Isvalid;
import com.zzia.graduation.common.util.StringUtils;
import com.zzia.graduation.common.util.ToastUtils;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.SharedPreferenceUtils;

import java.util.ArrayList;

/**
 * 登录页面
 * Author: yunyujing
 * Date: 2016/04/25
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {


    private View mLoginFormView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private Button mLoginButton;

    private View mSignFormView;
    private EditText companyName;
    private EditText companyAddress;
    private EditText companyEmail;
    private EditText companyPassword;
    private EditText companySurePassword;
    private GridLayout addDeptView;
    private TextView addDept;
    private Button sureSign;

    private ArrayList<String> depts;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //fullScreen

        setContentView(R.layout.activity_login);

        sqLiteDatabase = new MySQLiteOpenHelper(this).getWritableDatabase();

        initView();

    }

    private void initView() {
        mLoginFormView = findViewById(R.id.login_form);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginFormView.setVisibility(View.GONE);
                mSignFormView.setVisibility(View.VISIBLE);
            }
        });
        mLoginButton = (Button) findViewById(R.id.email_login_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        mSignFormView = findViewById(R.id.sign_in_form);
        companyName = (EditText) findViewById(R.id.commpany);
        companyAddress = (EditText) findViewById(R.id.commpany_address);
        companyEmail = (EditText) findViewById(R.id.company_email);
        companyPassword = (EditText) findViewById(R.id.company_password);
        companySurePassword = (EditText) findViewById(R.id.sure_company_password);
        depts = new ArrayList<>();
        addDeptView = (GridLayout) findViewById(R.id.activity_login_add_dept_gl);
        addDept = (TextView) findViewById(R.id.activity_login_add_dept);
        addDept.setOnClickListener(this);
        sureSign = (Button) findViewById(R.id.sure_sign);
        sureSign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSign();
            }
        });


    }


    /**
     * 注册
     */
    private void attemptSign() {
        String name = companyName.getText().toString();
        String address = companyAddress.getText().toString();
        String email = companyEmail.getText().toString();
        String password = companyPassword.getText().toString();
        String surePasswprd = companySurePassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            companyName.setError(getString(R.string.error_field_required));
            focusView = companyName;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            companyEmail.setError(getString(R.string.error_field_required));
            focusView = companyEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            companyEmail.setError(getString(R.string.error_invalid_email));
            focusView = companyEmail;
            cancel = true;
        } else if (ifEmailExists(email)) {
            companyEmail.setError(getString(R.string.error_exists_email));
            focusView = companyEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            companyPassword.setError(getString(R.string.error_field_required));
            focusView = companyPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            companyPassword.setError(getString(R.string.error_invalid_password));
            focusView = companyPassword;
            cancel = true;
        } else if (TextUtils.isEmpty(surePasswprd)) {
            companySurePassword.setError(getString(R.string.error_field_required));
            focusView = companySurePassword;
            cancel = true;
        } else if (!password.equals(surePasswprd)) {
            companySurePassword.setError(getString(R.string.error_password_not_match));
            focusView = companySurePassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            addDataToSql(name, email, password, address);

        }
    }

    /**
     * 注册邮箱是否已存在
     *
     * @param email
     * @return
     */
    private boolean ifEmailExists(String email) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from company where company_email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    /**
     * 将注册信息添加到数据库并登录
     *
     * @param name
     * @param email
     * @param password
     */
    private void addDataToSql(String name, String email, String password, String address) {

        //获取部门信息
        for (int i = 0; i < addDeptView.getChildCount(); i++) {

            CheckBox checkBox = (CheckBox) addDeptView.getChildAt(i);

            if (checkBox.isChecked()) {
                depts.add(checkBox.getText().toString());

            }
        }
        if (depts == null || depts.size() <= 0) {
            ToastUtils.show(getApplicationContext(), "请设置公司部门");
        } else {
            //将注册账号添加到数据库并登录
            sqLiteDatabase.execSQL("INSERT INTO company(company_name,company_email,company_password,company_address) VALUES (?,?,?,?); ", new String[]{name, email, password, address});
            Cursor cursor = sqLiteDatabase.rawQuery("select * from company " +
                    "where company_name=? and company_email=? and company_password=? and company_address=? ", new String[]{name, email, password, address});
            if (cursor.moveToFirst()) {

                //登录信息存储到SharedPreferences并登录
                SharedPreferenceUtils.put(getApplicationContext(), User.isCompany, true);
                SharedPreferenceUtils.put(getApplicationContext(), User.companyId, cursor.getInt(cursor.getColumnIndex("company_id")));
                SharedPreferenceUtils.put(getApplicationContext(), User.id, cursor.getInt(cursor.getColumnIndex("company_id")));
                SharedPreferenceUtils.put(getApplicationContext(), User.name, name);
                SharedPreferenceUtils.put(getApplicationContext(), User.email, email);
                SharedPreferenceUtils.put(getApplicationContext(), User.address, address);

            }

            //将部门信息添加到部门表
            if (depts != null && depts.size() > 0) {
                for (int i = 0; i < depts.size(); i++) {
                    sqLiteDatabase.execSQL("INSERT INTO department(dept_name,dept_num,company_id) VALUES (?," + 0 + ",?); ",
                            new String[]{depts.get(i), String.valueOf(cursor.getInt(cursor.getColumnIndex("company_id")))});
                }

            }

            cursor.close();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


    /**
     * 登录
     */
    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Cursor cursorCompany = sqLiteDatabase.rawQuery("SELECT * FROM company where company_email=?", new String[]{email});
            Cursor cursor = sqLiteDatabase.rawQuery("select * from user where user_email=?", new String[]{email});
            if (cursorCompany.getCount() == 0 && cursor.getCount() == 0) {
                ToastUtils.show(this, "邮箱不存在");
            } else {

                if (cursorCompany.moveToFirst()) {
                    if (cursorCompany.getString(cursorCompany.getColumnIndex("company_email")).equals(email)) {//企业邮箱
                        if (!cursorCompany.getString(cursorCompany.getColumnIndex("company_password")).equals(password)) {
                            ToastUtils.show(this, "密码错误");
                        } else {
                            //登录信息存储到SharedPreferences并登录
                            SharedPreferenceUtils.put(getApplicationContext(), User.isCompany, true);
                            SharedPreferenceUtils.put(getApplicationContext(), User.companyId, cursorCompany.getInt(cursorCompany.getColumnIndex("company_id")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.id, cursorCompany.getInt(cursorCompany.getColumnIndex("company_id")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.name, cursorCompany.getString(cursorCompany.getColumnIndex("company_name")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.email, email);
                            SharedPreferenceUtils.put(getApplicationContext(), User.address, cursorCompany.getString(cursorCompany.getColumnIndex("company_address")));

                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }
                cursorCompany.close();


                if (cursor.moveToFirst()) {

                    if (cursor.getString(cursor.getColumnIndex("user_email")).equals(email)) {//个人邮箱
                        if (!cursor.getString(cursor.getColumnIndex("user_password")).equals(password)) {
                            ToastUtils.show(this, "密码错误");
                        } else {
                            //登录信息存储到SharedPreferences并登录
                            SharedPreferenceUtils.put(getApplicationContext(), User.isCompany, false);
                            int managerValue = cursor.getInt(cursor.getColumnIndex("user_ismanager"));
                            if (managerValue == 0) {
                                SharedPreferenceUtils.put(getApplicationContext(), User.isManager, false);
                            } else {
                                SharedPreferenceUtils.put(getApplicationContext(), User.isManager, true);
                            }
                            SharedPreferenceUtils.put(getApplicationContext(), User.companyId, Integer.parseInt(User.getBasicInfo(getApplicationContext(),cursor.getInt(cursor.getColumnIndex("user_id"))).get("company_id")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.id, cursor.getInt(cursor.getColumnIndex("user_id")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.deptName, User.getBasicInfo(getApplicationContext(),0).get("dept_name"));
                            SharedPreferenceUtils.put(getApplicationContext(), User.name, cursor.getString(cursor.getColumnIndex("user_name")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.sex, cursor.getString(cursor.getColumnIndex("user_sex")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.age, cursor.getString(cursor.getColumnIndex("user_age")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.email, email);
                            SharedPreferenceUtils.put(getApplicationContext(), User.tel, cursor.getString(cursor.getColumnIndex("user_tel")));
                            SharedPreferenceUtils.put(getApplicationContext(), User.address, cursor.getString(cursor.getColumnIndex("user_address")));


                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }
                cursor.close();
            }


        }
    }

    private boolean isEmailValid(String email) {
        return Isvalid.isEmail(email);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_add_dept:
                editDept();
                break;
        }
    }

    /**
     * 自定义部门
     */
    private void editDept() {

        final EditText addEditText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加部门")
                .setView(addEditText)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        //动态加载部门名称
                        String deptName = addEditText.getText().toString();
                        if (!StringUtils.isEmpty(deptName)) {

                            int rowCount = addDeptView.getRowCount();
                            int count = addDeptView.getChildCount();
                            GridLayout.LayoutParams params = null;
                            int columnNum = count % 3;
                            if (columnNum == 0) {//现在的行列刚好放下
                                GridLayout.Spec rowSpec = GridLayout.spec(rowCount);     //设置它的行和列
                                GridLayout.Spec columnSpec = GridLayout.spec(0);
                                params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                            } else {
                                GridLayout.Spec rowSpec = GridLayout.spec(rowCount - 1);     //设置它的行和列
                                GridLayout.Spec columnSpec = GridLayout.spec(columnNum);
                                params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                            }

                            CheckBox checkBox = new CheckBox(LoginActivity.this);
                            checkBox.setText(deptName);
                            checkBox.setChecked(true);

                            addDeptView.addView(checkBox, params);

                        }
                    }
                })
                .show();

    }

}

