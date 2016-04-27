package com.zzia.graduation.http;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by apple on 15/6/19.
 * <p/>
 * 写法来源
 * http://www.tuicool.com/articles/3INNz2
 */
public class SendFileByOkHttpUtils {
    private static final MediaType MEDIA_TYPE_OCTET_STREAM = MediaType.parse("application/octet-stream");

    public static boolean defSendLogFile(String uri, File file, Map<String, String> map) {
        try {
            Response response = sendLogFile(uri, file, map);
            if (!response.isSuccessful()) {
                Log.e("SendFileByOkHttpUtils", "defSendFile:!response.isSuccessful()");
                return false;
            }
            String json = response.body().string();
            Log.e("SendFileByOkHttpUtils", "defSendFile:json:" + json);
            JSONObject jsonObject = new JSONObject(json);
            return "0".equals(jsonObject.getString("retcode"));
        } catch (Exception e) {
            Log.e("SendFileByOkHttpUtils", "defSendFile", e);
            return false;
        }
    }

    public static Response sendLogFile(String uri, File file, Map<String, String> map) throws Exception {
        // 复杂集合体
        MultipartBuilder me = new MultipartBuilder();
        me.type(MultipartBuilder.FORM);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> kv = it.next();
            me.addFormDataPart(kv.getKey(), kv.getValue());
        }
        me.addFormDataPart("Filelogdata", file.getName(), RequestBody.create(MEDIA_TYPE_OCTET_STREAM, file));
        RequestBody requestBody = me.build();

        // 请求
        Request request = new Request.Builder()
                .url(uri)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        return client.newCall(request).execute();
    }

    public static Response sendFile(String uri, File file, Map<String, String> map) throws Exception {
        // 复杂集合体
        MultipartBuilder me = new MultipartBuilder();
        me.type(MultipartBuilder.FORM);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> kv = it.next();
            me.addFormDataPart(kv.getKey(), kv.getValue());
        }
        me.addFormDataPart("Filedata", file.getName(), RequestBody.create(MEDIA_TYPE_OCTET_STREAM, file));
        RequestBody requestBody = me.build();

        // 请求
        Request request = new Request.Builder()
                .url(uri)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        return client.newCall(request).execute();
    }

}
