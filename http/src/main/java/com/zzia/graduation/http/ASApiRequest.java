package com.zzia.graduation.http;

import android.util.Log;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by apple on 15/7/9.
 * AppShare的api请求
 */
public class ASApiRequest {

    private String apiUrl;

    private String host;

    private String ipApiUrl;

    private String method;

    private Map<String, String> allParams;

    private String userToken;

    private String skey;

    private String persetCacheKey;

    private ASApiStrategy strategy;

    private long tagStartTime;

    private ASApiLog asApiLog;

    // 默认策略
    public static final ASApiStrategy DEF_STRATEGY = ASApiStrategy.UseOutDataFirstCache;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getAllParams() {
        return allParams;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getSkey() {
        return skey;
    }

    public String getPersetCacheKey() {
        return persetCacheKey;
    }

    public ASApiStrategy getStrategy() {
        return strategy;
    }

    public String getHost() {
        return host;
    }

    public String getIpApiUrl() {
        return ipApiUrl;
    }

    public long getTagStartTime() {
        return tagStartTime;
    }

    public ASApiLog getASApiLog() {
        return asApiLog;
    }


    /**
     * 参数检测
     */
    public void paramsCheck(ASApiDataFrom dataFrom) {
        if (dataFrom == null) throw new IllegalArgumentException("dataFrom == null");

        if (dataFrom == ASApiDataFrom.NET) {
            if (apiUrl == null) throw new IllegalArgumentException("apiUrl == null");
            if (method == null) throw new IllegalArgumentException("method == null");
            if (allParams == null) throw new IllegalArgumentException("allParams == null");

            return;
        }

        if (dataFrom == ASApiDataFrom.CACHE) {
            if (method == null) throw new IllegalArgumentException("method == null");
            if (allParams == null) throw new IllegalArgumentException("allParams == null");

            return;
        }

        if (dataFrom == ASApiDataFrom.PRESET_CACHE) {
            if (persetCacheKey == null)
                throw new IllegalArgumentException("persetCacheKey == null");

            return;
        }

        throw new IllegalArgumentException("dataFrom unknown");
    }

    public ASApiRequest(Builder builder) {
        this.tagStartTime = System.currentTimeMillis();
        this.apiUrl = builder.apiUrl;
        this.host = builder.host;
        this.ipApiUrl = builder.ipApiUrl;
        this.method = builder.method;
        this.allParams = builder.allParams;
        this.userToken = builder.userToken;
        this.skey = builder.skey;
        this.persetCacheKey = builder.persetCacheKey;
        this.strategy = builder.strategy == null ? DEF_STRATEGY : builder.strategy;
        this.asApiLog = builder.asApiLog;

        for (ASApiDataFrom dataFrom : strategy.dataFroms) {
            paramsCheck(dataFrom);
        }
    }

    /**
     * 请求参数对照
     * 1.DataFrom.API_NET : apiUrl, method, allParams
     * 2.DataFrom.API_CACHE : method, allParams
     * 3.DataFrom.PRESET_CACHE : persetCacheKey
     */
    public static class Builder {

        private String apiUrl;

        private String host;

        private String ipApiUrl;

        private String method;

        private Map<String, String> allParams;

        private String userToken;

        private String skey;

        private String persetCacheKey;

        private ASApiStrategy strategy;

        private ASApiLog asApiLog;

        public Builder() {
        }

        public Builder(ASCommonParams commonParams) {
            apiUrl(commonParams.getApiUrl(), commonParams.getHost(), commonParams.getIpApiUrl())
                    .addParams(commonParams.getCommonPramas())
                    .skey(commonParams.getSkey())
                    .asApiLog(commonParams.getASApiLog())
                    .userToken(commonParams.getUserToken());
        }

        public Builder asApiLog(ASApiLog asApiLog) {
            this.asApiLog = asApiLog;
            return this;
        }

        private Map<String, String> getAllParams() {
            if (allParams == null) allParams = new TreeMap<>();
            return allParams;
        }

        public Builder apiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            this.host = null;
            this.ipApiUrl = null;
            return this;
        }

        public Builder apiUrl(String apiUrl, String host, String ipApiUrl) {
            this.apiUrl = apiUrl;
            this.host = host;
            this.ipApiUrl = ipApiUrl;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder userToken(String userToken) {
            this.userToken = userToken;
            return this;
        }

        public Builder addParams(String key, String value) {
            if (key == null || value == null) { // key和value不能为空
                Log.e("ASApiRequest", "addParams:key=" + key + ",value=" + value);
                return this;
            }
            getAllParams().put(key, value);
            return this;
        }

        public Builder addParams(Map<String, String> paramsMap) {
            if (paramsMap == null) {
                Log.e("ASApiRequest", "addParams:paramsMap == null");
                return this;
            }

            getAllParams().putAll(paramsMap);
            return this;
        }

        public Builder skey(String skey) {
            this.skey = skey;
            return this;
        }

        public Builder persetApiCache(String persetCacheKey) {
            this.persetCacheKey = persetCacheKey;
            return this;
        }

        public Builder strategy(ASApiStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public ASApiRequest build() {
            return new ASApiRequest(this);
        }
    }

}
