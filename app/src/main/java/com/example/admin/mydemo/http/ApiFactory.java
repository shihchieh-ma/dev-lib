package com.example.admin.mydemo.http;

import com.example.admin.mydemo.intface.GitHubAPI;

/**
 * Created by Marl_Jar on 2017/6/8.
 */

public enum  ApiFactory {
    INSTANCE;

    private static GitHubAPI gitHubAPI;
    ApiFactory() {
    }

    public static GitHubAPI gitHubAPI() {
        if (gitHubAPI == null) {
            gitHubAPI = RetrofitClient.INSTANCE.getRetrofit().create(GitHubAPI.class);
        }
        return gitHubAPI;
    }

}