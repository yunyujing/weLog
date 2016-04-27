package com.zzia.graduation.common.net;

import com.zzia.graduation.common.bean.BaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * JSON解析接口
 *
 * @author xiongfei.miao 20111027
 */
public interface DefaultJSONData {

    // 解析json数组
     void parse(JSONArray array) throws JSONException;

    // 解析json对象
     void parse(JSONObject object) throws JSONException;

     BaseBean getMap();

     List<Object> getList();

     boolean isHasData();
}
