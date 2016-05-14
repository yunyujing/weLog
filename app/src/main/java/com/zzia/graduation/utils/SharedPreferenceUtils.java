package com.zzia.graduation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * SharedPreferences 工具类
 * Created by yunyujing on 16/5/13.
 */
public class SharedPreferenceUtils {

    private static SharedPreferences sp;

    public static SharedPreferenceUtils getSharedPreferenceUtils(Context context) {
        sp = context.getSharedPreferences("welog", Context.MODE_APPEND);

        return new SharedPreferenceUtils(sp);
    }

    public SharedPreferenceUtils(SharedPreferences sharedPreferences) {
        this.sp=sharedPreferences;
    }

    public static SharedPreferences getSp() {
        return sp;
    }

    public static boolean contains(String key) {
        return sp.contains(key);
    }

    public static String getValue(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static void setValue(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getValue(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static void setValue(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getValue(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static void setValue(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setValues(HashMap<String, Object> values) {
        Iterator<Map.Entry<String, Object>> iterator = values.entrySet().iterator();
        Map.Entry<String, Object> entry = null;
        SharedPreferences.Editor editor = sp.edit();
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (entry.getValue() instanceof Integer) {
                editor.putInt(entry.getKey(), Integer.parseInt(entry.getValue().toString()));
            } else if (entry.getValue() instanceof String) {
                editor.putString(entry.getKey(), entry.getValue().toString());
            } else if (entry.getValue() instanceof Boolean) {
                editor.putBoolean(entry.getKey(), Boolean.valueOf(entry.getValue().toString()));
            } else if (entry.getValue() instanceof Long) {
                editor.putLong(entry.getKey(), Long.parseLong(entry.getValue().toString()));
            }
        }
        editor.commit();
    }

}
