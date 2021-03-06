package com.zzia.graduation.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.db.MySQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/9
 */
public class Project {
    /**
     * 根据项目id查找：任务列表
     *
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getTasks(Context context,int id) {

        ArrayList<BaseBean> taskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from task " +
                "where project_id=? ;",new String[]{String.valueOf(id)});
        while (cursor.moveToNext()){
            BaseBean task=new BaseBean();
            task.set("task_id",cursor.getInt(cursor.getColumnIndex("task_id")));
            task.set("task_name",cursor.getString(cursor.getColumnIndex("task_name")));
            task.set("task_creater",User.getBasicInfo(context,cursor.getInt(cursor.getColumnIndex("task_creater"))).get("user_name"));
            task.set("task_createtime",cursor.getString(cursor.getColumnIndex("task_createtime")));
            task.set("task_excuter",User.getBasicInfo(context,cursor.getInt(cursor.getColumnIndex("task_excuter"))).get("user_name"));
            task.set("task_endtime",cursor.getString(cursor.getColumnIndex("task_endtime")));

            int state = cursor.getInt(cursor.getColumnIndex("task_state"));
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
            taskList.add(task);
        }
        cursor.close();

        return taskList;
    }
}
