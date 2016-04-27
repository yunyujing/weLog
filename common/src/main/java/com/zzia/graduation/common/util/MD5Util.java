package com.zzia.graduation.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {/*
 * protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
 * '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; protected static
 * MessageDigest messagedigest = null; static { try { messagedigest =
 * MessageDigest.getInstance("MD5"); } catch (NoSuchAlgorithmException e) {
 * LogUtils.i("MD5Util", "MD5FileUtil messagedigest初始化失败"); } }
 * 
 * public static String getFileMD5String(File file) throws IOException {
 * FileInputStream in = new FileInputStream(file); String md5Str =
 * bufferToHex(Md5Util.digest(in)); messagedigest.reset(); System.gc(); return
 * md5Str; }
 * 
 * public static String getMD5String(String s) { return
 * getMD5String(s.getBytes()); }
 * 
 * public static String getMD5String(byte[] bytes) {
 * messagedigest.update(bytes); return bufferToHex(messagedigest.digest()); }
 * 
 * private static String bufferToHex(byte bytes[]) { return bufferToHex(bytes,
 * 0, bytes.length); }
 * 
 * private static String bufferToHex(byte bytes[], int m, int n) {
 * StringBuffer stringbuffer = new StringBuffer(2 * n); int k = m + n; for
 * (int l = m; l < k; l++) { appendHexPair(bytes[l], stringbuffer); } return
 * stringbuffer.toString(); }
 * 
 * private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
 * char c0 = hexDigits[(bt & 0xf0) >> 4]; char c1 = hexDigits[bt & 0xf];
 * stringbuffer.append(c0); stringbuffer.append(c1); }
 * 
 * public static boolean checkPassword(String password, String md5PwdStr) {
 * String s = getMD5String(password); return s.equals(md5PwdStr); }
 * 
 * // public static void main(String[] args) throws IOException { // long
 * begin = System.currentTimeMillis(); // // File big = new
 * File("D:\\temp\\jre-7u11-linux-i586.tar.gz"); // String md5 =
 * getFileMD5String(big); // // long end = System.currentTimeMillis(); //
 * System.out.println("md5:" + md5); // System.out.println("time:" + ((end -
 * begin) / 1000) + "s"); // // }
 */

    protected final static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    protected static MessageDigest messagedigest = null;
    static {
        initMessageDigest();
    }

    private static void initMessageDigest(){
        if (null == messagedigest) {
            try {
                messagedigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                //            LogUtils.i("MD5Util", "MD5FileUtil messagedigest初始化失败");
            }
        }
    }

    public static String getFileMD5String(File file) throws IOException {
        /*
         * FileInputStream in = new FileInputStream(file); FileChannel ch = in.getChannel();
         * MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
         * messagedigest.update(byteBuffer); String md5Str = bufferToHex(messagedigest.digest());
         * messagedigest.reset(); byteBuffer.clear(); in.close(); System.gc(); return md5Str;
         */
        MessageDigest fileMessageDigest = null;
        try {
            fileMessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (null == fileMessageDigest) {
            return "";
        }
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[2048];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            fileMessageDigest.update(buffer, 0, numRead);
        }
        fis.close();
        String md5Str = bufferToHex(fileMessageDigest.digest());
        fileMessageDigest.reset();
        System.gc();
        return md5Str;

    }

    public synchronized static String getMD5String(String s) {
        initMessageDigest();
        return getMD5String(s.getBytes());
    }

    public synchronized static String getMD5String(byte[] bytes) {
        initMessageDigest();
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
                return bufferToHex(bytes, 0, bytes.length);
//        BigInteger bi = new BigInteger(bytes).abs();
//        return bi.toString(36);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public synchronized static boolean checkPassword(String password, String md5PwdStr) {
        initMessageDigest();
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    // public static void main(String[] args) throws IOException {
    // long begin = System.currentTimeMillis();
    //
    // File big = new File("D:\\temp\\jre-7u11-linux-i586.tar.gz");
    // String md5 = getFileMD5String(big);
    //
    // long end = System.currentTimeMillis();
    // System.out.println("md5:" + md5);
    // System.out.println("time:" + ((end - begin) / 1000) + "s");
    //
    // }


}
