package dev.majes.base.rxnet.request;


import java.util.Map;

import dev.majes.base.rxnet.api.RetrofitAPI;
import dev.majes.base.rxnet.base.RetrofitClient;

/**
 * Instance
 * Created by D on 2017/10/24.
 */
public class PutRequest extends HttpRequest<PutRequest> {

    public PutRequest(String url) {
        super(url);
    }

    public PutRequest(String url, Map<String, String> params) {
        super(url, params);
    }

    @Override
    protected void init() {
        observable = RetrofitClient.getInstance().create(RetrofitAPI.class).put(url, params);
    }

    /**
     * New
     */
    public static class PutRequestF extends HttpRequestF<PutRequestF> {

        public PutRequestF(String url) {
            super(url);
        }

        public PutRequestF(String url, Map<String, String> params) {
            super(url, params);
        }

        @Override
        protected void init() {
            observable = RetrofitClient.getRetrofit(config).create(RetrofitAPI.class).put(url, params);
        }
    }
}
