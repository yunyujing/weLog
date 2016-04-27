package com.zzia.graduation.http;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by apple on 15/7/10.
 * appshare基于okhttp的httpclient
 */
public class ASOkHttpClient implements ASHttpClient<Response> {

    public static ASOkHttpClient mInstance;

    public synchronized static ASOkHttpClient getInstance() {
        if (mInstance == null) mInstance = new ASOkHttpClient();
        return mInstance;
    }

    public static final MediaType XML = MediaType.parse("text/xml; charset=utf-8");
    private final OkHttpClient client;

    protected ASOkHttpClient() {
        this.client = new OkHttpClient();
        this.client.setConnectTimeout(10, TimeUnit.SECONDS);    // 超时时间
        this.client.setReadTimeout(30, TimeUnit.SECONDS);
//        this.client.setRetryOnConnectionFailure(false);
    }

    public byte[] postBytes(String url, byte[] content) throws ReqIOException, RespUnSuccessException, RespGetBytesIOException {
        return bytes(post(url, content));
    }

    public byte[] postBytes(String ipUrl, String host, byte[] content) throws ReqIOException, RespUnSuccessException, RespGetBytesIOException {
        return bytes(post(ipUrl, host, content));
    }

    @Override
    public Response post(String url, byte[] content) throws ReqIOException {
        if (url == null) throw new IllegalArgumentException("url == null");

        if (content == null) throw new IllegalArgumentException("content == null");

        RequestBody body = RequestBody.create(XML, content);
        Request request = new Request.Builder().url(url).post(body).build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new ReqIOException(e);
        }
    }

    @Override
    public Response post(String ipUrl, String host, byte[] content) throws ReqIOException {
        if (ipUrl == null) throw new IllegalArgumentException("ipUrl == null");

        if (host == null) throw new IllegalArgumentException("host == null");

        if (content == null) throw new IllegalArgumentException("content == null");

        RequestBody body = RequestBody.create(XML, content);
        Request request = new Request.Builder().addHeader("Host", host).url(ipUrl).post(body).build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new ReqIOException(e);
        }
    }

    @Override
    public byte[] bytes(Response ret) throws RespUnSuccessException, RespGetBytesIOException {
        if (ret == null) throw new IllegalArgumentException("response == null");

        if (!ret.isSuccessful()) throw new RespUnSuccessException(ret);

        try {
            return ret.body().bytes();
        } catch (IOException e) {
            throw new RespGetBytesIOException(e, ret);
        }
    }

    @Override
    public Response get(String url) throws ReqIOException {
        if (url == null) throw new IllegalArgumentException("url == null");

        Request request = new Request.Builder().url(url).build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new ReqIOException(e);
        }
    }
}
