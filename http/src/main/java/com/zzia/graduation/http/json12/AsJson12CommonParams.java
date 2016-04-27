package com.zzia.graduation.http.json12;


import com.zzia.graduation.http.ASCommonParams;

/**
 * Created by apple on 15/11/9.
 */
public abstract class AsJson12CommonParams extends ASCommonParams {
    public static final String API_FORMAT_JSON = "json";
    public static final String API_VERSION_JSON_1_2 = "1.2";

    @Override
    public String getApiFormat() {
        return API_FORMAT_JSON;
    }

    @Override
    public String getApiVersion() {
        return API_VERSION_JSON_1_2;
    }
}
