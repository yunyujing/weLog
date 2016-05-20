package com.zzia.graduation.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zzia.graduation.bean.Company;
import com.zzia.graduation.bean.User;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.common.util.ToastUtils;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.utils.SharedPreferenceUtils;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.MainActivity;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/29
 */
public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener{
    private MyActionBar actionBar;
    private Spinner projectSpinner;
    private ArrayList<Integer> projectIdList;
    private ArrayList<String> projectList;
    private EditText nameEdit;
    private TextView createrText;
    private int createrId=0;
    private Spinner excuterSpinner;
    private ArrayList<Integer> excuterIdList;
    private ArrayList<String> excuterList;
    private TextView createTimeText, endTimeText;
    private String createTime="", endTime="";
    private Spinner state;

    public static void startAddTaskActivity(Context context, String from) {
        Intent intent = new Intent(context, AddTaskActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_layout);
        initData();
        initView();
    }

    private void initData() {
        createrId=Integer.parseInt(String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id,0)));
        createTime = Common.getNowDate();

        projectIdList=new ArrayList<>();
        projectList = new ArrayList<>();
        ArrayList<BaseBean> list = Company.getProjects(getApplicationContext());
        if (list == null || list.size() <= 0) {//当前还没有建立新项目
            ToastUtils.show(getApplicationContext(), "请先创建项目再新建任务", Toast.LENGTH_LONG);
        } else {
            for (int i = 0; i < list.size(); i++) {
                projectIdList.add(list.get(i).getInt("project_id"));
                projectList.add(list.get(i).getStr("project_name"));
            }
        }

        excuterIdList=new ArrayList<>();
        excuterList = new ArrayList<>();
        ArrayList<BaseBean> arrayList = Company.getUsers(getApplicationContext());
        if (list == null || list.size() <= 0) {//当前还没有建立新项目
            ToastUtils.show(getApplicationContext(), "请先添加员工再指定执行者", Toast.LENGTH_LONG);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                excuterIdList.add(arrayList.get(i).getInt("user_id"));
                excuterList.add(arrayList.get(i).getStr("user_name"));

            }
        }


    }

    private void initView() {
        actionBar = (MyActionBar) findViewById(R.id.edit_add_task_layout_actionbar);
        actionBar.setBackAction(new MyActionBar.BackAction(this));
        actionBar.setRight2Action(new MyActionBar.Action() {
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
                String name = nameEdit.getText().toString();
                String project = projectSpinner.getSelectedItem().toString();
                int projectId=0;
                for(int i=0;i<projectList.size();i++){
                    if(project.equals(projectList.get(i))){
                        projectId=projectIdList.get(i);
                    }
                }

                String excuter = excuterSpinner.getSelectedItem().toString();
                int excuterId=0;
                for(int i=0;i<excuterList.size();i++){
                    if(excuter.equals(excuterList.get(i))){
                        excuterId=excuterIdList.get(i);
                    }
                }

                String stateString=state.getSelectedItem().toString();
                int taskState=0;
                if(stateString.equals("进行中")){
                    taskState=1;
                }else if (stateString.equals("测试中")){
                    taskState=2;
                }else if(stateString.equals("已完成")){
                    taskState=3;
                }
                addDataToSql(projectId, name,createrId ,createTime,excuterId,endTime,taskState);

            }
        });

        projectSpinner = (Spinner) findViewById(R.id.edit_add_task_layout_choice_project);
        projectSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, projectList));
        projectSpinner.setSelection(0);
        projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                projectSpinner.setSelection(0);
            }
        });
        nameEdit = (EditText) findViewById(R.id.edit_add_task_layout_task_name);
        createrText = (TextView) findViewById(R.id.edit_add_task_layout_create_name);
        createrText.setText(String.valueOf(SharedPreferenceUtils.get(getApplicationContext(),User.name,"")));
        excuterSpinner = (Spinner) findViewById(R.id.edit_add_task_layout_excute_name);
        excuterSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, excuterList));
        excuterSpinner.setSelection(0);
        excuterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                excuterSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                excuterSpinner.setSelection(0);
            }
        });

        createTimeText = (TextView) findViewById(R.id.edit_add_task_layout_createtime);
        createTimeText.setText(createTime);
        endTimeText = (TextView) findViewById(R.id.edit_add_task_layout_endtime);
        endTimeText.setText("");
        endTimeText.setOnClickListener(this);

        state= (Spinner) findViewById(R.id.edit_add_task_layout_state);
        state.setSelection(0);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                state.setSelection(0);

            }
        });

    }

    /**
     * 将数据添加到数据库
     * @param projectId
     * @param name
     * @param createrId
     * @param createTime
     * @param excuterId
     * @param endTime
     * @param state
     */
    private void addDataToSql(int projectId, String name, int createrId, String createTime, int excuterId, String endTime, int state) {

        SQLiteDatabase sqLiteDatabase=new MySQLiteOpenHelper(getApplicationContext()).getWritableDatabase();
        sqLiteDatabase.execSQL("insert into task (task_name,task_creater,task_createtime,project_id,task_excuter,task_endtime,task_state) values " +
                "(?,?,?,?,?,?,?)",new String[]{name,String.valueOf(createrId),createTime,String.valueOf(projectId),String.valueOf(excuterId),endTime,String.valueOf(state)});

        //插入成功之后返回,不能直接finnish();那样数据不会刷新
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("show","task");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.edit_add_task_layout_endtime:
//                showDatePickerDialog();
                break;
        }
    }

}
