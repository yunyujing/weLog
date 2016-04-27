package com.zzia.graduation.http;


import com.zzia.graduation.common.net.ApiCacheInteface;

/**
 * Created by apple on 15/7/10.
 * 含有httpclean,cache,persetcache的功能集合
 */
public interface ASApiClient {

    ASHttpClient getHttpClient();

    ApiCacheInteface getApiCacheUtil();

    IASPersetApiCache getPersetApiCacheUtil();

    ASApiCall newCall(ASApiRequest request);
}
