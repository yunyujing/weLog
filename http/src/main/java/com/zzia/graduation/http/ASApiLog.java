package com.zzia.graduation.http;

/**
 * Created by apple on 15/7/25.
 */
public interface ASApiLog {
    void log(ASApiRequest request, String methodName, String tag, long startTime, long endTime, String error);

    void errRequestCode(String url, String code);
}
