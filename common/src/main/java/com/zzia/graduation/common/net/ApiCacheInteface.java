package com.zzia.graduation.common.net;


import com.zzia.graduation.common.bean.BaseBean;

import java.io.File;
import java.util.Map;

public interface ApiCacheInteface {

    /**
     * 保存缓存策略的配置信息
     */
    void saveConfig(BaseBean baseBean);

    /**
     * 清除缓存
     */
    void cleanCache();

    /**
     * 当前缓存是否过期
     */
    boolean isOutDate(String method, Map<String, String> params);

    /**
     * 得到缓存文件
     */
    File getCacheFile(String method, Map<String, String> params);

    /**
     * 得到此接口的缓存数据
     */
    String getCacheData(String method, Map<String, String> params);

    /**
     * 得到此接口的bytes缓存数据
     */
    byte[] getCacheDataBytes(String method, Map<String, String> params);


    /**
     * 得到此接口的缓存时长
     */
    long getCacheDuration(String method, Map<String, String> params);

    /**
     * 保存缓存数据
     */
    boolean saveCache(String method, Map<String, String> params, String data);

    /**
     * 保存bytes缓存数据
     */
    boolean saveByteCache(String method, Map<String, String> params, byte[] dataBytes);
}
