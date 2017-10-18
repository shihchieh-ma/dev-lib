package com.example.admin.mydemo.utils;

import android.support.annotation.NonNull;

/**
 * Author：MaJes
 * Email：65218512@qq.com
 * Date: 2017/10/18  16:06
 * Description:
 */

public class OnRequestPermissionCallbackAdapter implements PermUtil.OnRequestPermissionCallback {

    /**
     * {@inheritDoc}
     * @param permission
     * @param requestCode
     */
    @Override
    public void onCheckedAlreadyGranted(String permission, int requestCode) {

    }

    @Override
    public void onCheckedAlreadyGranted(String[] permissions) {

    }

    @Override
    public void onSuccess(int requestCode, @NonNull String[] permissions) {

    }

    @Override
    public void onFailed(int requestCode, @NonNull String[] permissions) {

    }
}
