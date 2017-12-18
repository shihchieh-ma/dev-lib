package cn.majes.dev_lib_app.net;

import cn.majes.dev_lib_app.entity.PicEntity;
import cn.majes.dev_lib_app.entity.ProEntity;
import cn.majes.dev_lib_app.entity.EverEntity;
import cn.majes.dev_lib_app.entity.StoryEntity;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author majes 
 */

public interface OtherService {


    @GET("?as=A115C8457F69B85&cp=585F294B8845EE1&_signature=l")
    Flowable<PicEntity> getPhotoArticle(
            @Query("category") String category,
            @Query("max_behot_time") String time);

    @GET("?as=A115C8457F69B85&cp=585F294B8845EE1&_signature=l")
    Flowable<ProEntity> getOldArticle(
            @Query("category") String category,
            @Query("max_behot_time") String time);

    @GET("?as=A115C8457F69B85&cp=585F294B8845EE1&_signature=l")
    Flowable<EverEntity> getNotifyArticle(
            @Query("category") String category,
            @Query("max_behot_time") String time);

    @GET("?as=A115C8457F69B85&cp=585F294B8845EE1&_signature=l")
    Flowable<StoryEntity> getUserArticle(
            @Query("category") String category,
            @Query("max_behot_time") String time);

}
