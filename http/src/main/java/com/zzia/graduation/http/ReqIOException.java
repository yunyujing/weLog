package com.zzia.graduation.http;

import java.io.IOException;

/**
 * Created by apple on 15/12/23.
 * 请求连接出错或者写入数据出错，API发送环节出错
 */
public class ReqIOException extends IOException implements IGetApiErrorCode {
    public ReqIOException(IOException e) {
        super(e);
    }

    @Override
    public String getCode() {
        return "req_io";
    }
}
