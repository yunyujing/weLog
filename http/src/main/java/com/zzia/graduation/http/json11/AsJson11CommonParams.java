package com.zzia.graduation.http.json11;


import com.zzia.graduation.http.ASCommonParams;

/**
 * Created by apple on 15/7/10.
 */
public abstract class AsJson11CommonParams extends ASCommonParams {
    public static final String API_FORMAT_JSON = "json";
    public static final String API_VERSION_JSON_1_1 = "1.1";

    @Override
    public String getApiFormat() {
        return API_FORMAT_JSON;
    }

    @Override
    public String getApiVersion() {
        return API_VERSION_JSON_1_1;
    }

}
