package com.zzia.graduation.http;

import com.squareup.okhttp.Response;

/**
 * Created by apple on 15/12/22.
 */
public class RespUnSuccessException extends Exception implements IGetApiErrorCode {
    int code;

    public RespUnSuccessException(Response response) {
        code = response == null ? 0 : response.code();
    }

    @Override
    public String getCode() {
        return "resp_" + code;
    }
}
