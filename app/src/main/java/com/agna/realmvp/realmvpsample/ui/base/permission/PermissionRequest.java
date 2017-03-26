package com.agna.realmvp.realmvpsample.ui.base.permission;


public abstract class PermissionRequest {

    private static final int MAX_REQUEST_CODE = 32768;

    public abstract String[] getPermissions();

    public int getRequestCode(){
        return Math.abs(this.getClass().getCanonicalName().hashCode()) % MAX_REQUEST_CODE;
    }
}
