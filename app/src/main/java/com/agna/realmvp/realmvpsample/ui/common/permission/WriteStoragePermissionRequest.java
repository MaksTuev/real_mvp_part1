package com.agna.realmvp.realmvpsample.ui.common.permission;

import android.Manifest;

import com.agna.realmvp.realmvpsample.ui.base.permission.PermissionRequest;


public class WriteStoragePermissionRequest extends PermissionRequest {
    @Override
    public String[] getPermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }
}
