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
 * Author: yunyujing
 * Date: 2015/12/9
 */
public class User {
    public static String isCompany = "isCompany";
    public static String companyId = "companyId";
    public static String isManager = "isManager";
    public static String id = "id";
    public static String name = "name";
    public static String age = "age";
    public static String sex = "sex";
    public static String icon = "icon";
    public static String email = "email";
    public static String password = "password";
    public static String tel = "tel";
    public static String address = "address";
    public static String deptName = "deptName";

    /**
     * 根据用户id查找：公司id;公司名字;部门id;部门名字;
     *
     * @param context
     * @return HashMap
     */
    public static HashMap<String, String> getBasicInfo(Context context) {

        HashMap<String, String> userInfo = new HashMap<>();
        String companyId = null;
        String companyName = null;
        String deptId = null;
        String deptName = null;

        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from company,department,user " +
                        "where company.company_id=department.company_id and department.dept_id==user.dept_id and user.user_id=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        if (cursor.moveToFirst()) {
            companyId = String.valueOf(cursor.getInt(cursor.getColumnIndex("company_id")));
            companyName = String.valueOf(cursor.getString(cursor.getColumnIndex("company_name")));
            deptId = String.valueOf(cursor.getInt(cursor.getColumnIndex("dept_id")));
            deptName = cursor.getString(cursor.getColumnIndex("dept_name"));
            userInfo.put("company_id", companyId);
            userInfo.put("company_name", companyName);
            userInfo.put("dept_id", deptId);
            userInfo.put("dept_name", deptName);
        }
        cursor.close();
        return userInfo;
    }

    /**
     * 根据用户id查找：参与的所有项目
     *
     * @param context
     * @return HashMap
     */
    public static ArrayList<BaseBean> getProjects(Context context) {

        ArrayList<BaseBean> userInfo = new ArrayList<>();
        String companyId = null;
        String deptName = null;
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();

        //查找所有的公共项目
        Cursor cursor = sqLiteDatabase.rawQuery("select * from project " +
                        "where project_ispublic='1' and company_id=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, User.companyId, 0))});

        while (cursor.moveToNext()) {
            BaseBean project = new BaseBean();
            project.set("project_id", cursor.getInt(cursor.getColumnIndex("project_id")));
            project.set("project_name", cursor.getString(cursor.getColumnIndex("project_name")));
            project.set("project_info", cursor.getString(cursor.getColumnIndex("project_info")));
            userInfo.add(project);

        }
        cursor.close();

        //查找自己的创建的私有项目
        Cursor cursorPrivate = sqLiteDatabase.rawQuery("select * from project " +
                        "where project_ispublic='0' and project_creater=? and company_id=?;",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, User.id, 0)),String.valueOf(SharedPreferenceUtils.get(context, User.companyId, 0))});

        while (cursorPrivate.moveToNext()) {
            BaseBean project = new BaseBean();
            project.set("project_id", cursorPrivate.getInt(cursorPrivate.getColumnIndex("project_id")));
            project.set("project_name", cursorPrivate.getString(cursorPrivate.getColumnIndex("project_name")));
            project.set("project_info", cursorPrivate.getString(cursorPrivate.getColumnIndex("project_info")));
            userInfo.add(project);

        }
        cursorPrivate.close();

        return userInfo;
    }

    /**
     * 查询该公司的所有项目
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getCompanyProjects(Context context) {

        ArrayList<BaseBean> userInfo = new ArrayList<>();
        String companyId = null;
        String deptName = null;
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();

        //查找所有项目
        Cursor cursor = sqLiteDatabase.rawQuery("select * from project " +
                        "where company_id=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, User.companyId, 0))});

        while (cursor.moveToNext()) {
            BaseBean project = new BaseBean();
            project.set("project_id", cursor.getInt(cursor.getColumnIndex("project_id")));
            project.set("project_name", cursor.getString(cursor.getColumnIndex("project_name")));
            project.set("project_info", cursor.getString(cursor.getColumnIndex("project_info")));
            userInfo.add(project);

        }
        cursor.close();
        return userInfo;
    }
}
