//package com.zzia.graduation.common.util;
//
//import android.os.Environment;
//import android.util.Log;
//
//import com.zzia.graduation.common.log.ApsLogCatcher;
//
///**
// * 日志工具类,可通过设置LOG变量控制日志开关
// *
// * @author yuanhang <729741121@qq.com>
// */
//public class LogUtils {
//
//    public static boolean LOG = BuildConfig.DEBUG;// 是否打印日志,一键控制级别
//    public static boolean LOG_LOCAL = false;// 是否写入本地日志log_test.txt
//    public static String LOGTEST_LOCAL_FILEPATH = Environment.getExternalStorageDirectory().getPath() + "/appshare.common/log/log_test.txt";// 本地日志log_test.txt默认路径
//    public static String LOG_LOCAL_FILEPATH = Environment.getExternalStorageDirectory().getPath() + "/appshare.common/log/log.tmp";
//    public static String CHECK_APPDATA_DIR = "";// 要检测的文件目录，需在各产品配置路径，如果没有配置就不检测
//
//    public static int LOG_LEVEL = Log.DEBUG;// 控制打印级别,默认DEBUG以上级别打印
//
//    /**
//     * 区分测试
//     */
//    public static void iHAHA(String msg) {
//        if (LOG && LOG_LEVEL <= Log.INFO && msg != null) {
//            Log.i("haha", msg);
//            if (LOG_LOCAL) {
//                saveLogTest("info\n" + msg);
//            }
//        }
//    }
//
//    public static void d(String tag, String msg) {
//        if (LOG && LOG_LEVEL <= Log.DEBUG && tag != null && msg != null) {
//            Log.d(tag, msg);
//            if (LOG_LOCAL) {
//                saveLogTest("debug\n" + msg);
//            }
//        }
//    }
//
//    public static void d(String msg) {
//        if (LOG && LOG_LEVEL <= Log.DEBUG && msg != null) {
//            Log.d("", msg);
//            if (LOG_LOCAL) {
//                saveLogTest("debug\n" + msg);
//            }
//        }
//    }
//
//    public static void d(String tag, String msg, Throwable e) {
//        if (LOG && LOG_LEVEL <= Log.DEBUG && tag != null && msg != null) {
//            Log.d(tag, msg, e);
//            if (LOG_LOCAL) {
//                saveLogTest("debug\n" + msg);
//            }
//        }
//    }
//
//    public static void i(String tag, String msg) {
//        if (LOG && LOG_LEVEL <= Log.INFO && tag != null && msg != null) {
//            Log.i(tag, msg);
//            if (LOG_LOCAL) {
//                saveLogTest("info\n" + msg);
//            }
//        }
//    }
//
//    public static void i(String tag, String msg, Throwable e) {
//        if (LOG && LOG_LEVEL <= Log.INFO && tag != null && msg != null) {
//            Log.i(tag, msg, e);
//            if (LOG_LOCAL) {
//                saveLogTest("info\n" + msg);
//            }
//        }
//    }
//
//    public static void w(String tag, String msg) {
//        if (LOG && LOG_LEVEL <= Log.WARN && tag != null && msg != null) {
//            Log.w(tag, msg);
//            if (LOG_LOCAL) {
//                saveLogTest("warn\n" + msg);
//            }
//        }
//    }
//
//    public static void w(String tag, String msg, Throwable e) {
//        if (LOG && LOG_LEVEL <= Log.WARN && tag != null && msg != null) {
//            Log.w(tag, msg, e);
//            if (LOG_LOCAL) {
//                saveLogTest("warn\n" + msg);
//            }
//        }
//    }
//
//    public static void e(String tag, String msg) {
//        if (LOG && LOG_LEVEL <= Log.ERROR && tag != null && msg != null) {
//            Log.e(tag, msg);
//            if (LOG_LOCAL) {
//                saveLogTest("error\n" + msg);
//            }
//        }
//    }
//
//    public static void e(String tag, String msg, Throwable e) {
//        if (LOG && LOG_LEVEL <= Log.ERROR && tag != null && msg != null) {
//            Log.e(tag, msg, e);
//            if (LOG_LOCAL) {
//                saveLogTest("error\n" + msg);
//            }
//        }
//    }
//
//    public static void a(String tag, String msg) {
//        if (LOG && LOG_LEVEL <= Log.ERROR && tag != null && msg != null) {
//            Log.e(tag, msg);
//            if (LOG_LOCAL) {
//                saveLogTest("assert\n" + msg);
//            }
//        }
//    }
//
//    public static void a(String tag, String msg, Throwable e) {
//        if (LOG && LOG_LEVEL <= Log.ERROR && tag != null && msg != null) {
//            Log.e(tag, msg, e);
//            if (LOG_LOCAL) {
//                saveLogTest("assert\n" + msg);
//            }
//        }
//    }
//
//    /**
//     * 写入测试日志log_test_version.txt
//     *
//     * @param msg
//     */
//    public static void saveLogTest(String msg) {
//        ApsLogCatcher apsLogCatcher = new ApsLogCatcher(true, LOGTEST_LOCAL_FILEPATH, null);
//        apsLogCatcher.save(msg);
//    }
//
//    /**
//     * 写入log.tmp
//     *
//     * @param msg
//     */
//    public static void saveLog(String msg) {
//        ApsLogCatcher apsLogCatcher = new ApsLogCatcher(true, LOG_LOCAL_FILEPATH, null);
//        apsLogCatcher.save(msg);
//    }
//}
