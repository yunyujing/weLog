package com.zzia.graduation.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.utils.SharedPreferenceUtils;

import java.util.HashMap;

/**
 * Created by yunyujing on 16/5/15.
 */
public class Dept {


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
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, Common.UserInfo.id, 0))});
        while (cursor.moveToNext()) {
            int deptId = cursor.getInt(cursor.getColumnIndex("dept_id"));
            String deptName = cursor.getString(cursor.getColumnIndex("dept_name"));
            depts.put(deptId, deptName);
        }
        cursor.close();
        return depts;
    }

    /**
     * 根据用户id查找部门名字
     *
     * @param context
     * @return
     */
    public static String getDeptName(Context context) {

        String deptName = null;
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from department,user where department.dept_id==user.dept_id and user.user_id=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, Common.UserInfo.id, 0))});
        if (cursor.moveToFirst()) {
            deptName = cursor.getString(cursor.getColumnIndex("dept_name"));
        }
        cursor.close();
        return deptName;
    }
}
