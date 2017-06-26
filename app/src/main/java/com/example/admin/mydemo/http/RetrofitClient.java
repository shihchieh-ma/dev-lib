package com.example.admin.mydemo.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marl_Jar on 2017/6/8.
 */

public enum RetrofitClient{
    INSTANCE;

    private final Retrofit retrofit;

    RetrofitClient() {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OKHttpFactory.getOkHttpClient())

                //baseUrl
                .baseUrl(ApiContants.GITHUB_BASEURL)

                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())

                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}