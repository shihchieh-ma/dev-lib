package com.example.admin.mydemo.http;

import com.example.admin.mydemo.utils.HttpNetUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Marl_Jar on 2017/6/8.
 */

public class OnOffLineCachedInterceptor implements Interceptor {
    private static final int MAX_AGE = 60;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!HttpNetUtil.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)  //强制使用缓存
                    .build();
        }

        Response response = chain.proceed(request);
        if (HttpNetUtil.isConnected()) {
            int maxAge = MAX_AGE; // 有网络时 设置缓存超时1分钟
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
