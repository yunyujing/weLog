package com.zzia.graduation.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.zzia.graduation.adapters.SearchFragmentAdapter;
import com.zzia.graduation.bean.User;
import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.common.util.StringUtils;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.views.TipsLayout;
import com.zzia.graduation.welog.R;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/1
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private SearchView searchText;
    private LinearLayout searchContentll;
    private TextView searchContentText;
    private String content;
    private RecyclerView recyclerView;
    private ArrayList<BaseBean> arrayList;
    private SearchFragmentAdapter adapter;
    private TipsLayout tipsLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {

        searchContentText = (TextView) view.findViewById(R.id.search_layout_content);
        searchContentll = (LinearLayout) view.findViewById(R.id.search_layout_content_ll);
        searchContentll.setOnClickListener(this);
        searchText = (SearchView) view.findViewById(R.id.search_layout_edit);
        searchText.onActionViewExpanded();//设置搜索框为展开状态
        searchText.setSubmitButtonEnabled(true);//显示查询提交按钮
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //搜索内容提交
                if (!StringUtils.isEmpty(query)) {
//                    content = query;
//                    searchContentll.setVisibility(View.GONE);
                    if(arrayList.size()>0){
                        arrayList.clear();
                        adapter.notifyDataSetChanged();
                    }
                    search(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                //搜索内容改变
//                searchContentll.setVisibility(View.VISIBLE);
//                searchContentText.setText(newText);

                return false;
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.search_layout_history);
        recyclerView.addItemDecoration(new DetailProjectActivity.MyListItemDecoration(30));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter = new SearchFragmentAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);
        tipsLayout = (TipsLayout) view.findViewById(R.id.search_layout_tips);
        tipsLayout.showErrorTips("请输入搜索内容", R.mipmap.tips_error_load_failure);

    }

    /**
     * 搜索
     *
     * @param query
     */
    private void search(String query) {

        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(getActivity()).getWritableDatabase();
        if (StringUtils.isNumOrFloat(query)) {//正整数或正小数
            Cursor cursor = sqLiteDatabase.rawQuery("select * from checkwork where claim_money like ? ", new String[]{query});
            while (cursor.moveToNext()) {
                BaseBean plan = new BaseBean();
                plan.set("check_id", cursor.getInt(cursor.getColumnIndex("check_id")));
                plan.set("check_creater", User.getBasicInfo(getContext(), cursor.getInt(cursor.getColumnIndex("check_creater"))).get("user_name"));
                plan.set("check_checker", User.getBasicInfo(getContext(), cursor.getInt(cursor.getColumnIndex("check_checker"))).get("user_name"));
                plan.set("check_createtime", cursor.getString(cursor.getColumnIndex("check_createtime")));
                plan.set("check_checktime", cursor.getString(cursor.getColumnIndex("check_checktime")));
                plan.set("claim_time", cursor.getString(cursor.getColumnIndex("claim_time")));
                plan.set("claim_money", cursor.getString(cursor.getColumnIndex("claim_money")));
                plan.set("claim_reason", cursor.getString(cursor.getColumnIndex("claim_reason")));
                plan.set("check_state", cursor.getInt(cursor.getColumnIndex("check_state")));
                arrayList.add(plan);

            }
        } else {
            char[] chars = query.toCharArray();
            String string = "%";
            for (int i = 0; i < chars.length; i++) {
                string += chars[i] + "%";
            }

            Cursor cursor2 = sqLiteDatabase.rawQuery("select * from checkwork where plan_title like ? ", new String[]{string});
            while (cursor2.moveToNext()) {
                BaseBean plan = new BaseBean();
                plan.set("check_id", cursor2.getInt(cursor2.getColumnIndex("check_id")));
                plan.set("check_creater", User.getBasicInfo(getContext(), cursor2.getInt(cursor2.getColumnIndex("check_creater"))).get("user_name"));
                plan.set("check_checker", User.getBasicInfo(getContext(), cursor2.getInt(cursor2.getColumnIndex("check_checker"))).get("user_name"));
                plan.set("check_createtime", cursor2.getString(cursor2.getColumnIndex("check_createtime")));
                plan.set("check_checktime", cursor2.getString(cursor2.getColumnIndex("check_checktime")));
                plan.set("plan_title", cursor2.getString(cursor2.getColumnIndex("plan_title")));
                plan.set("plan_info", cursor2.getString(cursor2.getColumnIndex("plan_info")));
                plan.set("check_state", cursor2.getInt(cursor2.getColumnIndex("check_state")));
                plan.set("check_comment", cursor2.getString(cursor2.getColumnIndex("check_comment")));
                arrayList.add(plan);
            }

            cursor2.close();

            Cursor cursor3 = sqLiteDatabase.rawQuery("select * from checkwork where summary_title like ? ", new String[]{string});
            while (cursor3.moveToNext()) {
                BaseBean plan = new BaseBean();
                plan.set("check_id", cursor3.getInt(cursor3.getColumnIndex("check_id")));
                plan.set("check_creater", User.getBasicInfo(getContext(), cursor3.getInt(cursor3.getColumnIndex("check_creater"))).get("user_name"));
                plan.set("check_checker", User.getBasicInfo(getContext(), cursor3.getInt(cursor3.getColumnIndex("check_checker"))).get("user_name"));
                plan.set("check_createtime", cursor3.getString(cursor3.getColumnIndex("check_createtime")));
                plan.set("check_checktime", cursor3.getString(cursor3.getColumnIndex("check_checktime")));
                plan.set("summary_title", cursor3.getString(cursor3.getColumnIndex("summary_title")));
                plan.set("summary_info", cursor3.getString(cursor3.getColumnIndex("summary_info")));
                plan.set("check_state", cursor3.getInt(cursor3.getColumnIndex("check_state")));
                plan.set("check_comment", cursor3.getString(cursor3.getColumnIndex("check_comment")));
                arrayList.add(plan);
            }
            cursor3.close();


            Cursor cursor4 = sqLiteDatabase.rawQuery("select * from checkwork where over_content like ? ", new String[]{string});
            while (cursor4.moveToNext()) {
                BaseBean plan = new BaseBean();
                plan.set("check_id", cursor4.getInt(cursor3.getColumnIndex("check_id")));
                plan.set("check_creater", User.getBasicInfo(getContext(), cursor4.getInt(cursor4.getColumnIndex("check_creater"))).get("user_name"));
                plan.set("check_checker", User.getBasicInfo(getContext(), cursor4.getInt(cursor4.getColumnIndex("check_checker"))).get("user_name"));
                plan.set("check_createtime", cursor4.getString(cursor4.getColumnIndex("check_createtime")));
                plan.set("check_checktime", cursor4.getString(cursor4.getColumnIndex("check_checktime")));
                plan.set("over_starttime", cursor4.getString(cursor4.getColumnIndex("over_starttime")));
                plan.set("over_endtime", cursor4.getString(cursor4.getColumnIndex("over_endtime")));
                plan.set("over_content", cursor4.getString(cursor4.getColumnIndex("over_content")));
                plan.set("check_state", cursor4.getInt(cursor4.getColumnIndex("check_state")));
                arrayList.add(plan);
            }
            cursor4.close();


            Cursor cursor5 = sqLiteDatabase.rawQuery("select * from checkwork where leave_reason like ? ", new String[]{string});
            while (cursor5.moveToNext()) {
                BaseBean plan = new BaseBean();
                plan.set("check_id", cursor5.getInt(cursor5.getColumnIndex("check_id")));
                plan.set("check_creater", User.getBasicInfo(getContext(), cursor5.getInt(cursor5.getColumnIndex("check_creater"))).get("user_name"));
                plan.set("check_checker", User.getBasicInfo(getContext(), cursor5.getInt(cursor5.getColumnIndex("check_checker"))).get("user_name"));
                plan.set("check_createtime", cursor5.getString(cursor5.getColumnIndex("check_createtime")));
                plan.set("check_checktime", cursor5.getString(cursor5.getColumnIndex("check_checktime")));
                plan.set("leave_starttime", cursor5.getString(cursor5.getColumnIndex("leave_starttime")));
                plan.set("leave_endtime", cursor5.getString(cursor5.getColumnIndex("leave_endtime")));
                plan.set("leave_reason", cursor5.getString(cursor5.getColumnIndex("leave_reason")));
                plan.set("check_state", cursor5.getInt(cursor5.getColumnIndex("check_state")));
                arrayList.add(plan);
            }
            cursor5.close();


            Cursor cursor6 = sqLiteDatabase.rawQuery("select * from checkwork where claim_reason like ? ", new String[]{string});
            while (cursor6.moveToNext()) {
                BaseBean plan = new BaseBean();
                plan.set("check_id", cursor6.getInt(cursor6.getColumnIndex("check_id")));
                plan.set("check_creater", User.getBasicInfo(getContext(), cursor6.getInt(cursor6.getColumnIndex("check_creater"))).get("user_name"));
                plan.set("check_checker", User.getBasicInfo(getContext(), cursor6.getInt(cursor6.getColumnIndex("check_checker"))).get("user_name"));
                plan.set("check_createtime", cursor6.getString(cursor6.getColumnIndex("check_createtime")));
                plan.set("check_checktime", cursor6.getString(cursor6.getColumnIndex("check_checktime")));
                plan.set("claim_time", cursor6.getString(cursor6.getColumnIndex("claim_time")));
                plan.set("claim_money", cursor6.getString(cursor6.getColumnIndex("claim_money")));
                plan.set("claim_reason", cursor6.getString(cursor6.getColumnIndex("claim_reason")));
                plan.set("check_state", cursor6.getInt(cursor6.getColumnIndex("check_state")));
                arrayList.add(plan);
            }
            cursor6.close();


            Cursor cursor7 = sqLiteDatabase.rawQuery("select * from task where task_name like ? ", new String[]{string});
            while (cursor7.moveToNext()) {
                BaseBean task = new BaseBean();

                task.set("task_id", cursor7.getInt(cursor7.getColumnIndex("task_id")));
                task.set("task_name", cursor7.getString(cursor7.getColumnIndex("task_name")));
                task.set("task_creater", User.getBasicInfo(getContext(), cursor7.getInt(cursor7.getColumnIndex("task_creater"))).get("user_name"));
                task.set("task_createtime", cursor7.getString(cursor7.getColumnIndex("task_createtime")));
                task.set("task_excuter", User.getBasicInfo(getContext(), cursor7.getInt(cursor7.getColumnIndex("task_excuter"))).get("user_name"));
                task.set("task_endtime", cursor7.getString(cursor7.getColumnIndex("task_endtime")));

                int state = cursor7.getInt(cursor7.getColumnIndex("task_state"));
                switch (state) {
                    case 0:
                        task.set("task_state", "待处理");
                        break;
                    case 1:
                        task.set("task_state", "进行中");
                        break;
                    case 2:
                        task.set("task_state", "测试中");
                        break;
                    case 3:
                        task.set("task_state", "已完成");
                        break;


                }
                arrayList.add(task);

            }
            cursor7.close();

            Cursor cursor8 = sqLiteDatabase.rawQuery("select * from project where project_name like ?", new String[]{string});
            while (cursor8.moveToNext()) {
                BaseBean project = new BaseBean();
                project.set("project_id", cursor8.getInt(cursor8.getColumnIndex("project_id")));
                project.set("project_name", cursor8.getString(cursor8.getColumnIndex("project_name")));
                arrayList.add(project);
            }
            cursor8.close();


            //刷新数据
            adapter.notifyDataSetChanged();
            if (arrayList.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                tipsLayout.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                tipsLayout.setVisibility(View.VISIBLE);
                tipsLayout.showErrorTips("很抱歉，没有匹配的搜索项", R.mipmap.tips_error_no_data);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_layout_content_ll:
                search(content);
                adapter.notifyDataSetChanged();
                if (arrayList.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tipsLayout.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tipsLayout.setVisibility(View.VISIBLE);
                    tipsLayout.showErrorTips("很抱歉，没有匹配的搜索项", R.mipmap.tips_error_no_data);
                }
                break;

        }
    }


}
