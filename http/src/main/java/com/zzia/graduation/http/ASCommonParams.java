package com.zzia.graduation.http;

import android.os.Build;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 15/7/10.
 * 通用参数
 */
public abstract class ASCommonParams {

    /**
     * api的url
     */
    public abstract String getApiUrl();

    /**
     * getIpApiUrl的host
     */
    public abstract String getHost();

    /**
     * 含ip的apiUrl
     */
    public abstract String getIpApiUrl();

    /**
     * 返回数据的格式
     */
    public abstract String getApiFormat();


    /**
     * api的版本号
     */
    public abstract String getApiVersion();


    /**
     * 应用代号
     */
    public abstract String getPrdCaller();


    /**
     * 应用版本号
     */
    public abstract String getPrdVersion();


    /**
     * 设备Id
     */
    public abstract String getDeviceId();


    /**
     * 获取当前应用api密钥
     */
    public abstract String getSkey();

    /**
     * 获取token
     */
    public abstract String getUserToken();

    /**
     * 获取渠道
     */
    public abstract String getChannel();

    /**
     * 执行中关注的ASApiLog
     */
    public abstract ASApiLog getASApiLog();

    /**
     * 通用请求参数Map
     */
    public Map<String, String> getCommonPramas() {
        HashMap<String, String> commonPramas = new HashMap<>();
        commonPramas.put("caller", getPrdCaller());
        commonPramas.put("format", getApiFormat());
        commonPramas.put("ver", getApiVersion());
        commonPramas.put("prd_ver", getPrdVersion());
        commonPramas.put("mode", Build.MODEL);
        String deviceId = getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            commonPramas.put("device_id", deviceId);
        }
        String channel = getChannel();
        if (!TextUtils.isEmpty(channel)) {
            commonPramas.put("market_channel_id", channel);
        }
        return commonPramas;
    }

//    /**
//     * 获取所有参数map
//     *
//     * @param otherParams 其他参数
//     */
//    public Map<String, String> getAllParams(Map<String, String> otherParams) {
//        // other参数添加通用参数
//        TreeMap<String, String> allParams = new TreeMap<>();
//        if (otherParams == null || otherParams.isEmpty()) {
//            allParams.putAll(getCommonPramas());
//        } else {
//            allParams.putAll(otherParams);
//            allParams.putAll(getCommonPramas());
//        }
//        return allParams;
//    }
}
