package com.zzia.graduation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzia.graduation.bean.User;
import com.zzia.graduation.common.util.StringUtils;
import com.zzia.graduation.utils.SharedPreferenceUtils;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.LoginActivity;
import com.zzia.graduation.welog.R;

/**
 * 个人中心
 * Created by yunyujing on 4/27/16.
 */
public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private MyActionBar myActionBar;
    private SimpleDraweeView headImage;
    private TextView identity;
    private TextView personText;
    private TextView emailText;
    private TextView deptText;
    private TextView telText;
    private TextView addressText;
    private TextView addOrEdit;
    private Button changeUser;

    private String name;
    private String sex;
    private String age;
    private String email;
    private String department;
    private String tel;
    private String address;
    private boolean isManager;
    private boolean isCompany;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_layout);
        getData();
        initView();
    }

    public void getData() {
        isCompany = Boolean.parseBoolean(String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.isCompany, false)));
        isManager = Boolean.parseBoolean(String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.isManager, false)));
        name = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.name, ""));
        sex = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.sex, ""));

        age = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.age, ""));
        email = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.email, ""));
        department = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.deptName, ""));
        tel = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.tel, ""));
        address = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.address, ""));


    }

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.my_profile_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

        headImage = (SimpleDraweeView) findViewById(R.id.my_profile_head_img);
        identity = (TextView) findViewById(R.id.my_profile_introduce);
        personText = (TextView) findViewById(R.id.my_profile_layout_bottom_person);
        emailText = (TextView) findViewById(R.id.my_profile_layout_bottom_email);
        deptText = (TextView) findViewById(R.id.my_profile_layout_bottom_dept);
        telText = (TextView) findViewById(R.id.my_profile_layout_bottom_tel);
        addressText = (TextView) findViewById(R.id.my_profile_layout_bottom_address);

        addOrEdit = (TextView) findViewById(R.id.my_profile_add_user);
        addOrEdit.setOnClickListener(this);

        changeUser = (Button) findViewById(R.id.my_profile_layout_change_user);
        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, LoginActivity.class));
            }
        });

        if (isCompany) {

            identity.setText("企业账号");
            personText.setText("企业名称：" + name);
            emailText.setText("企业账号：" + email);
            addressText.setText("企业位置：" + address);
            addOrEdit.setText("添加员工");


        } else {

            addOrEdit.setText("编辑资料");

            identity.setText("员工账号");
            personText.setText("个人：" + name + "  " + sex + "  " + age);
            emailText.setText("账号：" + email);
            deptText.setText("部门：" + department);
            if (!StringUtils.isEmpty(tel)) {
                telText.setText("电话：" + tel);
                telText.setVisibility(View.VISIBLE);
            } else {
                telText.setVisibility(View.GONE);
            }

            if (!StringUtils.isEmpty(address)) {
                addressText.setVisibility(View.VISIBLE);
                addressText.setText("住址：" + address);
            } else {
                addressText.setVisibility(View.GONE);
            }

        }


        if (isCompany) {

        } else {
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_profile_add_user:

                if (isCompany) {
                    //添加新员工
                    startActivity(new Intent(MyProfileActivity.this, AddUserActivity.class));
                } else {
                    //编辑资料
                    startActivity(new Intent(MyProfileActivity.this, EditMyProfileActivity.class));

                }
                break;
        }
    }
}
