package com.zzia.graduation.common.log;

import java.io.File;

/**
 * 提供回调接口，由应用来决定对备份日志的处理
 */
public interface ApsLogSenderInterface {
    boolean sendLog(File logFile, String header);
    boolean sendLog(File logFile);
}
