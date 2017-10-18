package com.example.admin.mydemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.admin.mydemo.MyApplication;

/**
 * Created by Marl_Jar on 2017/6/8.
 */

public class HttpNetUtil {
    /**
     * 判断网络连接是否存在
     */
    public static boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean connected = info != null && info.isConnected();
        return connected;
    }
}
