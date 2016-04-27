package com.zzia.graduation.common.net;


import com.zzia.graduation.common.bean.BaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 通用若对象数据解析类
 *
 * @author xiongfei.miao
 *
 */
public class BasicHandler implements DefaultJSONData {

    private BaseBean map;
    private List<Object> list;

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public BaseBean getMap() {
        return map;
    }

    public void setMap(BaseBean map) {
        this.map = map;
    }

    @Override
    public boolean isHasData() {
        boolean b = false;
        if (null != map && map.isHasData()) {
            b = true;
        } else if (list != null && list.size() > 0) {
            b = true;
        }
        return b;
    }

    /**
     * 解析json数组
     */
    @Override
    public void parse(JSONArray array) throws JSONException {
        list = new ArrayList<Object>();
        int len = array.length();
        for (int i = 0; i < len; i++) {
            if (null != array.optJSONArray(i)) {
                list.add(parseJSONArray(array.optJSONArray(i)));
            } else if (null != array.optJSONObject(i)) {
                list.add(new BaseBean(array.optJSONObject(i)));
            }
        }
    }

    @Override
    public void parse(JSONObject object) throws JSONException {
        map = new BaseBean(object);
    }

    private List<Object> parseJSONArray(JSONArray jsonArray) {
        List<Object> objects = new ArrayList<Object>();
        int len = jsonArray.length();
        for (int i = 0; i < len; i++) {
            if (null != jsonArray.optJSONArray(i)) {
                objects.add(parseJSONArray(jsonArray.optJSONArray(i)));
            } else if (null != jsonArray.optJSONObject(i)) {
                objects.add(new BaseBean(jsonArray.optJSONObject(i)));
            } else {
                objects.add(jsonArray.opt(i));
            }
        }
        return objects;
    }
}
