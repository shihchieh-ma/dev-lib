package cn.majes.dev_lib_app.view.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

import cn.majes.dev_lib_app.R;
import dev.majes.base.log.Log;
import dev.majes.base.mvp.BaseActivity;
import dev.majes.utils.WaitShowUtils;
import ren.yale.android.cachewebviewlib.CacheWebView;
import ren.yale.android.cachewebviewlib.utils.NetworkUtils;

/**
 * @author majes
 * @date 12/16/17.
 */

public class WebViewActivity extends BaseActivity {

    private CacheWebView mWebView;
    public static final String KEY_URL="KEY_URL";
    private String url;

    @Override
    public void initData(Bundle savedInstanceState) {
        WaitShowUtils.getInstance(this).show();
        mWebView = findViewById(R.id.webview);
        url = getIntent().getStringExtra(KEY_URL);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WaitShowUtils.release();
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view,request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view,url);
            }


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                Log.d("shouldInterceptRequest: "+request.getUrl().toString());
                return null;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("shouldInterceptRequest: "+url);
                return null;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        initSettings();
        Log.e(url);
        if (url == null){
            throw new NullPointerException("your url is null!");
        }
        mWebView.loadUrl(url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    private void initSettings(){
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);

        webSettings.setDefaultTextEncodingName("UTF-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(mWebView,true);
        }
        if (NetworkUtils.isConnected(mWebView.getContext()) ){
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        setCachePath();

    }

    private void setCachePath(){

        File cacheFile = new File(mWebView.getContext().getCacheDir(),"appcache_name");
        String path = cacheFile.getAbsolutePath();

        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(path);

    }

}
