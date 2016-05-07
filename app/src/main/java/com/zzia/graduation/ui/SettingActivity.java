package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * Created by yunyujing on 4/27/16.
 */
public class SettingActivity extends AppCompatActivity {

    private MyActionBar myActionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        initView();
    }

    private void initView() {
        myActionBar= (MyActionBar) findViewById(R.id.setting_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
    }
}
