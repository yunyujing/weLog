//package com.zzia.graduation.common.mobile;
//
//import android.content.Context;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.telephony.TelephonyManager;
//
//import com.zzia.graduation.common.store.CommonStoreSpUtil;
//import com.zzia.graduation.common.util.FileUtils;
//import com.zzia.graduation.common.util.StringUtils;
//
//import java.util.Locale;
//import java.util.TreeMap;
//
///**
// * 获取deviceId 需要检验deviceId的唯一性，保证获取的deviceId未被屏蔽，如果未被屏蔽的情况下获得null就回传null
// * 保证MAC地址作为deviceId的一部分，如果两者不能同时满足就给出用户提示
// */
//public class DeviceInfoManager {
//
//    public static String getDeviceId(Context context) {
//        CommonStoreSpUtil.init(context);
//        String mDeviceId = CommonStoreSpUtil.getValue(CommonStoreSpUtil.device_id, "");
//        if (validOfDeviceId(mDeviceId)) {
//            FileUtils.writeFileSdcard(FileUtils.CACHEDIR_COMMON + "deviceInfo.data", mDeviceId, false);
//            return mDeviceId;
//        } else {
//            mDeviceId = FileUtils.readFileContent(FileUtils.CACHEDIR_COMMON + "deviceInfo.data");
//            if (validOfDeviceId(mDeviceId)) {
//                CommonStoreSpUtil.setValue(CommonStoreSpUtil.device_id, mDeviceId);
//                return mDeviceId;
//            }
//        }
//        for (int i = 0; i < 3; i++) {
//            mDeviceId = buildUuid(context);
//            if (validOfDeviceId(mDeviceId)) {
//                CommonStoreSpUtil.setValue(CommonStoreSpUtil.device_id, mDeviceId);
//                FileUtils.writeFileSdcard(FileUtils.CACHEDIR_COMMON + "deviceInfo.data", mDeviceId, false);
//                break;
//            }
//        }
//        return mDeviceId;
//    }
//
//    private static String buildUuid(Context context) {
//        String devId = null;
//        try {
//            TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            devId = telManager.getDeviceId();
//        } catch (SecurityException e) {} catch (Exception e) {}
//        String macAddr = null;
//        try {
//            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = manager.getConnectionInfo();
//            // 替换成下划线防止转义
//            if (info != null) {// 当没有连接WIFI时，可能为NULL
//                if (!StringUtils.isEmpty(info.getMacAddress())) {
//                    macAddr = info.getMacAddress().replaceAll(":", "").toLowerCase(Locale.US);
//                }
//            }
//        } catch (SecurityException e) {} catch (Exception e) {}
//
//        String deviceId = devId + "_" + macAddr;
//        return deviceId;
//    }
//
//    /**
//     * 判断deviceId是否有效
//     *
//     * @param deviceId
//     * @return
//     */
//    public static boolean validOfDeviceId(String deviceId) {
//        if (StringUtils.isEmpty(deviceId) || !deviceId.contains("_")) {
//            return false;
//        }
//
//        String[] ids = deviceId.split("_");
//        if (ids == null || ids.length != 2) {
//            return false;
//        }
//
//        for (int i = 0; i < 2; i++) {
//            if (StringUtils.isEmpty(ids[i]) || ids[i].equals("null") || ids[i].matches("0+")) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public static boolean equalsDeviceId(String deviceId1, String deviceId2) {
//        if ((!StringUtils.isEmpty(deviceId1) && deviceId1.contains("_")) && (!StringUtils.isEmpty(deviceId2) && deviceId2.contains("_"))) {
//            String[] deviceId1s = deviceId1.split("_");
//            String[] deviceId2s = deviceId2.split("_");
//            if (deviceId1s[0].equals(deviceId2s[0]) || deviceId1s[1].equals(deviceId2s[1])) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static TreeMap<String, String> getDeviceInfo(Context context) {
//        TreeMap<String, String> map = new TreeMap<String, String>();
//        map.put("device_type", DeviceInformation.getInformation(context, DeviceInformation.InfoName.DEVICE_TYPE));
//        map.put("device_model", DeviceInformation.getInformation(context, DeviceInformation.InfoName.DEVICE_MODEL));
//        map.put("device_uuid", DeviceInformation.getInformation(context, DeviceInformation.InfoName.DEVICE_UUID));
//        map.put("width_px", DeviceInformation.getInformation(context, DeviceInformation.InfoName.WIDTH_PX));
//        map.put("height_px", DeviceInformation.getInformation(context, DeviceInformation.InfoName.HEIGHT_PX));
//        map.put("os_version", DeviceInformation.getInformation(context, DeviceInformation.InfoName.OS_VERSION));
//        map.put("sdk_version", DeviceInformation.getInformation(context, DeviceInformation.InfoName.SDK_VERSION));
//        map.put("build_version", DeviceInformation.getInformation(context, DeviceInformation.InfoName.DEVICE_TYPE));
//        map.put("http_agent", DeviceInformation.getInformation(context, DeviceInformation.InfoName.HTTP_AGENT));
//        map.put("sim_country", DeviceInformation.getInformation(context, DeviceInformation.InfoName.SIM_COUNTRY));
//        map.put("sim_imsi", DeviceInformation.getInformation(context, DeviceInformation.InfoName.SIM_IMSI));
//        map.put("mobile", DeviceInformation.getInformation(context, DeviceInformation.InfoName.PHONE_NUMBER));
//        map.put("mac_address", DeviceInformation.getInformation(context, DeviceInformation.InfoName.MAC_ADDRESS));
//        map.put("user_region", DeviceInformation.getInformation(context, DeviceInformation.InfoName.USER_REGION));
//        map.put("user_language", DeviceInformation.getInformation(context, DeviceInformation.InfoName.USER_LANGUAGE));
//        map.put("local_ip", DeviceInformation.getInformation(context, DeviceInformation.InfoName.LOCAL_IP));
//        return map;
//    }
//
//    public static boolean isNeedSenDeviceInfo() {
//        return CommonStoreSpUtil.getValue(CommonStoreSpUtil.is_send_device_info, true);
//    }
//
//    public static void setSendDeviceInfoSuccess() {
//        CommonStoreSpUtil.setValue(CommonStoreSpUtil.is_send_device_info, false);
//    }
//
//    // public static void sendDeviceInfo(Context context){
//    /*
//     * if (!NetworkUtils.isConnected(context)) { return ; } if
//     * (!CommonStoreSpUtil.getValue(CommonStoreSpUtil.is_send_device_info, true)) { return; }
//     * TreeMap<String, String> map = new TreeMap<String, String>();
//     * map.put("device_type",DeviceInformation.getInformation(context, InfoName.DEVICE_TYPE));
//     * map.put("device_model",DeviceInformation.getInformation (context,InfoName.DEVICE_MODEL));
//     * map.put("device_uuid",DeviceInformation. getInformation(context,InfoName.DEVICE_UUID));
//     * map.put("width_px",DeviceInformation .getInformation(context,InfoName.WIDTH_PX));
//     * map.put("height_px",DeviceInformation .getInformation(context,InfoName.HEIGHT_PX));
//     * map.put("os_version",DeviceInformation .getInformation(context,InfoName.OS_VERSION));
//     * map.put("sdk_version",DeviceInformation .getInformation(context,InfoName.SDK_VERSION));
//     * map.put("build_version",DeviceInformation .getInformation(context,InfoName.DEVICE_TYPE));
//     * map.put("http_agent",DeviceInformation .getInformation(context,InfoName.HTTP_AGENT));
//     * map.put("sim_country",DeviceInformation .getInformation(context,InfoName.SIM_COUNTRY));
//     * map.put("sim_imsi",DeviceInformation .getInformation(context,InfoName.SIM_IMSI));
//     * map.put("mac_address",DeviceInformation .getInformation(context,InfoName.MAC_ADDRESS));
//     * map.put("user_region",DeviceInformation .getInformation(context,InfoName.USER_REGION));
//     * map.put("user_language",DeviceInformation .getInformation(context,InfoName.USER_LANGUAGE));
//     * map.put("local_ip",DeviceInformation .getInformation(context,InfoName.LOCAL_IP));
//     */
//
//    /*
//     * HttpTools.requestToParse("aps.regDevice",map,new HttpTools.RequestCallback(){
//     *
//     * @Override public void success(Response response) { if (response.isHasData()) {
//     * if(response.getMap().containKey("retcode")){ String retcode =
//     * response.getMap().getStr("retcode"); if("0".equals(retcode)){
//     * CommonStoreSpUtil.setValue(CommonStoreSpUtil.is_send_device_info, false); } } } } });
//     */
//    // }
//
//}
