package com.example.admin.mydemo.commen;

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

}
