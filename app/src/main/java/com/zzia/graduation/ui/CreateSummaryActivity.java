package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.zzia.graduation.adapters.CheckLargeImageGridAdapter;
import com.zzia.graduation.bean.CheckWork;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/22.
 */
public class CreateSummaryActivity extends AppCompatActivity{

    private MyActionBar myActionBar;
    private TextView createrText, checkerText, createtimeText, checktimeText, titleText, contentText;
    private int checkId;
    private String creater, checker, createtime, checktime, title, content;
    private TextView stateText, commentText;
    private int state;
    private String comment;

    private GridView gridView;
    private CheckLargeImageGridAdapter checkLargeImageGridAdapter;
    private ArrayList<String> list;

    public static void startCreateSummaryActivity(Context context, BaseBean baseBean) {
        Intent intent=new Intent(context,CreateSummaryActivity.class);
        intent.putExtra("check_id", baseBean.getInt("check_id"));
        intent.putExtra("check_creater", baseBean.getStr("check_creater"));
        intent.putExtra("check_createtime", baseBean.getStr("check_createtime"));
        intent.putExtra("check_checker", baseBean.getStr("check_checker"));
        intent.putExtra("check_checktime", baseBean.getStr("check_checktime"));
        intent.putExtra("summary_title", baseBean.getStr("summary_title"));
        intent.putExtra("summary_info", baseBean.getStr("summary_info"));
        intent.putExtra("check_state", baseBean.getInt("check_state"));
        context.startActivity(intent);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_plan_layout);
        initData();
        initView();

    }
    private void initData() {
        Intent intent = getIntent();
        checkId = intent.getIntExtra("check_id", 0);
        creater = intent.getStringExtra("check_creater");
        createtime = intent.getStringExtra("check_createtime");
        checker = intent.getStringExtra("check_checker");
        checktime = intent.getStringExtra("check_checktime");
        title = intent.getStringExtra("summary_title");
        content = intent.getStringExtra("summary_info");
        state = intent.getIntExtra("check_state", 0);

        list = new ArrayList<>();
        list = CheckWork.getCheckImages(getApplicationContext(), checkId);

    }


    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.create_plan_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
        myActionBar.setTitle("工作总结");

        createrText = (TextView) findViewById(R.id.create_plan_layout_creater);
        createrText.setText(creater);
        createtimeText = (TextView) findViewById(R.id.create_plan_layout_createtime);
        createtimeText.setText(createtime);
        checkerText = (TextView) findViewById(R.id.create_plan_layout_checker);
        checkerText.setText(checker);
        checktimeText = (TextView) findViewById(R.id.create_plan_layout_checketime);
        checktimeText.setText(checktime);
        titleText = (TextView) findViewById(R.id.create_plan_layout_title);
        titleText.setText(title);
        contentText = (TextView) findViewById(R.id.create_plan_layout_content);
        contentText.setText(content);


        gridView = (GridView) findViewById(R.id.create_plan_layout_grid);
        if (list != null && !list.isEmpty()) {
            checkLargeImageGridAdapter = new CheckLargeImageGridAdapter(this, list);
            gridView.setAdapter(checkLargeImageGridAdapter);
            gridView.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.GONE);
        }

        stateText= (TextView) findViewById(R.id.create_plan_layout_state);
        if(state==0){
            stateText.setText("待审核");
        }else if(state==1){
            stateText.setText("通过");
        }else {
            stateText.setText("驳回");
        }

        commentText = (TextView) findViewById(R.id.create_plan_layout_comment);
        commentText.setText(comment);

    }
}
