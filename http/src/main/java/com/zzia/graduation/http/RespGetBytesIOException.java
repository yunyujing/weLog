package com.zzia.graduation.http;

import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by apple on 15/12/23.
 */
public class RespGetBytesIOException extends IOException implements IGetApiErrorCode {
    int code;

    public RespGetBytesIOException(IOException e, Response response) {
        super(e);
        code = response == null ? 0 : response.code();
    }

    @Override
    public String getCode() {
        return "resp_getbytes_" + code;
    }
}
