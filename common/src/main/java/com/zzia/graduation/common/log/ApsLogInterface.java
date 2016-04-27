package com.zzia.graduation.common.log;


public interface ApsLogInterface {

    /**
     * 获取日志文件路径
     *
     * @return 日志文件路径
     */
    String getLogFilePath();

    /**
     * 将信息写入文件
     *
     * @param message
     */
    void save(String message);

    void sendLog();


    /**
     * 清除本地的log文本信息
     */
    void cleanLog();

}
