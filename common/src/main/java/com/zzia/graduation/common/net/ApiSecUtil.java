package com.zzia.graduation.common.net;


import com.zzia.graduation.common.util.Base64;
import com.zzia.graduation.common.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ApiSecUtil {
    /**
     * 是否是base64编码的字符串
     */
    public static boolean isClearText(String data) {
        if (data == null || data.trim().length() == 0) {
            return true;
        } else {
            // 如果起始字符为[a-zA-Z0-9+/]则认为是base64编码
            char c = data.charAt(0);
            if (c >= 'A' && c <= 'Z') {
                return false;
            } else if (c >= 'a' && c <= 'z') {
                return false;
            } else if (c >= '0' && c <= '9') {
                return false;
            } else if (c == '+' || c == '/') {
                return false;
            }
        }
        return true;
    }

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] key, byte[] iv, byte[] clear) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            int plaintextLength = clear.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(clear, 0, plaintext, 0, clear.length);

            SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            return cipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new RuntimeException("encrypt error", e);
        }
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] key, byte[] iv, byte[] endata) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            return cipher.doFinal(endata);
        } catch (Exception e) {
            throw new RuntimeException("decrypt error", e);
        }
    }

    public static String encodeBase64(String str) {
        return new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
    }

    public static String decodeBase64(String str) {
        if (isEncrypt(str)) {
            return new String(Base64.decode(str.getBytes(), Base64.DEFAULT));
        }
        return str;
    }

    public static boolean isEncrypt(String str) {
        return !StringUtils.isEmpty(str) && !str.contains("http://");
    }
}
