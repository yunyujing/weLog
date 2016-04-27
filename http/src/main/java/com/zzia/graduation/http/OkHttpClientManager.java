package com.zzia.graduation.http;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by hcy on 2016/2/26.
 *
 * OkHttpClientManager
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpClientManager(){
        mOkHttpClient = new OkHttpClient();
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance(){
        if (mInstance == null)
        {
            synchronized (OkHttpClientManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response getAsyn(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     */
    private String getAsString(String url) throws IOException {
        Response execute = getAsyn(url);
        return execute.body().string();
    }


    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    public void getAsyn(String url, final ResultCallback callback){
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }


    private void deliveryResult(final ResultCallback callback, Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Request request, final IOException e){
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response)
            {
                try{
                    String string = response.body().string();
                    sendSuccessResultCallback(string, callback);
                } catch (IOException e){//IO异常
                    sendFailedStringCallback(response.request(), e, callback);
                }
            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onResponse(object);
                }
            }
        });
    }

    public static abstract class ResultCallback<T>{

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }

}

