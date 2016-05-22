package com.zzia.graduation.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.Common;
import com.zzia.graduation.utils.SharedPreferenceUtils;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/20.
 */
public class CheckWork {

    /**
     * 根据checkID查找审核表的所有图片
     *
     * @param context
     * @param checkID
     * @return
     */
    public static ArrayList<String> getCheckImages(Context context, int checkID) {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from image where check_id=?",
                new String[]{String.valueOf(checkID)});
        while (cursor.moveToNext()) {
            String imageurl = cursor.getString(cursor.getColumnIndex("img_url"));
            arrayList.add(imageurl);
        }
        cursor.close();
        if (arrayList != null && arrayList.size() > 0) {

            for (int i = 0; i < arrayList.size(); i++) {
                Log.e(Common.LOG_APP, arrayList.get(i));
            }
        }
        return arrayList;
    }

    /**
     * 获取当前登录用户创建的计划列表
     *
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getCreatePlanList(Context context) {

        ArrayList<BaseBean> planList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from checkwork where plan_title!=? and check_creater=?",
                new String[]{"NULL",String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        while (cursor.moveToNext()) {
            BaseBean plan = new BaseBean();
            plan.set("check_id", cursor.getInt(cursor.getColumnIndex("check_id")));
            plan.set("check_creater", String.valueOf(SharedPreferenceUtils.get(context, User.name, "")));
            plan.set("check_checker", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("check_checker"))).get("user_name"));
            plan.set("check_createtime", cursor.getString(cursor.getColumnIndex("check_createtime")));
            plan.set("check_checktime", cursor.getString(cursor.getColumnIndex("check_checktime")));
            plan.set("plan_title", cursor.getString(cursor.getColumnIndex("plan_title")));
            plan.set("plan_info", cursor.getString(cursor.getColumnIndex("plan_info")));
            plan.set("check_state", cursor.getInt(cursor.getColumnIndex("check_state")));
            plan.set("check_comment",cursor.getString(cursor.getColumnIndex("check_comment")));

            planList.add(plan);


        }
        cursor.close();

        return planList;

    }

    /**
     * 获取当前登录用户审核的计划列表
     *
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getCheckPlanList(Context context) {

        ArrayList<BaseBean> planList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from checkwork where plan_title!=? and check_checker=?", new String[]{
                "NULL",String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        while (cursor.moveToNext()) {
            BaseBean plan = new BaseBean();
            plan.set("check_id", cursor.getInt(cursor.getColumnIndex("check_id")));
            plan.set("check_creater", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("check_creater"))).get("user_name"));
            plan.set("check_checker", String.valueOf(SharedPreferenceUtils.get(context, User.name, "")));
            plan.set("check_createtime", cursor.getString(cursor.getColumnIndex("check_createtime")));
            plan.set("check_checktime", cursor.getString(cursor.getColumnIndex("check_checktime")));
            plan.set("plan_title", cursor.getString(cursor.getColumnIndex("plan_title")));
            plan.set("plan_info", cursor.getString(cursor.getColumnIndex("plan_info")));
            plan.set("check_state", cursor.getInt(cursor.getColumnIndex("check_state")));
            plan.set("check_comment",cursor.getString(cursor.getColumnIndex("check_comment")));


            planList.add(plan);

        }
        cursor.close();

        return planList;

    }

    /**
     * 获取当前企业的所有计划列表
     *
     * @param context
     * @return
     */
    public static ArrayList<BaseBean> getCompanyPlanList(Context context) {
        ArrayList<BaseBean> planList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from checkwork where plan_title!=? and company_id=?",
                new String[]{"NULL",String.valueOf(SharedPreferenceUtils.get(context, User.companyId, 0))});
        while (cursor.moveToNext()) {
            BaseBean plan = new BaseBean();
            plan.set("check_id", cursor.getInt(cursor.getColumnIndex("task_id")));
            plan.set("check_creater", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("check_creater"))).get("user_name"));
            plan.set("check_checker", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("check_checker"))).get("user_name"));
            plan.set("check_createtime", cursor.getString(cursor.getColumnIndex("check_createtime")));
            plan.set("check_checktime", cursor.getString(cursor.getColumnIndex("check_checktime")));
            plan.set("plan_title", cursor.getString(cursor.getColumnIndex("plan_title")));
            plan.set("plan_info", cursor.getString(cursor.getColumnIndex("plan_info")));
            plan.set("check_state", cursor.getInt(cursor.getColumnIndex("check_state")));
            plan.set("check_comment",cursor.getString(cursor.getColumnIndex("check_comment")));


            planList.add(plan);

        }
        cursor.close();
        return planList;

    }

    public static ArrayList<BaseBean> getCreateSummaryList(Context context) {
        ArrayList<BaseBean> planList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from checkwork where summary_title!=? and  check_creater=?",
                new String[]{"NULL",String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        while (cursor.moveToNext()) {
            BaseBean plan = new BaseBean();
            plan.set("check_id", cursor.getInt(cursor.getColumnIndex("check_id")));
            plan.set("check_creater", String.valueOf(SharedPreferenceUtils.get(context, User.name, "")));
            plan.set("check_checker", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("check_checker"))).get("user_name"));
            plan.set("check_createtime", cursor.getString(cursor.getColumnIndex("check_createtime")));
            plan.set("check_checktime", cursor.getString(cursor.getColumnIndex("check_checktime")));
            plan.set("summary_title", cursor.getString(cursor.getColumnIndex("summary_title")));
            plan.set("summary_info", cursor.getString(cursor.getColumnIndex("summary_info")));
            plan.set("check_state", cursor.getInt(cursor.getColumnIndex("check_state")));
            plan.set("check_comment",cursor.getString(cursor.getColumnIndex("check_comment")));

            planList.add(plan);


        }
        cursor.close();

        return planList;

    }

    public static ArrayList<BaseBean> getCheckSummaryList(Context context) {
        ArrayList<BaseBean> planList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from checkwork where summary_title!=? and  check_checker=?",
                new String[]{"NULL",String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        while (cursor.moveToNext()) {
            BaseBean plan = new BaseBean();
            plan.set("check_id", cursor.getInt(cursor.getColumnIndex("check_id")));
            plan.set("check_creater", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("check_creater"))).get("user_name"));
            plan.set("check_checker", String.valueOf(SharedPreferenceUtils.get(context, User.name, "")));
            plan.set("check_createtime", cursor.getString(cursor.getColumnIndex("check_createtime")));
            plan.set("check_checktime", cursor.getString(cursor.getColumnIndex("check_checktime")));
            plan.set("summary_title", cursor.getString(cursor.getColumnIndex("summary_title")));
            plan.set("summary_info", cursor.getString(cursor.getColumnIndex("summary_info")));
            plan.set("check_state", cursor.getInt(cursor.getColumnIndex("check_state")));
            plan.set("check_comment",cursor.getString(cursor.getColumnIndex("check_comment")));

            planList.add(plan);

        }
        cursor.close();

        return planList;

    }

    public static ArrayList<BaseBean> getCreateOverTimeList(FragmentActivity context) {
        ArrayList<BaseBean> planList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from checkwork where over_content!=? and  check_checker=?",
                new String[]{"NULL",String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        while (cursor.moveToNext()) {
            BaseBean plan = new BaseBean();
            plan.set("check_id", cursor.getInt(cursor.getColumnIndex("check_id")));
            plan.set("check_creater", String.valueOf(SharedPreferenceUtils.get(context, User.name, "")));
            plan.set("check_checker", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("check_checker"))).get("user_name"));
            plan.set("check_createtime", cursor.getString(cursor.getColumnIndex("check_createtime")));
            plan.set("check_checktime", cursor.getString(cursor.getColumnIndex("check_checktime")));
            plan.set("over_starttime", cursor.getString(cursor.getColumnIndex("over_starttime")));
            plan.set("over_endtime",cursor.getString(cursor.getColumnIndex("over_endtime")));
            plan.set("over_content", cursor.getString(cursor.getColumnIndex("over_content")));
            plan.set("check_state", cursor.getInt(cursor.getColumnIndex("check_state")));

            planList.add(plan);

        }
        cursor.close();

        return planList;
    }

    public static ArrayList<BaseBean> getCheckOverTimeList(Context context) {
        ArrayList<BaseBean> planList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from checkwork where over_content!=? and  check_checker=?",
                new String[]{"NULL",String.valueOf(SharedPreferenceUtils.get(context, User.id, 0))});
        while (cursor.moveToNext()) {
            BaseBean plan = new BaseBean();
            plan.set("check_id", cursor.getInt(cursor.getColumnIndex("check_id")));
            plan.set("check_creater", User.getBasicInfo(context, cursor.getInt(cursor.getColumnIndex("check_creater"))).get("user_name"));
            plan.set("check_checker", String.valueOf(SharedPreferenceUtils.get(context, User.name, "")));
            plan.set("check_createtime", cursor.getString(cursor.getColumnIndex("check_createtime")));
            plan.set("check_checktime", cursor.getString(cursor.getColumnIndex("check_checktime")));
            plan.set("over_starttime", cursor.getString(cursor.getColumnIndex("over_starttime")));
            plan.set("over_endtime",cursor.getString(cursor.getColumnIndex("over_endtime")));
            plan.set("over_content", cursor.getString(cursor.getColumnIndex("over_content")));
            plan.set("check_state", cursor.getInt(cursor.getColumnIndex("check_state")));

            planList.add(plan);

        }
        cursor.close();

        return planList;

    }
}
