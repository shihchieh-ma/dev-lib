package com.example.admin.mydemo.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：MaJes
 * Email：65218512@qq.com
 * Date: 2017/10/18  16:03
 * Description:
 */

public class PermUtil {
    private Activity mActivity;
    private List<RequestInfo> requestInfos = new ArrayList<>();

    private static PermUtil permUtil;

    /**
     * @param activity 依赖Activity
     */
    private PermUtil(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 获取PermUtil实例
     *
     * @return permUtil
     * <br/> call this method after the PermUtil has been install {@link #install(Activity)}
     */
    public static PermUtil getInstance() {
        return permUtil;
    }

    /**
     * 将工具安装到依赖Activity
     *
     * @param activity 依赖Activity
     * @return PermUtil
     */
    public static PermUtil install(Activity activity) {
        if (permUtil == null) {
            permUtil = new PermUtil(activity);
        }
        return permUtil;
    }

    /**
     * 检查并申请权限
     *
     * @param permission
     * @param requestCode
     * @param callback
     */
    public void checkAndRequestPermission(String permission, final int requestCode, OnRequestPermissionCallback callback) {
        if (permission == null) {
            return;
        }
        // 检查权限是否已经申请
        if (ActivityCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_GRANTED) {
            if (callback != null) callback.onCheckedAlreadyGranted(permission, requestCode);
            return;
        }

        RequestInfo requestInfo = new RequestInfo(requestCode, permission, callback);
        if (!requestInfos.contains(requestInfo)) {
            requestInfos.add(requestInfo);
            scheduleNext(requestInfo);
        }

    }

    /**
     * 请求多个权限
     *
     * @param permissions
     * @param requestCode
     * @param callback
     */
    public void checkAndRequestPermissions(String[] permissions, int requestCode, OnRequestPermissionCallback callback) {
        if (permissions == null) {
            return;
        }

        if (checkGrantedPermissions(permissions)) {
            if (callback != null) callback.onCheckedAlreadyGranted(permissions);
            return;
        }

        // 将权限集合变成一个String方便处理
        String permsArray = list2String(getDeniedPermissions(permissions));
        RequestInfo requestInfo = new RequestInfo(requestCode, permsArray, callback);
        if (!requestInfos.contains(requestInfo)) {
            requestInfos.add(requestInfo);
            scheduleNext(requestInfo);
        }

    }

    /**
     * 依次申请权限
     *
     * @param requestInfo
     */
    private void scheduleNext(RequestInfo requestInfo) {
        // 判断请求的权限是否在头部
        if (requestInfos.size() <= 0 || !isFirstElement(requestInfo)) {
            return;
        }

        if (requestInfo.permission.contains(",")) {  // 请求多个权限
            List<String> deniedPermissions = getDeniedPermissions(requestInfo.permission.split(","));
            String[] perms = list2Array(deniedPermissions); // 只获取还未授权的权限
            ActivityCompat.requestPermissions(mActivity, perms, requestInfo.requestCode);
        } else {  // 请求一个权限
            ActivityCompat.requestPermissions(mActivity, new String[]{requestInfo.permission}, requestInfo.requestCode);
        }
    }

    /**
     * 是否是第一个元素
     *
     * @param requestInfo
     */
    public boolean isFirstElement(RequestInfo requestInfo) {
        if (requestInfos.size() <= 0 || requestInfo == null) {
            return false;
        }
        return requestInfos.get(0).equals(requestInfo);
    }


    /**
     * list<string> 转 string[]
     *
     * @param list
     * @return
     */
    public String[] list2Array(List<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    /**
     * list转string，以，分隔
     *
     * @param list
     * @return
     */
    public String list2String(List<String> list) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size()) {
                sb.append(",");
            }
        }

        return sb.toString();
    }


    /**
     * 处理权限请求
     * <br/>do not call this method directly
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void dealRequestPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestInfos.size() > 0) {
            RequestInfo requestInfo = requestInfos.get(0);
            if (requestInfo == null || requestInfo.requestCode != requestCode) return;
            OnRequestPermissionCallback callback = requestInfo.callback;
            if (callback == null) {  //没有callback则直接准备申请下一个权限
                prepareScheduleNext(requestInfo);
                return;
            }

            if (grantResults.length > 0) {
                List<String> grantedPerms = new ArrayList<>();  // 授权成功的权限集合
                List<String> deniedPerms = new ArrayList<>();  // 授权失败的权限集合

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        grantedPerms.add(permissions[i]);
                    } else {
                        deniedPerms.add(permissions[i]);
                    }
                }

                // 发布权限结果
                if (grantedPerms.size() > 0)
                    callback.onSuccess(requestCode, list2Array(grantedPerms));
                if (deniedPerms.size() > 0)
                    callback.onFailed(requestCode, list2Array(deniedPerms));
                prepareScheduleNext(requestInfo);

            }
        }
    }

    /**
     * 为下一次申请权限做准备
     *
     * @param requestInfo
     */

    private void prepareScheduleNext(RequestInfo requestInfo) {
        // 移除已经申请过的信息
        requestInfos.remove(requestInfo);
        if (requestInfos.size() > 0) {
            RequestInfo nextInfo = requestInfos.get(0);
            if (nextInfo.permission != null) {
                scheduleNext(nextInfo);
            }
        }
    }

    /**
     * 检查所给权限组是否都已经授权了
     *
     * @param permissions
     * @return
     */
    public boolean checkGrantedPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取还没有授权的权限
     *
     * @param permissions
     * @return
     */
    public List<String> getDeniedPermissions(String[] permissions) {
        if (permissions == null) return null;
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }

        return deniedPermissions;
    }


    public interface OnRequestPermissionCallback {
        /**
         * 检查发现已经授权
         *
         * @param permission
         * @param requestCode
         */
        void onCheckedAlreadyGranted(String permission, int requestCode);

        void onCheckedAlreadyGranted(String[] permissions);

        /**
         * 当申请权限成功时
         */
        void onSuccess(int requestCode, @NonNull String[] permissions);

        /**
         * 当申请权限失败时
         */
        void onFailed(int requestCode, @NonNull String[] permissions);
    }

    /**
     * 取消安装，防止内存泄漏
     */
    public void uninstall(Activity activity) {
        if (mActivity == activity) {
            requestInfos = null;
            mActivity = null;
            permUtil = null;
        }
    }

    /**
     * 权限请求的信息类
     */
    private class RequestInfo {
        int requestCode;
        String permission;
        OnRequestPermissionCallback callback;

        public RequestInfo() {
        }

        public RequestInfo(int requestCode, String permission, OnRequestPermissionCallback callback) {
            this.requestCode = requestCode;
            this.permission = permission;
            this.callback = callback;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RequestInfo that = (RequestInfo) o;

            return requestCode == that.requestCode && permission.equals(that.permission);
        }

        @Override
        public int hashCode() {
            int result = requestCode;
            result = 31 * result + permission.hashCode();
            result = 31 * result + (callback != null ? callback.hashCode() : 0);
            return result;
        }
    }
}
