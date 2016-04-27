package com.zzia.graduation.http.json11;


import com.zzia.graduation.http.ASApiClient;
import com.zzia.graduation.http.ASApiRequest;

/**
 * Created by apple on 15/7/10.
 */
public abstract class ASJson11ApiClient implements ASApiClient {

//    @Override
//    public ASHttpClient getHttpClient() {
//        return ASOkHttpClient.getInstance();
//    }

    @Override
    public ASJson11ApiCall newCall(ASApiRequest request) {
        return new ASJson11ApiCall(this, request);
    }
}
