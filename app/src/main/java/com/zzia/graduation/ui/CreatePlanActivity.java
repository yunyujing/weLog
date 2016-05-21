package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.zzia.graduation.adapters.CheckLargeImageGridAdapter;
import com.zzia.graduation.bean.CheckWork;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/21.
 */
public class CreatePlanActivity extends AppCompatActivity {


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

    public static void startCreatePlanActivity(Context context, BaseBean baseBean) {
        Intent intent = new Intent(context, CreatePlanActivity.class);
        intent.putExtra("check_id", baseBean.getInt("check_id"));
        intent.putExtra("check_creater", baseBean.getStr("check_creater"));
        intent.putExtra("check_createtime", baseBean.getStr("check_createtime"));
        intent.putExtra("check_checker", baseBean.getStr("check_checker"));
        intent.putExtra("check_checktime", baseBean.getStr("check_checktime"));
        intent.putExtra("plan_title", baseBean.getStr("plan_title"));
        intent.putExtra("plan_info", baseBean.getStr("plan_info"));
        intent.putExtra("check_state", baseBean.getInt("check_state"));
        intent.putExtra("check_comment", baseBean.getStr("check_comment"));
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        title = intent.getStringExtra("plan_title");
        content = intent.getStringExtra("plan_info");
        state = intent.getIntExtra("check_state", 0);
        comment=intent.getStringExtra("check_comment");

        list = new ArrayList<>();
        list = CheckWork.getCheckImages(getApplicationContext(), checkId);

    }

    private void initView() {
        myActionBar = (MyActionBar) findViewById(R.id.create_plan_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
//        myActionBar.setRight2Action(new MyActionBar.Action() {
//            @Override
//            public int getDrawable() {
//                return R.mipmap.actionbar_edit_complete;
//            }
//
//            @Override
//            public int getText() {
//                return 0;
//            }
//
//            @Override
//            public void performAction(View view) {
//                //将审批结果发送到数据库
//                String stateString = stateSpinner.getSelectedItem().toString();
//                int stateInt = 0;
//                if (stateString.equals("通过")) {
//                    stateInt = 1;
//                } else if (stateString.equals("驳回")) {
//                    stateInt = 2;
//                }
//                String commment = checkEdit.getText().toString();
//                addDataToSql(checkId, stateInt, commment);
//
//            }
//        });

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
//        stateSpinner = (Spinner) findViewById(R.id.create_plan_layout_state);
//        stateSpinner.setSelection(state);
//        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                stateSpinner.setSelection(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                stateSpinner.setSelection(state);
//
//            }
//        });


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
