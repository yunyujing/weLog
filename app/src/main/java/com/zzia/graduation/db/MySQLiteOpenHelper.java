package com.zzia.graduation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 初始化数据库和数据
 * Created by yunyujing on 16/5/10.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "welog.db"; // database name
    public static final int DB_VERSION = 4;//database version
    //    1:test    2:建表    3:修改表结构,添加company表  4:修改company表结构

    public static final String TABLE_DEPARTMENT = "department";
    public static final String TABLE_USER = "user";
    public static final String TABLE_PROJECT = "project";
    public static final String TABLE_TASK = "task";
    public static final String TABLE_COMMENT = "comment";
    public static final String TABLE_CHECKWork = "checkwork";
    public static final String TABLE_IMAGE = "image";

    private SQLiteDatabase database;

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //数据库第一次创建时被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CreateTable.TEST);

        db.execSQL(CreateTable.COMPANY);
        db.execSQL(CreateTable.DEPARTMENT);
        db.execSQL(CreateTable.USER);
        db.execSQL(CreateTable.PROJECT);
        db.execSQL(CreateTable.TASK);
        db.execSQL(CreateTable.CHECKWORK);
        db.execSQL(CreateTable.COMMENT);
        db.execSQL(CreateTable.IMAGE);


    }

    //数据库版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            case 2:
                db.execSQL(CreateTable.DEPARTMENT);
                db.execSQL(CreateTable.USER);
                db.execSQL(CreateTable.PROJECT);
                db.execSQL(CreateTable.TASK);
                db.execSQL(CreateTable.CHECKWORK);
                db.execSQL(CreateTable.COMMENT);
                db.execSQL(CreateTable.IMAGE);
                break;
            case 3:
            case 4:
                db.execSQL("DROP TABLE IF EXISTS company;");
                db.execSQL("DROP TABLE IF EXISTS image;");
                db.execSQL("DROP TABLE IF EXISTS comment;");
                db.execSQL("DROP TABLE IF EXISTS checkwork;");
                db.execSQL("DROP TABLE IF EXISTS task;");
                db.execSQL("DROP TABLE IF EXISTS project;");
                db.execSQL("DROP TABLE IF EXISTS user;");
                db.execSQL("DROP TABLE IF EXISTS department;");

                db.execSQL(CreateTable.COMPANY);
                db.execSQL(CreateTable.DEPARTMENT);
                db.execSQL(CreateTable.USER);
                db.execSQL(CreateTable.PROJECT);
                db.execSQL(CreateTable.TASK);
                db.execSQL(CreateTable.CHECKWORK);
                db.execSQL(CreateTable.COMMENT);
                db.execSQL(CreateTable.IMAGE);
                break;

        }
    }



}
