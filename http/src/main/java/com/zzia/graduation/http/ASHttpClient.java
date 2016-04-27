package com.zzia.graduation.http;

/**
 * Created by apple on 15/7/10.
 * appshare的httpclient
 */
public interface ASHttpClient<R> {

    byte[] postBytes(String url, byte[] content) throws ReqIOException, RespUnSuccessException, RespGetBytesIOException;

    byte[] postBytes(String ipUrl, String host, byte[] content) throws ReqIOException, RespUnSuccessException, RespGetBytesIOException;

    /**
     * 获取R实体Bytes
     */
    byte[] bytes(R ret) throws RespUnSuccessException, RespGetBytesIOException;


    /**
     * post请求
     *
     * @param url     post的url
     * @param content post携带的内容
     */
    R post(String url, byte[] content) throws ReqIOException;

    /**
     * post请求
     *
     * @param host    ipUrl的host
     * @param ipUrl   post的ipUrl
     * @param content post携带的内容
     */
    R post(String ipUrl, String host, byte[] content) throws ReqIOException;

    /**
     * get请求
     *
     * @param url get请求的url
     */
    R get(String url) throws ReqIOException;
}
