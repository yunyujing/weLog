package com.zzia.graduation.common.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CommonStoreSpUtil {

    public static final String device_id = "common_device_id";
    public static final String is_send_device_info = "send_device_info";

    private static SharedPreferences sp;


    public static void init(Context context){
        if (null == sp) {
            sp = context.getSharedPreferences("common", Context.MODE_PRIVATE);
        }
    }

    public static SharedPreferences getSp() {
        return sp;
    }


    public static String getValue(String key, String defValue){
        if (null == sp) {
            return "";
        }
        return sp.getString(key, defValue);
    }
    public static void setValue(String key, String value){
        if (null == sp) {
            return;
        }
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static int getValue(String key, int defValue){
        if (null == sp) {
            return 0;
        }
        return sp.getInt(key, defValue);
    }
    public static void setValue(String key, int value){
        if (null == sp) {
            return;
        }
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public static float getValue(String key, float defValue){
        if (null == sp) {
            return 0f;
        }
        return sp.getFloat(key, defValue);
    }
    public static void setValue(String key, float value){
        if (null == sp) {
            return;
        }
        Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    public static long getValue(String key, long defValue){
        if (null == sp) {
            return 0L;
        }
        return sp.getLong(key, defValue);
    }
    public static void setValue(String key, long value){
        if (null == sp) {
            return;
        }
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }
    public static boolean getValue(String key, boolean defValue){
        if (null == sp) {
            return false;
        }
        return sp.getBoolean(key, defValue);
    }
    public static void setValue(String key, boolean value){
        if (null == sp) {
            return;
        }
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static void setValues(HashMap<String, String> values){
        if (null == sp) {
            return;
        }
        Iterator<Entry<String, String>> iterator = values.entrySet().iterator();
        Entry<String, String> entry = null;
        Editor editor = sp.edit();
        while(iterator.hasNext()){
            entry = iterator.next();
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }

}
