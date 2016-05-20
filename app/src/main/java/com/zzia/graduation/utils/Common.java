package com.zzia.graduation.utils;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: yunyujing
 * Date: 2015/12/27
 */
public class Common {
    //调试日志
    public static final String LOG_APP="welog";
    public static final String DETAIL_TASK = "detail_task";
    public static final String DETAIL_PROJECT = "detail_project";
    public static final String ADD_PROJECT="add_project";
    public static final String ADD_TASK="add_task";
    public static final String ADD_OVERTIME="add_overtime";
    public static final String ADD_HOLIDAY="add_holiday";
    public static final String SHOW_IMAGE="show_image";
    public static final String UPLOAD_IMAGE="upload_image";

    // 缓存文件夹
    public static final String DEFAULT_SDCARD = Environment.getExternalStorageDirectory().getPath();
    public static final String CACHEDIR_RELATIVE_PATH = "/com.zzia.welog";
    public static final String CACHEDIR = DEFAULT_SDCARD + CACHEDIR_RELATIVE_PATH;
    public static final String CACHEDIR_IMG = DEFAULT_SDCARD + CACHEDIR_RELATIVE_PATH+"/imgs/";
    public static final String CACHEDIR_COMMON = Environment.getExternalStorageDirectory().getPath() + "/zzia/common/";// 产品公共目录

    public static final String FILE_SHARED="welog";

    /**
     * 获取当前系统时间
     * @return yyyy-MM-dd hh:mm:ss
     */
    public static String getNowTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createTime = dateFormat.format(new Date(System.currentTimeMillis()));
        return createTime;
    }
    /**
     * 获取当前系统时间
     * @return yyyy-MM-dd hh:mm
     */
    public static String getNowTimeNoSecond(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String createTime = dateFormat.format(new Date(System.currentTimeMillis()));
        return createTime;
    }

    /**
     * 获取当前系统日期
     * @return yyyy-MM-dd
     */
    public static String getNowDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = dateFormat.format(new Date(System.currentTimeMillis()));
        return createTime;
    }

}
