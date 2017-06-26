package com.example.admin.mydemo.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.admin.mydemo.R;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class DetailActivity extends AppCompatActivity {
    static public DetailActivity mSingleton = null;
    static public WebView mWebView = null;
    static public String mJavaScript = null;
    private String url;
    private Intent intent;
    public static Context webViewContext;
    private String loading;


    private class JsInterface {

        @JavascriptInterface
        public void sendId(final String msg) {
        }

        @JavascriptInterface
        public void getBusinessType(String msg) {
        }

        @JavascriptInterface
        public String getPhoneNumberTurnToDial() {
            return null;
        }
    }


    private void privateCallDial(final String msg) {
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        webViewContext = this;
        intent = getIntent();
        url = intent.getStringExtra("URL");
        loading = intent.getStringExtra("loading");
        if (loading != null) {
            url = loading;
        }
        initViewAndData();
    }

    private void initViewAndData() {
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    mWebView.loadUrl(mJavaScript);
                } else if (msg.what == 1) {
                    mSingleton.finish();
                }
            }
        };
        mSingleton = this;
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.addJavascriptInterface(new JsInterface(), "AndroidWebView");
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setCacheMode(LOAD_NO_CACHE);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.getSettings().setCacheMode(LOAD_NO_CACHE);
        mWebView.setWebChromeClient(new WebChromeClient());


        if (null == url) {
            url = "http://www.baidu.com";
        }
        String userid = "";
        String connid = "";
        String agentid = "";

        mWebView.loadUrl(url);

    }


    private Rect rect;

    @Override
    public void onBackPressed() {
        if (null != mWebView && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        intent = null;
        url = null;
        loading = null;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        if (hasFocus) {
            mWebView.loadUrl(mJavaScript);
        }
    }

    static public Handler mHandler = null;

    static public void execJavaScript(String javaScript) {
        DetailActivity.mJavaScript = javaScript;
        if (DetailActivity.mSingleton != null) {
            DetailActivity.mHandler.sendEmptyMessageDelayed(0, 0);
        }
    }
}
