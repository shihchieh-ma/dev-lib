package dev.majes.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;


import java.util.HashMap;
import java.util.Map;

import dev.majes.base.database.DaoManager;
import dev.majes.base.log.Log;
import dev.majes.base.rxnet.CommenHeader;
import dev.majes.base.rxnet.RxNet;
import dev.majes.base.utils.SSLUtil;
import dev.majes.base.utils.ThreadPoolUtils;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author majes
 * @date 12/12/17.
 */

public class DevLibApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        DevLibApplication.context = this;
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put(CommenHeader.platform, "Android");
                    headers.put(CommenHeader.app_version, "v1.0.0");
                //初始化数据库和Log设置
                DaoManager.initDaoManager();
                ApplicationInfo info = getApplicationInfo();
                boolean isDebugVersion = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
                Log.init("Dev-Lib", isDebugVersion);
                DaoManager.getDaoManager().setDebug(isDebugVersion);
                //全局配置
                RxNet.init()
               // .baseUrl(API.API_BASE)
                /**
                 * 添加token等动态请求头方法
                 * 1: Override HeadersInterceptor.intercept()
                 * 2: addInterceptor()
                 */
                .headers(headers)
                .connectTimeout(10 * 1000)
                .readTimeout(10 * 1000)
                .writeTimeout(10 * 1000)
                .retryCount(3)
                .retryDelayMillis(2 * 1000)
                .sslSocketFactory(SSLUtil.getSslSocketFactory(null, null, null))
                .setLog("RetrofitLog Back = ", HttpLoggingInterceptor.Level.BODY)
                .build();
            }
        });

    }


    public static Context getDevLibApplicationContext() {
        if (null == context) {
            throw new NullPointerException("context is null , " +
                    "did you forget init DevLibApplication?");
        }
        return context;
    }
}
