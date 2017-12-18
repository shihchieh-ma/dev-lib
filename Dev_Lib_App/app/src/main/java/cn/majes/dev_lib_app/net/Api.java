package cn.majes.dev_lib_app.net;


import dev.majes.base.net.XApi;

/**
 * @author majes
 */

public class Api {
    public static final String API_BASE_URL = "http://gank.io/api/";
    public static final String API_BASE_URL2 = "http://www.toutiao.com/api/pc/feed/";

    private static ApiService apiService;
    private static OtherService otherService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    apiService = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(ApiService.class);
                }
            }
        }
        return apiService;
    }

    public static OtherService getOtherService() {
        if (otherService == null) {
            synchronized (Api.class) {
                if (otherService == null) {
                    otherService = XApi.getInstance().getRetrofit(API_BASE_URL2, true).create(OtherService.class);
                }
            }
        }
        return otherService;
    }

}
