package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * Created by yunyujing on 16/5/15.
 */
public class EditMyProfileActivity extends AppCompatActivity {
    private MyActionBar myActionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_profile_layout);
        initView();
    }

    private void initView() {
        myActionBar= (MyActionBar) findViewById(R.id.edit_my_profile_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

    }
}
