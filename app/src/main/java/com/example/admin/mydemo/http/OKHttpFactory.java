package com.example.admin.mydemo.http;

import com.example.admin.mydemo.application.MyApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Marl_Jar on 2017/6/8.
 */

public class OKHttpFactory {
    private OkHttpClient client;
    private static final int TIMEOUT_READ = 25;
    private static final int TIMEOUT_CONNECTION = 25;

    //缓存目录
    Cache cache = new Cache(MyApplication.getApplication().getCacheDir(), 10 * 1024 * 1024);

    private OKHttpFactory(){
        client = new OkHttpClient.Builder().cache(cache)
//                .addInterceptor(new LoggingInterceptor()) //打印log
                .addNetworkInterceptor(new LoggingInterceptor())
                .addInterceptor(new UserAgentInterceptor("retrofit"))
                //走缓存，两个都要设置
                .addInterceptor(new OnOffLineCachedInterceptor())
                .addNetworkInterceptor(new OnOffLineCachedInterceptor())
                .retryOnConnectionFailure(true) //失败重连
                //time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }


    public static OkHttpClient getOkHttpClient(){
        return Holder.INSTANCE.client;
    }

    private static class Holder{
        final public static OKHttpFactory INSTANCE = new OKHttpFactory();
    }
}
