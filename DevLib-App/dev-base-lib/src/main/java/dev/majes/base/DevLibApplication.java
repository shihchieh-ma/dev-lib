package dev.majes.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;


import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.FunGameHitBlockHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dev.majes.base.database.DaoManager;
import dev.majes.base.log.Log;
import dev.majes.base.net.NetError;
import dev.majes.base.net.NetProvider;
import dev.majes.base.net.RequestHandler;
import dev.majes.base.net.XApi;
import dev.majes.base.utils.DPXUtils;
import dev.majes.base.utils.ThreadPoolUtils;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import ren.yale.android.cachewebviewlib.CacheWebView;

/**
 * @author majes
 * @date 12/12/17.
 */

public class DevLibApplication extends Application {

    private static Context context;
    private static final String CACHE_NAME = "wv_cache_path";

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

    @Override
    public void onCreate() {
        super.onCreate();
        DevLibApplication.context = this;

        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                //初始化数据库和Log设置
                DaoManager.initDaoManager();
                ApplicationInfo info = getApplicationInfo();
                final boolean isDebugVersion = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
                Log.init("Dev-Lib", isDebugVersion);
                DaoManager.getDaoManager().setDebug(isDebugVersion);

                XApi.registerProvider(new NetProvider() {

                    @Override
                    public Interceptor[] configInterceptors() {
                        return new Interceptor[0];
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
                        return 0;
                    }

                    @Override
                    public long configReadTimeoutMills() {
                        return 0;
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
                });

                File cacheFile = new File(context.getCacheDir(),CACHE_NAME);
                CacheWebView.getCacheConfig().init(context,cacheFile.getAbsolutePath(),1024*1024*100,1024*1024*10)
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
