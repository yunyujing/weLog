package com.zzia.graduation.http;

import android.text.TextUtils;
import android.util.Base64;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.common.net.ApiSecUtil;
import com.zzia.graduation.common.net.BasicHandler;
import com.zzia.graduation.common.net.DefaultJSONData;
import com.zzia.graduation.common.net.SignUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by apple on 15/7/10.
 * AppShare的json11工具类
 */
public class ASJsonApiUtil {
    /**
     * 请求post的携带的content
     *
     * @param method   方法
     * @param sendData 请求原始数据
     * @return 加密好的请求字节数组
     */
    public static String getRequestParams(String method, Map<String, String> sendData, String userToken , String skey) {
        return Base64.encodeToString(getRequestParamsNoBase64(method, sendData, userToken, skey), Base64.DEFAULT);
    }

    public static byte[] getRequestParamsNoBase64(String method, Map<String, String> sendData, String userToken, String skey) {
        // 要发送的数据
        TreeMap<String, String> data = new TreeMap<>(sendData);
        data.remove("_method");
        data.put("method", method);
        // 对数据增加时间戳
        data.put("timestamp", String.valueOf(System.currentTimeMillis()));

        if (!TextUtils.isEmpty(userToken) && !data.containsKey("token")) {
            data.put("token", userToken);
        }

        // 签名
        data.put("sign", SignUtil.getSignature(data, skey));

        // 对发送的数据base64编码
        JSONObject jsonObject = new JSONObject(data);
        return jsonObject.toString().getBytes();
    }


    /**
     * 返回的respBean是否成功
     */
    public static boolean retcodeSuccess(BaseBean respBean) {
        return respBean != null && "0".equals(respBean.getStr("retcode"));
    }

    /**
     * 将bytes解密并转换成BaseBean
     */
    public static BaseBean bytesDecodeToBaseBean(byte[] bytes, String skey) throws Exception {
        byte[] decodebytes = bytesDecode(bytes, skey);

        String json = new String(decodebytes, "UTF-8");

        return getBaseBeanForJson(json);
    }

    /**
     * 将bytes解密
     */
    public static byte[] bytesDecode(byte[] bytes, String skey) throws Exception {
        if (bytes == null) throw new NullPointerException("bytes == null");

        return decodeBytes(bytes, skey);
    }

    /**
     * 是否是json字符串
     */
    public static boolean isJsonStr(String str) {
        return str != null && (str.startsWith("{") || str.startsWith("["));
    }

    /**
     * 将json字符串转换成BaseBean
     *
     * @param json 原始str数据，主要是json
     * @return 通用BaseBean
     * @throws Exception 无法处理的异常
     */
    public static BaseBean getBaseBeanForJson(String json) throws Exception {
        if (!isJsonStr(json))
            throw new Exception("input string not json string!");

        if (json.startsWith("{")) {
            JSONObject object = new JSONObject(json);
            DefaultJSONData defaultJSONData = new BasicHandler();
            defaultJSONData.parse(object);
            return defaultJSONData.getMap();
        }

        if (json.startsWith("[")) {
            JSONArray object = new JSONArray(json);
            DefaultJSONData defaultJSONData = new BasicHandler();
            defaultJSONData.parse(object);
            return defaultJSONData.getMap();
        }

        throw new Exception("input string not json string!");
    }

//    public static BaseBean getBaseBeanForJson(String json) throws Exception {
//        if (json.startsWith("{")) {
//            JSONObject object;
//            object = new JSONObject(json);
//            if (object.optInt("code") == -1011) { // token invalid
//                throw new Exception("TOKEN INVALID");
//            } else {
//            DefaultJSONData defaultJSONData = new BasicHandler();
//            defaultJSONData.parse(object);
//            return defaultJSONData.getMap();
//            }
//        } else if (json.startsWith("[")) {
//            JSONArray object = new JSONArray(json);
//            DefaultJSONData defaultJSONData = new BasicHandler();
//            defaultJSONData.parse(object);
//
//            return defaultJSONData.getMap();
//        } else {
//            throw new Exception("NOT JSON");
//        }
//    }

    /**
     * 解密api返回的数据
     *
     * @param cacheBytes 输入的字节数据
     * @return 解密完成的数据
     */
    public static byte[] decodeBytes(byte[] cacheBytes, String skey) throws Exception {
        if (skey == null) throw new NullPointerException("skey == null");

        // clean UTF-8 BOM EFBBBF
        // http://www.cnblogs.com/chenwenbiao/archive/2011/07/27/2118372.html
        // 去掉部分字符串携带UTF-8头标识
        if (cacheBytes.length > 2
                && "EF".equals(Integer.toHexString(cacheBytes[0] & 0xFF).toUpperCase(Locale.US))
                && "BB".equals(Integer.toHexString(cacheBytes[1] & 0xFF).toUpperCase(Locale.US))
                && "BF".equals(Integer.toHexString(cacheBytes[2] & 0xFF).toUpperCase(Locale.US))) {
            return ApiSecUtil.decrypt(skey.getBytes(), getApiSecIv(), Base64.decode(cacheBytes, 3, cacheBytes.length - 3, Base64.DEFAULT));
        } else {
            return ApiSecUtil.decrypt(skey.getBytes(), getApiSecIv(), Base64.decode(cacheBytes, Base64.DEFAULT));
        }

    }

    /**
     * IvParameterSpec的字节数组
     */
    private static byte[] getApiSecIv() {
        return "gcsbbisagoodfath".getBytes();
    }
}
