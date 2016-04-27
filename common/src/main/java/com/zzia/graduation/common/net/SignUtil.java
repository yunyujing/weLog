package com.zzia.graduation.common.net;


import com.zzia.graduation.common.util.MD5Util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SignUtil {
    public static String getSignature(Map<String, String> data, String key) {
        StringBuffer valueBuffer = new StringBuffer();
        try {
            Set<Entry<String, String>> set = data.entrySet();
            if (set != null) {
                Iterator<Entry<String, String>> iterator = set.iterator();
                if (iterator != null) {
                    while (iterator.hasNext()) {
                        Entry<String, String> entry = iterator.next();
                        if (entry != null) {
                        	if (entry.getValue() == null) {
								entry.setValue("");
							}
                            valueBuffer.append(entry.getKey()).append("=").append(entry.getValue());
                        }
                    }
                }
            }
        } catch (Exception e) {}
        valueBuffer.append(key);
        return MD5Util.getMD5String(valueBuffer.toString());
    }
}
