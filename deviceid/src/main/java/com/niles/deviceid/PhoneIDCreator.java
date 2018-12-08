package com.niles.deviceid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by Niles
 * Date 2018/12/8 18:06
 * Email niulinguo@163.com
 * <p>
 * 特点：
 * 1、不可修改
 * 2、不需要权限
 * 3、卸载后 ID 不变
 * 4、获取 READ_PHONE_STATE 权限后，ID 会变
 */
public class PhoneIDCreator implements DeviceIDCreator {

    private final Context mContext;

    public PhoneIDCreator(Context context) {
        mContext = context;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return telephonyManager.getImei();
                } else {
                    //noinspection deprecation
                    return telephonyManager.getDeviceId();
                }
            }
        } catch (Exception ignore) {
        }
        return null;
    }

    @SuppressLint("HardwareIds")
    private static String getAndroidID(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception ignore) {
        }
        return null;
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private static String getSerial() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Build.getSerial();
            } else {
                //noinspection deprecation
                return Build.SERIAL;
            }
        } catch (Exception ignore) {
        }
        return null;
    }

    @SuppressLint("HardwareIds")
    private static String getDeviceUUID() {
        String serial = String.valueOf(getSerial());
        String most = "293;" +
                Build.BOARD.length() % 10 + ";" +
                Build.BRAND.length() % 10 + ";" +
                Build.DEVICE.length() % 10 + ";" +
                Build.HARDWARE.length() % 10 + ";" +
                Build.ID.length() % 10 + ";" +
                Build.MODEL.length() % 10 + ";" +
                serial + ";" +
                Build.PRODUCT.length() % 10;
        return new UUID(most.hashCode(), serial.hashCode()).toString();
    }

    private static byte[] sha1(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.reset();
            messageDigest.update(data.getBytes());
            return messageDigest.digest();
        } catch (Exception ignore) {
        }
        return null;
    }

    private static String byte2Hex(byte[] data) {
        if (data == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (byte d : data) {
            String str = Integer.toHexString(d & 0xff);
            if (str.length() == 1) {
                stringBuilder.append("0");
            }
            stringBuilder.append(str);
        }
        return stringBuilder.toString().toUpperCase();
    }

    @Override
    public String createDeviceID() {
        StringBuilder stringBuilder = new StringBuilder("1234567890");

        String imei = getIMEI(mContext);
        String androidId = getAndroidID(mContext);
        String serial = getSerial();
        String deviceUUID = getDeviceUUID();

        if (!TextUtils.isEmpty(imei)) {
            stringBuilder
                    .append("1:")
                    .append(imei)
                    .append(";");
        }

        if (!TextUtils.isEmpty(androidId)) {
            stringBuilder
                    .append("2:")
                    .append(androidId)
                    .append(";");
        }

        if (!TextUtils.isEmpty(serial)) {
            stringBuilder
                    .append("3:")
                    .append(serial)
                    .append(";");
        }

        if (!TextUtils.isEmpty(deviceUUID)) {
            stringBuilder
                    .append("4:")
                    .append(deviceUUID)
                    .append(";");
        }

        return byte2Hex(sha1(stringBuilder.toString()));
    }
}
