package com.zzia.graduation.welog;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.zzia.graduation.common.util.ToastUtils;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.SharedPreferenceUtils;

/**
 * 登录页面
 * Author: yunyujing
 * Date: 2016/04/25
 */
public class LoginActivity extends AppCompatActivity {

    private View mLoginFormView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private Button mLoginButton;

    private View mSignFormView;
    private EditText companyName;
    private EditText companyEmail;
    private EditText companyPassword;
    private EditText companySurePassword;
    private Button sureSign;

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
        companyEmail = (EditText) findViewById(R.id.company_email);
        companyPassword = (EditText) findViewById(R.id.company_password);
        companySurePassword = (EditText) findViewById(R.id.sure_company_password);
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

            addDataToSql(name, email, password);

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
    private void addDataToSql(String name, String email, String password) {
        //将数据添加到数据库
        sqLiteDatabase.execSQL("INSERT INTO company(company_name,company_email,company_password) VALUES (?,?,?); ", new String[]{name, email, password});
        //登录信息存储到SharedPreferences并登录
        SharedPreferenceUtils.getSharedPreferenceUtils(this).setValue("isCompany", true);
        SharedPreferenceUtils.getSharedPreferenceUtils(this).setValue("login_id", email);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


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
                        if (!cursorCompany.getString(3).equals(password)) {
                            ToastUtils.show(this, "密码错误");
                        } else {
                            //登录信息存储到SharedPreferences并登录
                            SharedPreferenceUtils.getSharedPreferenceUtils(this).setValue("isCompany", true);
                            SharedPreferenceUtils.getSharedPreferenceUtils(this).setValue("login_id", cursorCompany.getString(0));

                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                }
                cursorCompany.close();


                if(cursor.moveToFirst()){

                    if (cursor.getString(cursor.getColumnIndex("user_email")).equals(email)) {//个人邮箱
                        if (!cursor.getString(3).equals(password)) {
                            ToastUtils.show(this, "密码错误");
                        } else {
                            //登录信息存储到SharedPreferences并登录
                            SharedPreferenceUtils.getSharedPreferenceUtils(this).setValue("isCompany", false);
                            SharedPreferenceUtils.getSharedPreferenceUtils(this).setValue("login_id", cursor.getString(0));

                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                }
                cursor.close();
            }


        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }


}

