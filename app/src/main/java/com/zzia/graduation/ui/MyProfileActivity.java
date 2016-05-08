package com.zzia.graduation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

/**
 * 个人中心
 * Created by yunyujing on 4/27/16.
 */
public class MyProfileActivity extends AppCompatActivity {

    private MyActionBar myActionBar;
    private SimpleDraweeView headImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_layout);
        getData();
        initView();
    }

    public void getData() {
    }

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.my_profile_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));

        headImage = (SimpleDraweeView) findViewById(R.id.my_profile_head_img);

        findViewById(R.id.my_profile_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑资料
            }
        });
    }


}
