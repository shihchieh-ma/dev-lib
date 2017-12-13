package dev.majes.base.rxnet.request;


import java.util.Map;

import dev.majes.base.rxnet.api.RetrofitAPI;
import dev.majes.base.rxnet.base.RetrofitClient;

/**
 * Instance
 * Created by D on 2017/10/24.
 */
public class OptionRequest extends HttpRequest<OptionRequest> {

    public OptionRequest(String url) {
        super(url);
    }

    public OptionRequest(String url, Map<String, String> params) {
        super(url, params);
    }

    @Override
    protected void init() {
        observable = RetrofitClient.getInstance().create(RetrofitAPI.class).options(url, params);
    }

    /**
     * New
     */
    public static class OptionRequestF extends HttpRequestF<OptionRequestF> {

        public OptionRequestF(String url) {
            super(url);
        }

        public OptionRequestF(String url, Map<String, String> params) {
            super(url, params);
        }

        @Override
        protected void init() {
            observable = RetrofitClient.getRetrofit(config).create(RetrofitAPI.class).options(url, params);
        }
    }
}
