package com.niles.deviceidsdk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.niles.permission.IStoragePermission;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Niles
 * Date 2018/12/7 11:20
 * Email niulinguo@163.com
 */
@RuntimePermissions
public abstract class MainPermissionActivity extends AppCompatActivity implements IStoragePermission {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainPermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void requestStorage() {
        MainPermissionActivityPermissionsDispatcher.onStorageRequestedWithPermissionCheck(this);
    }

    @Override
    @NeedsPermission(value = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"})
    public void onStorageRequested() {

    }

    @Override
    @OnShowRationale(value = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"})
    public void showRationaleForStorage(PermissionRequest request) {
        request.proceed();
    }

    @Override
    @OnPermissionDenied(value = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"})
    public void showPermissionDeniedForStorage() {

    }

    @Override
    @OnNeverAskAgain(value = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"})
    public void showNeverAskAgainForStorage() {

    }
}
