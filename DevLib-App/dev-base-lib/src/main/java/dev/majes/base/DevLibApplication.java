package dev.majes.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;


import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;
import java.io.IOException;

import dev.majes.base.database.DaoManager;
import dev.majes.base.log.Log;
import dev.majes.base.net.NetError;
import dev.majes.base.net.NetProvider;
import dev.majes.base.net.RequestHandler;
import dev.majes.base.net.XApi;
import dev.majes.base.utils.ThreadPoolUtils;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ren.yale.android.cachewebviewlib.CacheWebView;

/**
 * @author majes
 * @date 12/12/17.
 */

public class DevLibApplication extends Application {

    private static Context context;
    private static final String CACHE_WEBVIEW = "wv_cache_path";
    private static final String CACHE_NET = "retrofit_cache_path";
    private static Cache cache;
    private static final long CONNECTTIMEOUT = 30 * 1000l;
    private static final long READTIMEOUT = 30 * 1000l;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(android.R.color.holo_blue_light, android.R.color.white);
//                return new PhoenixHeader(context);
                return new WaveSwipeHeader(context);
                //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new BallPulseFooter(context);
//                return new ClassicsFooter(context).setDrawableSize(DPXUtils.dip2px(context,20));
            }
        });
    }

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        DevLibApplication application = (DevLibApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private Interceptor[] ir;

    @Override
    public void onCreate() {
        super.onCreate();
        DevLibApplication.context = this;
        LeakCanary.install(this);
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                //初始化数据库和Log设置
                DaoManager.initDaoManager();
                ApplicationInfo info = getApplicationInfo();
                final boolean isDebugVersion = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
                Log.init("Dev-Lib", isDebugVersion);
                DaoManager.getDaoManager().setDebug(isDebugVersion);
                //设置缓存路径
                File httpCacheDirectory = new File(context.getCacheDir(), CACHE_NET);
                //设置缓存 50M
                cache = new Cache(httpCacheDirectory, 50 * 1024 * 1024);
                Interceptor interceptor = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.d("request=" + request);
                        Response response = chain.proceed(request);
                        Log.d("response=" + response);

                        String cacheControl = request.cacheControl().toString();
                        if (TextUtils.isEmpty(cacheControl)) {
                            cacheControl = "public, max-age=60";
                        }
                        return response.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma")
                                .build();
                    }
                };

                ir = new Interceptor[]{interceptor};
                XApi.registerProvider(new NetProvider() {

                    @Override
                    public Interceptor[] configInterceptors() {
//                        return new Interceptor[0];
                        return ir;
                    }

                    @Override
                    public void configHttps(OkHttpClient.Builder builder) {

                    }

                    @Override
                    public CookieJar configCookie() {
                        return null;
                    }

                    @Override
                    public RequestHandler configHandler() {
                        return null;
                    }

                    @Override
                    public long configConnectTimeoutMills() {
                        return CONNECTTIMEOUT;
                    }

                    @Override
                    public long configReadTimeoutMills() {
                        return READTIMEOUT;
                    }

                    @Override
                    public boolean configLogEnable() {
                        return true;
                    }

                    @Override
                    public boolean handleError(NetError error) {
                        return false;
                    }

                    @Override
                    public boolean dispatchProgressEnable() {
                        return false;
                    }

                    @Override
                    public Cache configCache() {
//                        return null;
                        return cache;
                    }
                });

                File cacheFile = new File(context.getCacheDir(), CACHE_WEBVIEW);
                CacheWebView.getCacheConfig().init(context, cacheFile.getAbsolutePath(), 1024 * 1024 * 100, 1024 * 1024 * 10)
                        .enableDebug(isDebugVersion);
            }
        });

    }


    public static Context getDevLibApplicationContext() {
        if (null == context) {
            throw new NullPointerException("context is null , " +
                    "did you forget init DevLibApplication?");
        }
        return context;
    }

}
