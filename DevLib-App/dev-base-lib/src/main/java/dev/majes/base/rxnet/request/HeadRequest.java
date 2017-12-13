package dev.majes.base.rxnet.request;


import java.util.Map;

import dev.majes.base.rxnet.api.RetrofitAPI;
import dev.majes.base.rxnet.base.RetrofitClient;

/**
 * Instance
 * Created by D on 2017/10/24.
 */
public class HeadRequest extends HttpRequest<HeadRequest> {
    protected Map<String, String> params;

    public HeadRequest(String url) {
        super(url);
    }

    public HeadRequest(String url, Map<String, String> params) {
        super(url, params);
    }

    @Override
    protected void init() {
        observable = RetrofitClient.getInstance().create(RetrofitAPI.class).head(url, params);
    }

    /**
     * New
     */
    public static class HeadRequestF extends HttpRequestF<HeadRequestF> {

        public HeadRequestF(String url) {
            super(url);
        }

        public HeadRequestF(String url, Map<String, String> params) {
            super(url, params);
        }

        @Override
        protected void init() {
            observable = RetrofitClient.getRetrofit(config).create(RetrofitAPI.class).head(url, params);
        }
    }
}
