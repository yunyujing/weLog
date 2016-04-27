package com.zzia.graduation.http;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by apple on 15/7/10.
 * appshare的api执行
 */
public abstract class ASApiCall<D> implements ASApiLog {

    public interface ASApiSuccessRet<R> {
        /**
         * 每一获取成功数据的返回
         *
         * @param dataFrom   获取数据来源
         * @param successRet 成功的返回值
         */
        void onSuccess(ASApiDataFrom dataFrom, R successRet);
    }

    protected ASApiClient client;
    protected ASApiRequest request;

    public ASApiCall(ASApiClient client, ASApiRequest request) {
        this.client = client;
        this.request = request;
    }

    /**
     * 执行方法
     *
     * @param successCallback callback可多次返回成功数据
     * @return 返回最后的一个出错的信息数据
     */
    public abstract D execute(ASApiSuccessRet<D> successCallback) throws Exception;

    /**
     * 只获取单个数据来源的执行方法
     *
     * @param dataFrom 数据来源
     */
    public abstract D execute(ASApiDataFrom dataFrom) throws Exception;

    public abstract D bytesToR(byte[] bytes) throws Exception;

    public abstract byte[] getContent();

    public byte[] getBytes(ASApiDataFrom dataFrom) throws Exception {
        if (dataFrom == null) throw new NullPointerException("dataFrom == null");

        long startTime = System.currentTimeMillis();
        String tag = "ASApiDataFrom:" + dataFrom.name();
        String error = null;
        try {
            if (dataFrom == ASApiDataFrom.NET) {
                request.paramsCheck(ASApiDataFrom.NET);
                return getBytesApiNet();
            }

            if (dataFrom == ASApiDataFrom.CACHE) {
                request.paramsCheck(ASApiDataFrom.CACHE);
                return getBytesApiCache();
            }

            if (dataFrom == ASApiDataFrom.PRESET_CACHE) {
                request.paramsCheck(ASApiDataFrom.PRESET_CACHE);
                return getBytesPersetApiCache();
            }

            throw new IllegalArgumentException("dataFrom unknown");
        } catch (Exception e) {
            error = e.getMessage();
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            log(request, "getBytes", tag, startTime, endTime, error);
        }

    }

    /**
     * 从网络获取数据
     */
    private byte[] getBytesApiNet() throws Exception {
        String ipApiUrl = request.getIpApiUrl();
        String host = request.getHost();
        String apiUrl = request.getApiUrl();

        Log.e("getBytesApiNet", "ipApiUrl:" + ipApiUrl + ", host:" + host + ", apiUrl:" + apiUrl);
        if (TextUtils.isEmpty(ipApiUrl) || TextUtils.isEmpty(host)) {
            try {
                return client.getHttpClient().postBytes(apiUrl, getContent());
            } catch (ReqIOException | RespUnSuccessException | RespGetBytesIOException e) {
                errRequestCode(apiUrl, e.getCode());
                throw e;
            }
        } else {
            try {
                return client.getHttpClient().postBytes(ipApiUrl, host, getContent());
            } catch (ReqIOException | RespUnSuccessException | RespGetBytesIOException e) {
                errRequestCode(ipApiUrl, e.getCode());
                throw e;
            }
        }

    }

    /**
     * 是否属于有api缓存的请求，不属于是不会存储缓存和实用缓存的
     */
    public boolean isApiCacheRequest() {
        return client.getApiCacheUtil().getCacheDuration(request.getMethod(), request.getAllParams()) > 0;
    }

    /**
     * 缓存数据是否过期
     */
    public boolean apiCacheOutData() {
        return client.getApiCacheUtil().isOutDate(request.getMethod(), request.getAllParams());
    }

    /**
     * 从缓存获取数据
     */
    private byte[] getBytesApiCache() throws Exception {
        return client.getApiCacheUtil().getCacheDataBytes(request.getMethod(), request.getAllParams());
    }

    /**
     * 从预制缓存获取数据
     */
    private byte[] getBytesPersetApiCache() throws Exception {
        return client.getPersetApiCacheUtil().getCache(request.getPersetCacheKey());
    }

    @Override
    public void log(ASApiRequest request, String methodName, String tag, long startTime, long endTime, String error) {
        ASApiLog asApiLog = request.getASApiLog();

        if (asApiLog == null)
            return;

        asApiLog.log(request, methodName, tag, startTime, endTime, error);
    }

    @Override
    public void errRequestCode(String url, String code) {
        ASApiLog asApiLog = request.getASApiLog();

        if (asApiLog == null)
            return;

        asApiLog.errRequestCode(url, code);
    }
}
