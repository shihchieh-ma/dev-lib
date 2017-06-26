package com.example.admin.mydemo.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Marl_Jar on 2017/6/8.
 * 实现 Interceptor 接口，做我们自己的处理。
 * 目前使用中，一般用来设置UA、设置缓存策略 、打印Log...
 */

public final class UserAgentInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private final String userAgentHeaderValue;

    public UserAgentInterceptor(String userAgentHeaderValue) {
        this.userAgentHeaderValue = userAgentHeaderValue;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader(USER_AGENT_HEADER_NAME)   //移除先前默认的User-Agent
                .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)  //设置新的User-Agent
                .build();
        return chain.proceed(requestWithUserAgent);
    }
}