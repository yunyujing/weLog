package com.zzia.graduation.db;

/**
 * 数据表类
 * Created by yunyujing on 16/5/10.
 */
public class CreateTable {
    public static final String TEST = "CREATE TABLE test (" +
            "[user_id] INTEGER PRIMARY KEY," +
            "[user_name] VARCHAR(30)" +
            ");";

    public static final String COMPANY =
            "CREATE TABLE company(" +
                    "[company_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "[company_name] varchar(50) NOT NULL ," +
                    "[company_email] VARCHAR(255) NOT NULL ," +
                    "[company_password] VARCHAR(12) NOT NULL " +
                    ");";

    public static final String DEPARTMENT =
            "CREATE TABLE department (" +
                    "[dept_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "[dept_name] varchar(30) NOT NULL ," +
                    "[dept_num] tinyint(4) NOT NULL ," +
                    "[dept_distance] varchar(100) DEFAULT NULL," +
                    "[company_id] INTEGER NOT NULL," +
                    "FOREIGN KEY ([company_id]) REFERENCES company ([company_id]) ON DELETE CASCADE ON UPDATE CASCADE" +
                    " );";

    public static final String USER =
            "CREATE TABLE user (" +
                    "[user_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "[dept_id] INTEGER NOT NULL ," +
                    "[user_email] VARCHAR(255) NOT NULL ," +
                    "[user_password] VARCHAR(12) NOT NULL ," +
                    "[user_name] VARCHAR(30) NOT NULL," +
                    "[user_age] TINYINT(3) NOT NULL DEFAULT '0' ," +
                    "[user_sex] TINYINT(1) NOT NULL DEFAULT '0' ," +
                    "[user_address] VARCHAR(100) NOT NULL ," +
                    "[user_salary] FLOAT NOT NULL ," +
                    "[user_workage] VARCHAR(30) DEFAULT NULL ," +
                    "[user_companyage] VARCHAR(30) DEFAULT NULL ," +
                    "[user_ismanager] TINYINT(2) NOT NULL DEFAULT '0'," +
                    "[company_id] INTEGER NOT NULL," +
                    "FOREIGN KEY ([company_id]) REFERENCES company ([company_id]) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY ([dept_id]) REFERENCES department ([dept_id]) ON UPDATE CASCADE" +
                    ");";

    public static final String PROJECT =
            "CREATE TABLE project (" +
                    "  [project_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  [project_name] varchar(50) NOT NULL," +
                    "  [project_info] varchar(100) NOT NULL," +
                    "  [project_ispublic] tinyint(4) NOT NULL," +
                    "  [project_creater] INTEGER NOT NULL," +
                    "  [project_createtime] datetime NOT NULL," +
                    "  [company_id] INTEGER NOT NULL," +
                    "  FOREIGN KEY ([company_id]) REFERENCES company ([company_id]) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "  FOREIGN KEY ([project_creater]) REFERENCES user ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION" +
                    ");";

    public static final String TASK =
            "CREATE TABLE task (" +
                    "  [task_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  [task_name] varchar(100) NOT NULL," +
                    "  [task_info] varchar(500) NOT NULL," +
                    "  [task_creater] INTEGER NOT NULL," +
                    "  [task_createtime] datetime NOT NULL," +
                    "  [project_id] INTEGER NOT NULL," +
                    "  [task_excuter] INTEGER NOT NULL," +
                    "  [task_statrttime] datetime NOT NULL," +
                    "  [task_endtime] datetime NOT NULL," +
                    "  [task_state] tinyint(4) NOT NULL," +
                    "  FOREIGN KEY ([task_creater]) REFERENCES user ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY ([task_excuter]) REFERENCES user ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY ([project_id]) REFERENCES project ([project_id]) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ") ;";

    public static final String CHECKWORK =
            "CREATE TABLE checkwork (" +
                    "  [check_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  [check_creater] INTEGER NOT NULL," +
                    "  [check_createtime] datetime NOT NULL," +
                    "  [check_checker] INTEGER NOT NULL," +
                    "  [check_checktime] datetime NOT NULL," +
                    "  [plan_title] varchar(100) DEFAULT NULL," +
                    "  [plan_info] text," +
                    "  [summary_title] varchar(100) DEFAULT NULL," +
                    "  [summary_info] text," +
                    "  [leave_starttime] datetime DEFAULT NULL," +
                    "  [leave_endtime] datetime DEFAULT NULL," +
                    "  [leave_reason] varchar(200) DEFAULT NULL," +
                    "  [over_starttime] datetime DEFAULT NULL," +
                    "  [over_endtime] datetime DEFAULT NULL," +
                    "  [over_content] varchar(200) DEFAULT NULL," +
                    "  [claim_time] datetime DEFAULT NULL," +
                    "  [claim_reason] varchar(200) DEFAULT NULL," +
                    "  [claim_money] smallint(6) DEFAULT NULL," +
                    "  [check_state] tinyint(4) NOT NULL," +
                    "  [company_id] INTEGER NOT NULL," +
                    "  FOREIGN KEY ([company_id]) REFERENCES company ([company_id]) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "  FOREIGN KEY ([check_creater]) REFERENCES user ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY ([check_checker]) REFERENCES user ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION" +
                    ");";

    public static final String COMMENT =
            "CREATE TABLE comment (" +
                    "  [comment_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  [comment_creater] int(11) NOT NULL," +
                    "  [comment_createtime] datetime NOT NULL," +
                    "  [comment_content] varchar(500) NOT NULL," +
                    "  [task_id] INTEGER NOT NULL," +
                    "  [check_id] INTEGER NOT NULL," +
                    "  FOREIGN KEY ([comment_creater]) REFERENCES user ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY ([task_id]) REFERENCES task ([task_id]) ON DELETE  CASCADE ON UPDATE  CASCADE," +
                    "  FOREIGN KEY ([check_id]) REFERENCES checkwork ([check_id]) ON DELETE  CASCADE ON UPDATE  CASCADE" +
                    ") ;";

    public static final String IMAGE =
            "CREATE TABLE image (" +
                    "  [img_id] INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  [img_url] varchar(500) NOT NULL," +
                    "  [user_id] INTEGER DEFAULT NULL," +
                    "  [project_id] INTEGER DEFAULT NULL," +
                    "  [check_id] INTEGER DEFAULT NULL," +
                    "  [comment_id] INTEGER DEFAULT NULL," +
                    "  FOREIGN KEY ([check_id]) REFERENCES checkwork ([check_id]) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY ([comment_id]) REFERENCES comment ([comment_id]) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY ([project_id]) REFERENCES project ([project_id]) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                    "  FOREIGN KEY ([user_id]) REFERENCES user ([user_id]) ON DELETE NO ACTION ON UPDATE NO ACTION" +
                    ") ;";



}

