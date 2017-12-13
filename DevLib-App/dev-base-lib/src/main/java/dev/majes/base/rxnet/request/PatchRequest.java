package dev.majes.base.rxnet.request;


import java.util.Map;

import dev.majes.base.rxnet.api.RetrofitAPI;
import dev.majes.base.rxnet.base.RetrofitClient;

/**
 * Instance
 * Created by D on 2017/10/24.
 */
public class PatchRequest extends HttpRequest<PatchRequest> {

    public PatchRequest(String url) {
        super(url);
    }

    public PatchRequest(String url, Map<String, String> params) {
        super(url, params);
    }

    @Override
    protected void init() {
        observable = RetrofitClient.getInstance().create(RetrofitAPI.class).patch(url, params);
    }

    /**
     * New
     */
    public static class PatchRequestF extends HttpRequestF<PatchRequestF> {

        public PatchRequestF(String url) {
            super(url);
        }

        public PatchRequestF(String url, Map<String, String> params) {
            super(url, params);
        }

        @Override
        protected void init() {
            observable = RetrofitClient.getRetrofit(config).create(RetrofitAPI.class).patch(url, params);
        }
    }
}
