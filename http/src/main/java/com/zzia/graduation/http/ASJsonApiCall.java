package com.zzia.graduation.http;

import android.util.Log;

import com.zzia.graduation.common.bean.BaseBean;


/**
 * Created by apple on 15/11/9.
 */
public abstract class ASJsonApiCall extends ASApiCall<BaseBean> {
    public static final String TAG = "ASJsonApiCall";

    public ASJsonApiCall(ASApiClient client, ASApiRequest request) {
        super(client, request);
    }

    /**
     * 网络数据处理为了存储缓存缓存
     */
    private void executeNetForSaveCache() {
        byte[] bytesApiNet;
        try {
            bytesApiNet = getBytes(ASApiDataFrom.NET);
            BaseBean baseBean = bytesToR(bytesApiNet);
            if (ASJsonApiUtil.retcodeSuccess(baseBean)) { // 存储网络缓存
                client.getApiCacheUtil().saveByteCache(request.getMethod(), request.getAllParams(), bytesApiNet);
            }
        } catch (Exception e) {
            Log.e(TAG, "NET", e);
        }
    }

    /**
     * 网络数据处理并存缓存
     */
    private BaseBean executeNetAndSaveCache(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        byte[] bytesApiNet;
        try {
            bytesApiNet = getBytes(ASApiDataFrom.NET);
            BaseBean baseBean = bytesToR(bytesApiNet);
            if (ASJsonApiUtil.retcodeSuccess(baseBean)) {
                if (successCallback != null)
                    successCallback.onSuccess(ASApiDataFrom.NET, baseBean);

                // 存储网络缓存
                try {
                    client.getApiCacheUtil().saveByteCache(request.getMethod(), request.getAllParams(), bytesApiNet);
                } catch (Exception e) {
                    Log.e("executeNetAndSaveCache", "saveByteCache", e);
                }

                return null;
            } else {
                return baseBean;
            }
        } catch (Exception e) {
            Log.e(TAG, "NET", e);
            throw e;
        }

    }

    /**
     * 网络数据处理 ASApiStrategy.Net
     */
    private BaseBean executeNet(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        byte[] bytesApiNet;

        try {
            bytesApiNet = getBytes(ASApiDataFrom.NET);
            BaseBean baseBean = bytesToR(bytesApiNet);
            if (ASJsonApiUtil.retcodeSuccess(baseBean)) {
                successCallback.onSuccess(ASApiDataFrom.NET, baseBean);
                return null;
            } else {
                return baseBean;
            }
        } catch (Exception e) {
            Log.e(TAG, "NET", e);
            throw e;
        }
    }

    /**
     * 缓存数据处理 ASApiStrategy.Cache
     */
    private BaseBean executeCache(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        byte[] bytesApiCache;
        try {
            bytesApiCache = getBytes(ASApiDataFrom.CACHE);
            BaseBean cacheBean = bytesToR(bytesApiCache);
            if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
                return null;
            } else {
                return cacheBean;
            }
        } catch (Exception e) {
            Log.e(TAG, "CACHE", e);
            throw e;
        }
    }

    /**
     * 预制缓存数据处理 ASApiStrategy.PersetCache
     */
    private BaseBean executePersetCache(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        byte[] bytesPersetApiCache;
        try {
            bytesPersetApiCache = getBytes(ASApiDataFrom.PRESET_CACHE);
            BaseBean persetCacheBean = bytesToR(bytesPersetApiCache);
            if (ASJsonApiUtil.retcodeSuccess(persetCacheBean)) {
                successCallback.onSuccess(ASApiDataFrom.PRESET_CACHE, persetCacheBean);
                return null;
            } else {
                return persetCacheBean;
            }
        } catch (Exception e) {
            Log.e(TAG, "PRESET_CACHE", e);
            throw e;
        }
    }

    /**
     * ASApiStrategy.UseOutDataFirstCache
     */
    private BaseBean executeUseOutDataFirstCache(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        // 不是缓存管理的请求，只请求网络
        if (!isApiCacheRequest()) {
            Log.e(TAG, "ApiCache not manager request");

            return executeNet(successCallback);
        }

        try {
            if (apiCacheOutData()) {
                BaseBean cacheBean = bytesToR(getBytes(ASApiDataFrom.CACHE));
                if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                    successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
                    successCallback = null;
                }
            } else {
                BaseBean cacheBean = bytesToR(getBytes(ASApiDataFrom.CACHE));
                if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                    successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
                    return null;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "CACHE", e);
        }

        if (successCallback == null) {
            executeNetForSaveCache();
            return null;
        } else {
            return executeNetAndSaveCache(successCallback);
        }

    }

    /**
     * ASApiStrategy.UseOutDataCache
     */
    private BaseBean executeUseOutDataCache(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        // 不是缓存管理的请求，只请求网络
        if (!isApiCacheRequest()) {
            Log.e(TAG, "ApiCache not manager request");

            return executeNet(successCallback);
        }

        try {
            if (apiCacheOutData()) { // 缓存过期
                BaseBean cacheBean = bytesToR(getBytes(ASApiDataFrom.CACHE));
                if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                    successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
                }
            } else { // 缓存没有过期
                BaseBean cacheBean = bytesToR(getBytes(ASApiDataFrom.CACHE));
                if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                    successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
                    return null;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "CACHE", e);
        }

        return executeNetAndSaveCache(successCallback);
    }

    /**
     * ASApiStrategy.UnUseOutDataCache
     */
    private BaseBean executeUnUseOutDataCache(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        // 不是缓存管理的请求，只请求网络
        if (!isApiCacheRequest()) {
            Log.e(TAG, "ApiCache not manager request");
            return executeNet(successCallback);
        }

        if (!apiCacheOutData()) {
            BaseBean cacheBean = bytesToR(getBytes(ASApiDataFrom.CACHE));
            if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
                return null;
            }
        }

        return executeNetAndSaveCache(successCallback);
    }

    /**
     * ASApiStrategy.Cache_Net
     */
    private BaseBean executeCacheNet(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        // 不是缓存管理的请求，只请求网络
        if (!isApiCacheRequest()) {
            Log.e(TAG, "ApiCache not manager request");

            return executeNet(successCallback);
        }

        try {
            BaseBean cacheBean = bytesToR(getBytes(ASApiDataFrom.CACHE));
            if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
            }
        } catch (Exception e) {
            Log.e(TAG, "CACHE", e);
        }

        return executeNetAndSaveCache(successCallback);
    }

    /**
     * ASApiStrategy.Perset_Cache_Net
     */
    private BaseBean executePersetCacheNet(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        // 不是缓存管理的请求
        if (!isApiCacheRequest()) {
            Log.e(TAG, "ApiCache not manager request");

            try {
                BaseBean persetCacheBean = bytesToR(getBytes(ASApiDataFrom.PRESET_CACHE));
                if (ASJsonApiUtil.retcodeSuccess(persetCacheBean)) {
                    successCallback.onSuccess(ASApiDataFrom.PRESET_CACHE, persetCacheBean);
                }
            } catch (Exception e) {
                Log.e(TAG, "PRESET_CACHE", e);
            }

            return executeNet(successCallback);
        }

        boolean usePersetCache = true;

        try {
            if (apiCacheOutData()) { // 缓存过期
                BaseBean cacheBean = bytesToR(getBytes(ASApiDataFrom.CACHE));
                if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                    successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
                    usePersetCache = false;
                }
            } else { // 缓存没有过期
                BaseBean cacheBean = bytesToR(getBytes(ASApiDataFrom.CACHE));
                if (ASJsonApiUtil.retcodeSuccess(cacheBean)) {
                    successCallback.onSuccess(ASApiDataFrom.CACHE, cacheBean);
                    return null;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "CACHE", e);
        }

        if (usePersetCache) {
            try { // 没有CACHE使用预制缓存
                BaseBean persetCacheBean = bytesToR(getBytes(ASApiDataFrom.PRESET_CACHE));
                if (ASJsonApiUtil.retcodeSuccess(persetCacheBean)) {
                    successCallback.onSuccess(ASApiDataFrom.PRESET_CACHE, persetCacheBean);
                }
            } catch (Exception e) {
                Log.e(TAG, "PRESET_CACHE", e);
            }
        }

        return executeNetAndSaveCache(successCallback);

    }

    @Override
    public BaseBean execute(ASApiSuccessRet<BaseBean> successCallback) throws Exception {
        if (successCallback == null) throw new IllegalArgumentException("successCallback == null");

        ASApiStrategy strategy = request.getStrategy();
        if (strategy == null) throw new IllegalArgumentException("strategy == null");

        long startTime = System.currentTimeMillis();
        String tag = "ASApiStrategy:" + strategy.name();
        String error = null;
        try {
            if (strategy == ASApiStrategy.Perset_Cache_Net)
                return executePersetCacheNet(successCallback);

            if (strategy == ASApiStrategy.UseOutDataFirstCache)
                return executeUseOutDataFirstCache(successCallback);

            if (strategy == ASApiStrategy.UseOutDataCache)
                return executeUseOutDataCache(successCallback);

            if (strategy == ASApiStrategy.UnUseOutDataCache)
                return executeUnUseOutDataCache(successCallback);

            if (strategy == ASApiStrategy.Cache_Net)
                return executeCacheNet(successCallback);

            if (strategy == ASApiStrategy.Net)
                return executeNet(successCallback);

            if (strategy == ASApiStrategy.Cache)
                return executeCache(successCallback);

            if (strategy == ASApiStrategy.PersetCache)
                return executePersetCache(successCallback);

            throw new IllegalArgumentException("strategy unknown");
        } catch (Exception e) {
            error = e.getMessage();
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            log(request, "execute", tag, startTime, endTime, error);
        }


    }

    @Override
    public BaseBean execute(ASApiDataFrom dataFrom) throws Exception {
        long startTime = System.currentTimeMillis();
        String tag = dataFrom == null ? "ASApiDataFrom:null" : "ASApiDataFrom:" + dataFrom.name();
        String error = null;
        try {
            return bytesToR(getBytes(dataFrom));
        } catch (Exception e) {
            error = e.getMessage();
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            log(request, "execute", tag, startTime, endTime, error);
        }
    }



    public BaseBean jsonToR(String json) throws Exception {
        return ASJsonApiUtil.getBaseBeanForJson(json);
    }

    public abstract String json(byte[] bytes) throws Exception;
}
