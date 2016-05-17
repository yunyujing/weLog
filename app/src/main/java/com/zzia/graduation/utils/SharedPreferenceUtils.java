package com.zzia.graduation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by yunyujing on 16/5/14.
 */
public class SharedPreferenceUtils {
    //    private static Context mContext;
//    public SharedPreferenceUtils(Context context) {
//        this.mContext = context;
//    }
//    //写入Sp的方法
//    public void setValue(Context context,String key, int value) {
//        SharedPreferences sp = context.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt(key, value);
//        editor.commit();
//    }
//
//    public void setValue(String key, float value) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putFloat(key, value);
//        editor.commit();
//    }
//
//    public void setValue(String key, long value) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putLong(key, value);
//        editor.commit();
//    }
//
//    public void setValue(String key, String value) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(key, value);
//        editor.commit();
//    }
//
//    public void setValue(String key, boolean value) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putBoolean(key, value);
//        editor.commit();
//    }
//
//
//    //读取SP文件的方法
//    public int getValue(String key, int defValue) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        return sp.getInt(key, defValue);
//    }
//
//    public float getValue(String key, float defValue) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        return sp.getFloat(key, defValue);
//    }
//
//    public long getValue(String key, long defValue) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        return sp.getLong(key, defValue);
//    }
//
//    public String getValue(String key, String defValue) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        return sp.getString(key, defValue);
//    }
//
//    public boolean getValue(String key, boolean defValue) {
//        SharedPreferences sp = mContext.getSharedPreferences("welog", Context.MODE_PRIVATE);
//        return sp.getBoolean(key, defValue);
//    }

    /**
     * 保存数据
     */
    public static void put(Context context, String key, Object obj) {
        SharedPreferences sp = context.getSharedPreferences(Common.FILE_SHARED, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, (String) obj);
        }
        editor.commit();
    }

    /**
     * 获取指定数据
     */
    public static Object get(Context context, String key, Object defaultObj) {
        SharedPreferences sp = context.getSharedPreferences(Common.FILE_SHARED, context.MODE_PRIVATE);
        if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        } else if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        }
        return null;
    }


    /**
     * 删除指定数据
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("welog", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }


    /**
     * 返回所有键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences("welog", context.MODE_PRIVATE);
        Map<String, ?> map = sp.getAll();
        return map;
    }

    /**
     * 删除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences("welog", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 检查key对应的数据是否存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("welog", context.MODE_PRIVATE);
        return sp.contains(key);
    }

}
