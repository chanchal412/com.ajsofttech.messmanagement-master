package com.project.messmanagement.utils;

import android.content.Context;


import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class CoreTask {
    public void getPermission(Context context) {
        ((TedPermission.Builder) ((TedPermission.Builder) TedPermission.with(context).setPermissionListener(new PermissionListener() {
            public void onPermissionDenied(List<String> list) {
            }

            public void onPermissionGranted() {
            }
        })).setPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")).check();
    }

}
