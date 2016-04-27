package com.zzia.graduation.http.json12;


import com.zzia.graduation.http.ASApiClient;
import com.zzia.graduation.http.ASApiRequest;
import com.zzia.graduation.http.ASHttpClient;
import com.zzia.graduation.http.ASOkHttpClient;

/**
 * Created by apple on 15/11/9.
 */
public abstract class ASJson12ApiClient implements ASApiClient {
    @Override
    public ASHttpClient getHttpClient() {
        return ASOkHttpClient.getInstance();
    }

    @Override
    public ASJson12ApiCall newCall(ASApiRequest request) {
        return new ASJson12ApiCall(this, request);
    }
}
