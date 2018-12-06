package com.niles.deviceidsdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.niles.deviceid.DeviceIDUtils;
import com.niles.deviceid.PublicFileUUIDCreator;

public class MainActivity extends AppCompatActivity {

    TextView mDeviceIDView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeviceIDView = findViewById(R.id.tv_device_id);

        DeviceIDUtils.setDeviceIDCreator(new PublicFileUUIDCreator(this));
        mDeviceIDView.setText(DeviceIDUtils.id());
    }
}
