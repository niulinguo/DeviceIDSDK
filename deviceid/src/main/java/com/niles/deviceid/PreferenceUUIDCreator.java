package com.niles.deviceid;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

/**
 * Created by Niles
 * Date 2018/12/6 21:37
 * Email niulinguo@163.com
 * <p>
 * 特点：
 * 1、不需要权限
 * 2、不可观察
 * 3、随 App 一起删除
 */
public class PreferenceUUIDCreator implements DeviceIDCreator {

    private final SharedPreferences mSharedPreferences;

    public PreferenceUUIDCreator(Context context) {
        mSharedPreferences = context.getSharedPreferences("deviceId", Context.MODE_PRIVATE);
    }

    @Override
    public String createDeviceID() {
        if (!mSharedPreferences.contains("DEVICE_ID")) {
            mSharedPreferences
                    .edit()
                    .putString("DEVICE_ID", UUID.randomUUID().toString())
                    .apply();
        }
        return mSharedPreferences.getString("DEVICE_ID", null);
    }
}
