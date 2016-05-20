package com.zzia.graduation.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zzia.graduation.db.MySQLiteOpenHelper;
import com.zzia.graduation.utils.Common;

import java.util.ArrayList;

/**
 * Created by yunyujing on 16/5/20.
 */
public class Comment {

    /**
     * 根据commentID查找该评论的所有图片
     *
     * @param context
     * @param commentId
     * @return
     */
    public static ArrayList<String> getCommentImages(Context context, int commentId) {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from image where comment_id=?",
                new String[]{String.valueOf(commentId)});
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

}
