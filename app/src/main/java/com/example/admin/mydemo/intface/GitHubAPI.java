package com.example.admin.mydemo.intface;

import com.example.admin.mydemo.bean.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Marl_Jar on 2017/6/8.
 * 测试
 */

//创建接口，声明GitHub的API
public interface GitHubAPI {
    @GET
    Call<User> getInfo(@Url String queryUrl);

    @GET("users")
    Call<List<User>> getUser(@Header("Authorization") String authorization);

    @GET("users/{user}")
    Call<User> getUserInfo(@Path("user") String user);

    @GET("users")
    Call<List<User>> getUserInfoBySort(@Query("sortby") String sort);

    @POST("add")
    Call<User> addUser(@Body User user);

    @POST("login")
    @FormUrlEncoded
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("register")
    Call<User> registerUser(@Part MultipartBody.Part photo, @Part("username") RequestBody username, @Part("password") RequestBody password);

    @Multipart
    @POST("register")
    Call<User> registerUser(@PartMap Map<String, RequestBody> params);
}