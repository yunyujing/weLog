package com.zzia.graduation.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzia.graduation.bean.User;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.utils.SharedPreferenceUtils;
import com.zzia.graduation.views.MyActionBar;
import com.zzia.graduation.welog.MainActivity;
import com.zzia.graduation.welog.R;

/**
 * Author: yunyujing
 * Date: 2015/12/29
 */
public class AddProjectActivity extends Activity {
    private MyActionBar actionBar;
    private EditText nameEdit, infoEdit;
    private Spinner spinner;
    private SimpleDraweeView icon;

    private String name, info, owner, iconUrl;


    public static void startAddProjectActivity(Context context, String from) {
        Intent intent = new Intent(context, AddProjectActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_project_layout);
        initView();
    }

    private void initView() {
        actionBar = (MyActionBar) findViewById(R.id.add_project_actionbar);
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
                name = nameEdit.getText().toString();
                info = infoEdit.getText().toString();
                owner = spinner.getSelectedItem().toString();
                if (!name.isEmpty()) {
                    addDataToSql(name, info, owner, iconUrl);
                }
            }

        });

        nameEdit = (EditText) findViewById(R.id.add_project_layout_name);
        infoEdit = (EditText) findViewById(R.id.add_project_layout_info);
        icon = (SimpleDraweeView) findViewById(R.id.add_project_layout_icon);
        spinner = (Spinner) findViewById(R.id.add_project_layout_owner);
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(1);
            }
        });

    }

    /**
     * 添加数据到project表
     *
     * @param name
     * @param info
     * @param owner
     * @param iconUrl
     */
    private void addDataToSql(String name, String info, String owner, String iconUrl) {
        int isPublic=0;
        if(owner.equals("公共项目")){
            isPublic=1;
        }
        String userId = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.id, 0));
        String companyId = String.valueOf(SharedPreferenceUtils.get(getApplicationContext(), User.companyId, 0));
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(this).getWritableDatabase();

        sqLiteDatabase.execSQL("insert into project (project_name,project_info,project_ispublic,project_creater,project_createtime,company_id) values (?,?,?,?,?,?)",
                new String[]{name, info, String.valueOf(isPublic), userId, Common.getNowTime(),companyId});

        //插入成功之后返回,不能直接finnish();那样数据不会刷新
        startActivity(new Intent(this, MainActivity.class));

    }
}
