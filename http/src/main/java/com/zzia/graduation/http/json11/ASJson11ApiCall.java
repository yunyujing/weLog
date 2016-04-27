package com.zzia.graduation.http.json11;

import android.util.Log;

import com.zzia.graduation.common.bean.BaseBean;
import com.zzia.graduation.http.ASApiClient;
import com.zzia.graduation.http.ASApiRequest;
import com.zzia.graduation.http.ASJsonApiCall;
import com.zzia.graduation.http.ASJsonApiUtil;


/**
 * Created by apple on 15/7/10.
 * 通用json11的bytes转成BaseBean
 */
public class ASJson11ApiCall extends ASJsonApiCall {
    public ASJson11ApiCall(ASApiClient client, ASApiRequest request) {
        super(client, request);
    }

    @Override
    public byte[] getContent() {
        return ASJsonApiUtil.getRequestParams(request.getMethod(), request.getAllParams(), request.getUserToken(), request.getSkey()).getBytes();
    }

    @Override
    public BaseBean bytesToR(byte[] bytes) throws Exception {
        String json = json(bytes);
        Log.e("bytesToR", "method:" + request.getMethod()
                + ",apiurl:" + request.getApiUrl()
                + ",ipapiurl:" + request.getIpApiUrl()
                + ",json:" + json);
        return jsonToR(json);
    }

    /**
     * 从可能加密，也可能不加密的字节数组中获取json字符串
     */
    @Override
    public String json(byte[] bytes) throws Exception {
        if (bytes == null) throw new NullPointerException("bytes == null");

        try {
            byte[] decodeBytes = ASJsonApiUtil.bytesDecode(bytes, request.getSkey());
            String json = new String(decodeBytes, "UTF-8");
            Log.e("ASJson11ApiCall", "json11:" + json);
            if (!ASJsonApiUtil.isJsonStr(json))
                throw new Exception("org string is not json string");
            return json;
        } catch (Exception e) {
            String orgStr = new String(bytes, "UTF-8");
            Log.e("ASJson11ApiCall", "json11-org:" + orgStr);
            if (!ASJsonApiUtil.isJsonStr(orgStr))
                throw e;
            return orgStr;
        }
    }

}
