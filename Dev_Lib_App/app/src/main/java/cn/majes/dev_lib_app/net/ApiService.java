package cn.majes.dev_lib_app.net;

import cn.majes.dev_lib_app.entity.HotEntity;
import cn.majes.dev_lib_app.entity.NewsEntity;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author majes
 */

public interface ApiService {

    @GET("data/{type}/{number}/{page}")
    Flowable<HotEntity> getGankHot(@Path("type") String type,
                                   @Path("number") int pageSize,
                                   @Path("page") int pageNum);



    @GET("data/{type}/{number}/{page}")
    Flowable<NewsEntity> getGankNews(@Path("type") String type,
                                     @Path("number") int pageSize,
                                     @Path("page") int pageNum);
}
