package com.example.admin.mydemo.design;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class Commen {
    public Commen() {
    }

    public static String[] TITLELIST = {"ViewPager1", "ViewPager2", "ViewPager3",
                                        "ViewPager4", "ViewPager5", "ViewPager6",
                                        "ViewPager7", "ViewPager8"};
    public static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(3000, TimeUnit.MILLISECONDS).
            readTimeout(6000, TimeUnit.MILLISECONDS).
            writeTimeout(6000, TimeUnit.MILLISECONDS).build();

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //返回值 -1：没有网络  1：WIFI网络2：wap网络3：net网络
    public static int GetNetype(Context context) {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = 3;
            } else {
                netType = 2;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }


}
