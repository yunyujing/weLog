package com.zzia.graduation.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.SharedPreferenceUtils;

import java.util.ArrayList;

/**
 * Author: yunyujing
 * Date: 2015/12/9
 */
public class Task {
    /**
     * 获取由我创建的任务列表
     *
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getCreateTasks(Context context) {
        ArrayList<BaseBean> createTaskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from task,project where task.project_id=project.project_id and task_creater=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        while (cursor.moveToNext()) {
            BaseBean task = new BaseBean();
            task.set("task_id", cursor.getInt(cursor.getColumnIndex("task_id")));
            task.set("task_name", cursor.getString(cursor.getColumnIndex("task_name")));
            task.set("task_creater", String.valueOf(SharedPreferenceUtils.get(context, User.name, "")));
            task.set("task_createtime", cursor.getString(cursor.getColumnIndex("task_createtime")));
            task.set("task_excuter", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("task_excuter"))).get("user_name"));
            task.set("task_endtime", cursor.getString(cursor.getColumnIndex("task_endtime")));
            task.set("task_state", cursor.getString(cursor.getColumnIndex("task_state")));
            task.set("project_name",cursor.getString(cursor.getColumnIndex("project_name")));
            createTaskList.add(task);

        }
        cursor.close();
        return createTaskList;
    }

    /**
     * 获取由我执行的任务列表
     *
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getExcuteTasks(Context context) {
        ArrayList<BaseBean> createTaskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from task,project where task.project_id=project.project_id and task_excuter=?",
                new String[]{String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        while (cursor.moveToNext()) {
            BaseBean task = new BaseBean();
            task.set("task_id", cursor.getInt(cursor.getColumnIndex("task_id")));
            task.set("task_name", cursor.getString(cursor.getColumnIndex("task_name")));
            task.set("task_creater", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("task_creater"))).get("user_name"));
            task.set("task_createtime", cursor.getString(cursor.getColumnIndex("task_createtime")));
            task.set("task_excuter", String.valueOf(SharedPreferenceUtils.get(context, User.name, "")));
            task.set("task_endtime", cursor.getString(cursor.getColumnIndex("task_endtime")));
            task.set("project_name",cursor.getString(cursor.getColumnIndex("project_name")));
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
            createTaskList.add(task);

        }
        cursor.close();

        return createTaskList;
    }

    /**
     * 根据taskID查找该任务的所有评论
     * @param context
     * @param taskId
     * @return
     */
    public static ArrayList<BaseBean> getComments(Context context,int taskId){
        ArrayList<BaseBean> commentsList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from comment where task_id=?",
                new String[]{String.valueOf(taskId)});
        while (cursor.moveToNext()){
            BaseBean comment=new BaseBean();
            comment.set("comment_id",cursor.getInt(cursor.getColumnIndex("comment_id")));
            comment.set("comment_creater",User.getBasicInfo(context,cursor.getInt(cursor.getColumnIndex("comment_creater"))).get("user_name"));
            comment.set("comment_createtime",cursor.getString(cursor.getColumnIndex("comment_createtime")));
            comment.set("comment_content",cursor.getString(cursor.getColumnIndex("comment_content")));
            comment.set("comment_image", Comment.getCommentImages(context,taskId));
            commentsList.add(comment);

        }
        cursor.close();
        return commentsList;
    }


}

