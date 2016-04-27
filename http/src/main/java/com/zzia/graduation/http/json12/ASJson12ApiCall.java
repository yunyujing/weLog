package com.zzia.graduation.http.json12;

import android.text.TextUtils;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.http.ASApiClient;
import com.zzia.graduation.http.ASApiRequest;
import com.zzia.graduation.http.ASJsonApiCall;
import com.zzia.graduation.http.ASJsonApiUtil;


/**
 * Created by apple on 15/11/9.
 */
public class ASJson12ApiCall extends ASJsonApiCall {
    public ASJson12ApiCall(ASApiClient client, ASApiRequest request) {
        super(client, request);
    }

    @Override
    public String json(byte[] bytes) throws Exception {
        if (bytes == null) {
            String ipApiUrl = request.getIpApiUrl();
            String host = request.getHost();
            String apiUrl = request.getApiUrl();
            errRequestCode(TextUtils.isEmpty(ipApiUrl) || TextUtils.isEmpty(host) ? apiUrl : ipApiUrl, "bytes_null");

            throw new NullPointerException("bytes == null");
        }

        String orgStr = new String(bytes, "UTF-8");

//        if(BuildConfig.DEBUG) {
//            Log.e("ASJson12ApiCall", "method:" + request.getMethod()
//                    + ",apiurl:" + request.getApiUrl()
//                    + ",ipapiurl:" + request.getIpApiUrl()
//                    + "json12-org:" + orgStr);
//        }


        if (!ASJsonApiUtil.isJsonStr(orgStr)) {

            String ipApiUrl = request.getIpApiUrl();
            String host = request.getHost();
            String apiUrl = request.getApiUrl();
            errRequestCode(TextUtils.isEmpty(ipApiUrl) || TextUtils.isEmpty(host) ? apiUrl : ipApiUrl, "notjson");

            throw new Exception("org string is not json string");
        }

        return orgStr;
    }

    @Override
    public BaseBean bytesToR(byte[] bytes) throws Exception {
        if (bytes == null) throw new NullPointerException("bytes == null");

        String json = json(bytes);

        return ASJsonApiUtil.getBaseBeanForJson(json);
    }

    @Override
    public byte[] getContent() {
        return ASJsonApiUtil.getRequestParamsNoBase64(request.getMethod(), request.getAllParams(), request.getUserToken(), request.getSkey());
    }
}
