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
        Cursor cursor=sqLiteDatabase.rawQuery("select * from project,task,user " +
                "where project.project_id=task.project_id and project.project_id=? ;",new String[]{String.valueOf(id)});
        while (cursor.moveToNext()){
            BaseBean task=new BaseBean();
            task.set("task_id",cursor.getInt(cursor.getColumnIndex("task_id")));
            task.set("task_name",cursor.getString(cursor.getColumnIndex("task_name")));
            task.set("task_creater",cursor.getInt(cursor.getColumnIndex("task_creater")));
            task.set("task_createtime",cursor.getString(cursor.getColumnIndex("task_createtime")));
            task.set("task_excuter",cursor.getInt(cursor.getColumnIndex("task_excuter")));
            task.set("task_statrttime",cursor.getString(cursor.getColumnIndex("task_statrttime")));
            task.set("task_endtime",cursor.getString(cursor.getColumnIndex("task_endtime")));
            task.set("task_state",cursor.getInt(cursor.getColumnIndex("task_state")));
            taskList.add(task);
        }
        cursor.close();

        return taskList;
    }
}
