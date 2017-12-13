package dev.majes.base.rxnet.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;


import java.io.IOException;

import dev.majes.base.log.Log;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 在线缓存拦截
 */
public class OnlineCacheInterceptor implements Interceptor {
    private String cacheControlValue;

    public OnlineCacheInterceptor() {
        this(60);////默认最大在线缓存时间（秒）
    }

    public OnlineCacheInterceptor(int cacheControlValue) {
        this.cacheControlValue = String.format("max-age=%d", cacheControlValue);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");
        if (TextUtils.isEmpty(cacheControl) || cacheControl.contains("no-store") || cacheControl.contains("no-cache") || cacheControl
                .contains("must-revalidate") || cacheControl.contains("max-age") || cacheControl.contains("max-stale")) {
            Log.d(originalResponse.headers().toString());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, " + cacheControlValue)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse;
        }
    }
}
