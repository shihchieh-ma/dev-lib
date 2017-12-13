package dev.majes.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.security.Permissions;

import dev.majes.base.database.DaoManager;
import dev.majes.base.log.Log;
import okhttp3.Cache;

/**
 * @author majes
 * @date 12/12/17.
 */

public class DevLibApplication extends Application {

    private static Context context;
    private static Cache cache;
    @Override
    public void onCreate() {
        super.onCreate();
        DevLibApplication.context = this;
        //初始化数据库和Log设置
        DaoManager.initDaoManager();
        ApplicationInfo info = getApplicationInfo();
        boolean isDebugVersion = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.init("Dev-Lib", isDebugVersion);
        DaoManager.getDaoManager().setDebug(isDebugVersion);
    }

    public static Cache getNetCache(){
        if (null == cache){
            synchronized ("getCache"){
                if (null == cache){
                    cache = new Cache(context.getCacheDir(), 50 * 1024 * 1024);
                }
            }
        }
        return cache;
    }

    public static Context getDevLibApplicationContext() {
        if (null == context) {
            throw new NullPointerException("context is null , " +
                    "did you forget init DevLibApplication?");
        }
        return context;
    }
}
