package com.example.admin.mydemo.intface;

import android.database.Observable;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Marl_Jar on 2017/6/9.
 */

public interface ApiService {
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String   fileUrl);
}
