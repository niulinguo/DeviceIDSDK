package com.niles.deviceid;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Niles
 * Date 2018/12/6 22:19
 * Email niulinguo@163.com
 * <p>
 * 特点：
 * 1、需要存储权限
 * 2、可观察，可修改
 * 3、不随 App 一起删除
 */
public class PublicFileUUIDCreator implements DeviceIDCreator {

    private final File mDeviceIDFile;

    public PublicFileUUIDCreator(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String dirName = context.getString(applicationInfo.labelRes);
        mDeviceIDFile = new File(new File(Environment.getExternalStorageDirectory(), dirName), "device_id.txt");
    }

    @Override
    public String createDeviceID() {
        String deviceId = readFile();
        if (deviceId == null) {
            writeFile(deviceId = UUID.randomUUID().toString());
        }
        return deviceId;
    }

    private void writeFile(String deviceID) {
        if (!mDeviceIDFile.exists()) {
            File dir = mDeviceIDFile.getParentFile();
            if (!dir.exists() && !dir.mkdirs()) {
                throw new RuntimeException("请打开存储权限");
            }
        }

        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new FileWriter(mDeviceIDFile));
            fileWriter.write(deviceID);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("请打开存储权限");
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String readFile() {
        if (!mDeviceIDFile.exists()) {
            return null;
        }

        String deviceID;
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(mDeviceIDFile));
            deviceID = fileReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("请打开存储权限");
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return deviceID;
    }
}
