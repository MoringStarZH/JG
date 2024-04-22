package com.example.jg.Utils;

/**
 * @title DeviceUtil
 * @Author: ZKY
 * @CreateTime: 2024-04-02  09:30
 * @Description: TODO
 */
public class DeviceUtil {
    public static boolean  isMobileDevice(String requestHeader){
        /**
         * android : 所有android设备
         * mac os : iphone ipad
         * windows phone:Nokia等windows系统的手机
         */
        String[] deviceArray = new String[]{"android","mac os","windows phone"};
        if(requestHeader == null)
            return false;
        requestHeader = requestHeader.toLowerCase();
        for (String s : deviceArray) {
            if (requestHeader.indexOf(s) > 0) {
                return true;
            }
        }
        return false;
    }
}
