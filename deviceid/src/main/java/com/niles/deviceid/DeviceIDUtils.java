package com.niles.deviceid;

/**
 * Created by Niles
 * Date 2018/12/6 21:34
 * Email niulinguo@163.com
 */
public class DeviceIDUtils {

    private static String sId;
    private static DeviceIDCreator sDeviceIDCreator;

    public static void setDeviceIDCreator(DeviceIDCreator deviceIDCreator) {
        sDeviceIDCreator = deviceIDCreator;
    }

    public static String id() {
        if (sId == null) {
            sId = sDeviceIDCreator.createDeviceID();
        }
        return sId;
    }
}
