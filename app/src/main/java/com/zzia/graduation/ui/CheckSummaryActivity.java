package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.zzia.graduation.adapters.CheckLargeImageGridAdapter;
import com.zzia.graduation.bean.CheckWork;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.common.util.ToastUtils;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * 工作总结列表
 * Created by yunyujing on 16/5/4.
 */
public class CheckSummaryActivity extends AppCompatActivity {

    private MyActionBar myActionBar;
    private TextView createrText, checkerText, createtimeText, checktimeText, titleText, contentText;
    private int checkId;
    private String creater, checker, createtime, checktime, title, content;
    private int state;
    private Spinner stateSpinner;
    private EditText checkEdit;

    private GridView gridView;
    private CheckLargeImageGridAdapter checkLargeImageGridAdapter;
    private ArrayList<String> list;

    public static void startCheckSummaryActivity(Context context, BaseBean baseBean){

        Intent intent=new Intent(context,CheckSummaryActivity.class);
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
        setContentView(R.layout.check_plan_layout);
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
        myActionBar = (MyActionBar) findViewById(R.id.check_plan_layout_actionbar);
        myActionBar.setBackAction(new MyActionBar.BackAction(this));
        myActionBar.setTitle("工作总结");
        myActionBar.setRight2Action(new MyActionBar.Action() {
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
                //将审批结果发送到数据库
                String stateString = stateSpinner.getSelectedItem().toString();
                int stateInt = 0;
                if (stateString.equals("通过")) {
                    stateInt = 1;
                } else if (stateString.equals("驳回")) {
                    stateInt = 2;
                }
                String commment = checkEdit.getText().toString();
                addDataToSql(checkId, stateInt, commment);

            }
        });

        createrText = (TextView) findViewById(R.id.check_plan_layout_creater);
        createrText.setText(creater);
        createtimeText = (TextView) findViewById(R.id.check_plan_layout_createtime);
        createtimeText.setText(createtime);
        checkerText= (TextView) findViewById(R.id.check_plan_layout_checker);
        checkerText.setText(checker);
        checktimeText= (TextView) findViewById(R.id.check_plan_layout_checketime);
        checktimeText.setText(checktime);
        titleText = (TextView) findViewById(R.id.check_plan_layout_title);
        titleText.setText(title);
        contentText = (TextView) findViewById(R.id.check_plan_layout_content);
        contentText.setText(content);
        stateSpinner = (Spinner) findViewById(R.id.check_plan_layout_state);
        stateSpinner.setSelection(state);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stateSpinner.setSelection(state);

            }
        });


        gridView = (GridView) findViewById(R.id.check_plan_layout_grid);
        if (list != null && !list.isEmpty()) {
            checkLargeImageGridAdapter = new CheckLargeImageGridAdapter(this, list);
            gridView.setAdapter(checkLargeImageGridAdapter);
            gridView.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.GONE);
        }

        checkEdit = (EditText) findViewById(R.id.check_plan_layout_comment);

    }

    /**
     * 将数据添加到数据库
     * @param checkId
     * @param stateId
     * @param commment
     */
    private void addDataToSql(int checkId, int stateId, String commment) {
        if (stateId == this.state && commment.isEmpty()) {
            ToastUtils.show(getApplicationContext(), "没有审核，不能进行提交");
        } else {

            SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();
            if (stateId != this.state) {

                sqLiteDatabase.execSQL("update checkwork set check_state=? where check_id=? ;",
                        new String[]{String.valueOf(stateId), String.valueOf(checkId)});
            }
            if (commment != null && !commment.isEmpty()) {

                sqLiteDatabase.execSQL("update checkwork set check_comment=? where check_id=? ;",
                        new String[]{String.valueOf(commment), String.valueOf(checkId)});
            }

            finish();
        }

    }
}
