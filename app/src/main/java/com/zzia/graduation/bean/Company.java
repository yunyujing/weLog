package com.zzia.graduation.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yunyujing on 16/5/18.
 */
public class Company {

    /**
     * 根据公司id查找本公司的所有部门
     *
     * @param context
     * @return
     */
    public static HashMap<Integer, String> getDepts(Context context) {
        HashMap<Integer, String> depts = new HashMap<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from department where company_id=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, User.companyId, 0))});
        while (cursor.moveToNext()) {
            int deptId = cursor.getInt(cursor.getColumnIndex("dept_id"));
            String deptName = cursor.getString(cursor.getColumnIndex("dept_name"));
            depts.put(deptId, deptName);
        }
        cursor.close();
        return depts;
    }

    /**
     * 根据公司ID查找全部的员工
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getUsers(Context context) {
        ArrayList<BaseBean> userList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from user,department where user.dept_id=department.dept_id and department.company_id=? ;",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context,User.companyId,0))});
        while (cursor.moveToNext()){
            BaseBean user=new BaseBean();
            user.set("user_id",cursor.getInt(cursor.getColumnIndex("user_id")));
            user.set("user_name",cursor.getString(cursor.getColumnIndex("user_name")));
            userList.add(user);
        }
        cursor.close();
        return userList;
    }

    /**
     * 根据公司ID查找全部项目
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getProjects(Context context) {
        ArrayList<BaseBean> userList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from project where company_id=?;",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context,User.companyId,0))});
        while (cursor.moveToNext()){
            BaseBean project=new BaseBean();
            project.set("project_id",cursor.getInt(cursor.getColumnIndex("project_id")));
            project.set("project_name",cursor.getString(cursor.getColumnIndex("project_name")));
            userList.add(project);
        }
        cursor.close();

        return userList;
    }

}
