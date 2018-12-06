package com.niles.deviceid;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Niles
 * Date 2018/12/6 21:48
 * Email niulinguo@163.com
 * <p>
 * 特点
 * 1、不需要权限
 * 2、可观察，可修改
 * 3、随 App 一起删除
 */
public class FileUUIDCreator implements DeviceIDCreator {

    private final File mDeviceIDFile;

    public FileUUIDCreator(Context context) {
        mDeviceIDFile = new File(context.getExternalCacheDir(), "device_id.txt");
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
        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new FileWriter(mDeviceIDFile));
            fileWriter.write(deviceID);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
        String deviceID = null;
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(mDeviceIDFile));
            deviceID = fileReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
