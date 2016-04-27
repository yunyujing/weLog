//package com.zzia.graduation.common.mobile;
//
//import android.content.Context;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.os.Build;
//import android.telephony.TelephonyManager;
//import android.text.format.Formatter;
//import android.util.DisplayMetrics;
//
//import com.zzia.graduation.common.util.StringUtils;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.lang.reflect.Method;
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.util.Enumeration;
//
//public class DeviceInformation {
//    @SuppressWarnings("incomplete-switch")
//    public static String getInformation(Context context, String infoName) {
//        String value = "";
//        String str1 = null;
//        String str2 = null;
//        String[] arrayOfString = null;
//        FileReader fr = null;
//        BufferedReader localBufferedReader = null;
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        switch (infoName) {
//        // case PHONE_KTV_VERSION:
//        // try {
//        // PackageInfo packageInfo =
//        // MyApplication.getContext().getPackageManager().getPackageInfo("com.amusic.media",
//        // PackageManager.GET_CONFIGURATIONS);
//        // value = packageInfo.versionName;
//        // } catch (NameNotFoundException e) {
//        // e.printStackTrace();
//        // }
//        // break;
//            case InfoName.CPU_MODEL:
//                str1 = "/proc/cpuinfo";
//                try {
//                    fr = new FileReader(str1);
//                    localBufferedReader = new BufferedReader(fr, 8192);
//                    str2 = localBufferedReader.readLine();
//                    if (StringUtils.isEmpty(str2)) {
//                        arrayOfString = str2.split("\\s+");
//                        for (int i = 2; i < arrayOfString.length; i++) {
//                            value += arrayOfString[i] + " ";
//                        }
//                    }
//                } catch (IOException e) {} finally {
//                    try {
//                        if (localBufferedReader != null) {
//                            localBufferedReader.close();
//                        }
//                        if (fr != null) {
//                            fr.close();
//                        }
//                    } catch (Exception e) {
//
//                    }
//                }
//                break;
//            case InfoName.CPU_MAX_FREQUENCY:
//                ProcessBuilder cmd;
//                try {
//                    String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
//                    cmd = new ProcessBuilder(args);
//                    Process process = cmd.start();
//                    InputStream in = process.getInputStream();
//                    byte[] re = new byte[24];
//                    while (in.read(re) != -1) {
//                        value = value + new String(re);
//                    }
//                    in.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    value = "N/A";
//                }
//                value.trim();
//                break;
//            case InfoName.MEMORY_TOTAL:
//                str1 = "/proc/meminfo";
//                try {
//                    fr = new FileReader(str1);
//                    localBufferedReader = new BufferedReader(fr);
//                    str2 = localBufferedReader.readLine();
//                    if (!StringUtils.isEmpty(str2)) {
//                        arrayOfString = str2.split("\\s+");
//                    }
//                    long initial_memory = 0;
//                    initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
//                    value = Formatter.formatFileSize(context, initial_memory);//
//
//                } catch (IOException e) {} finally {
//                    try {
//                        if (localBufferedReader != null) {
//                            localBufferedReader.close();
//                        }
//                        if (fr != null) {
//                            fr.close();
//                        }
//                    } catch (Exception e) {
//
//                    }
//                }
//                break;
//            case InfoName.SCREEN_DENSITYDPI:
//                value = String.valueOf(dm.densityDpi);
//                break;
//            case InfoName.CORE_VERSION:
//                Process process = null;
//                try {
//                    process = Runtime.getRuntime().exec("cat /proc/version");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (null == process) {
//                    return "";
//                }
//                InputStream outs = process.getInputStream();
//                InputStreamReader isrout = new InputStreamReader(outs);
//                BufferedReader brout = new BufferedReader(isrout, 8 * 1024);
//
//                String result = "";
//                String line;
//                try {
//                    while ((line = brout.readLine()) != null) {
//                        result += line;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (!StringUtils.isEmpty(result)) {
//                    String Keyword = "version ";
//                    int index = result.indexOf(Keyword);
//                    line = result.substring(index + Keyword.length());
//                    index = line.indexOf(" ");
//                    value = line.substring(0, index);
//                }
//                break;
//            case InfoName.BASE_VERSION:
//                try {
//                    Class<?> cl = Class.forName("android.os.SystemProperties");
//                    Object invoker = cl.newInstance();
//                    Method m = cl.getMethod("get", String.class, String.class);
//                    Object obj = m.invoke(invoker, "gsm.version.baseband", "no message");
//                    value = obj.toString();
//                } catch (Exception e) {}
//                break;
//
//
//
//            case InfoName.DEVICE_TYPE:
//                value = "android";
//                break;
//            case InfoName.DEVICE_MODEL:
//                value = Build.MODEL;
//                break;
//            case InfoName.DEVICE_UUID:
//                value = DeviceInfoManager.getDeviceId(context);
//                break;
//            case InfoName.WIDTH_PX:
//                value = String.valueOf(dm.widthPixels);
//                break;
//            case InfoName.HEIGHT_PX:
//                value = String.valueOf(dm.heightPixels);
//                break;
//            case InfoName.OS_VERSION:
//                value = Build.VERSION.RELEASE;
//                break;
//            case InfoName.SDK_VERSION:
//                value = String.valueOf(Build.VERSION.SDK_INT);
//                break;
//            case InfoName.BUILD_VERSION:
//                value = Build.VERSION.INCREMENTAL;
//                break;
//            case InfoName.HTTP_AGENT:
//                value = System.getProperty("http.agent");
//                break;
//            case InfoName.SIM_COUNTRY:
//                TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//                value = telManager.getSimCountryIso();
//                break;
//            case InfoName.SIM_IMSI:
//                try {
//                    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//                    value = tm.getSubscriberId();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//                break;
//            case InfoName.PHONE_NUMBER:
//                TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//                value = phoneMgr.getLine1Number();
//                break;
//            case InfoName.MAC_ADDRESS:
//                try {
//                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                    if (wifiInfo.getMacAddress() != null) {
//                        value = wifiInfo.getMacAddress();
//                    }
//                } catch (Exception e) {}
//                break;
//            case InfoName.USER_REGION:
//                value = System.getProperty("user.region");
//                break;
//            case InfoName.USER_LANGUAGE:
//                value = System.getProperty("user.language");
//                break;
//            case InfoName.LOCAL_IP:
//                try {
//                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//                        NetworkInterface intf = en.nextElement();
//                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//                            InetAddress inetAddress = enumIpAddr.nextElement();
//                            if (!inetAddress.isLoopbackAddress()) {
//                                value = inetAddress.getHostAddress().toString();
//                            }
//                        }
//                    }
//                } catch (SocketException ex) {}
//                break;
//        }
//
//        return value;
//    }
//
////    public enum InfoName {
////        PHONE_KTV_VERSION("softVersion"),
////        CPU_MODEL("cpuModel"),
////        CPU_MAX_FREQUENCY("cpuClk"),
////        MEMORY_TOTAL("memSize"),
////        SCREEN_DENSITYDPI("windowDensityDpi"),
////        SCREEN_RESOLUTION("windowSize"),
////        CORE_VERSION("coreVersion"),
////        BASE_VERSION("baseVersion"),
////
////
////        DEVICE_TYPE("device_type"),
////        DEVICE_MODEL("device_model"),
////        DEVICE_UUID("device_uuid"),
////        WIDTH_PX("width_px"),
////        HEIGHT_PX("height_px"),
////        OS_VERSION("os_version"),
////        SDK_VERSION("sdk_version"),
////        BUILD_VERSION("build_version"),
////        HTTP_AGENT("http_agent"),
////        SIM_COUNTRY("sim_country"),
////        SIM_IMSI("sim_imsi"),
////        MAC_ADDRESS("mac_address"),
////        USER_REGION("user_region"),
////        USER_LANGUAGE("user_language"),
////        LOCAL_IP("local_ip"), PHONE_NUMBER("phone_number");
////
////
////        private String name;
////
////        InfoName(String name) {
////            this.name = name;
////        }
////
////        @Override
////        public String toString() {
////            return name;
////        }
////    }
//
//    public static class InfoName {
//        public static final String PHONE_KTV_VERSION = "softVersion";
//        public static final String CPU_MODEL = "cpuModel";
//        public static final String CPU_MAX_FREQUENCY = "cpuClk";
//        public static final String MEMORY_TOTAL = "memSize";
//        public static final String SCREEN_DENSITYDPI = "windowDensityDpi";
//        public static final String SCREEN_RESOLUTION = "windowSize";
//        public static final String CORE_VERSION = "coreVersion";
//        public static final String BASE_VERSION = "baseVersion";
//
//        public static final String DEVICE_TYPE = "device_type";
//        public static final String DEVICE_MODEL = "device_model";
//        public static final String DEVICE_UUID = "device_uuid";
//        public static final String WIDTH_PX = "width_px";
//        public static final String HEIGHT_PX = "height_px";
//        public static final String OS_VERSION = "os_version";
//        public static final String SDK_VERSION = "sdk_version";
//        public static final String BUILD_VERSION = "build_version";
//        public static final String HTTP_AGENT = "http_agent";
//        public static final String SIM_COUNTRY = "sim_country";
//        public static final String SIM_IMSI = "sim_imsi";
//        public static final String MAC_ADDRESS = "mac_address";
//        public static final String USER_REGION = "user_region";
//        public static final String USER_LANGUAGE = "user_language";
//        public static final String LOCAL_IP = "local_ip";
//        public static final String PHONE_NUMBER = "phone_number";
//    }
//
//}
