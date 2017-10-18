package com.example.admin.mydemo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class MyApplication extends Application {

     //获取到主线程的上下文
     private  static MyApplication mContext           = null;
     //获取到主线程的handler
     private static Handler       mMainThreadHandler = null;
     //获取到主线程的looper
     private static Looper        mMainThreadLooper  = null;
     //获取到主线程
     private static Thread        mMainThead         = null;
     //获取到主线程的id
     private static int mMainTheadId;

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        this.mMainTheadId = android.os.Process.myTid();
    }
    public static MyApplication getApplication() {
                 return mContext;
             }

    public static Handler getMainThreadHandler() {
                     return mMainThreadHandler;
             }

    public static Looper getMainThreadLooper() {
                 return mMainThreadLooper;
             }

    public static Thread getMainThread() {
                 return mMainThead;
             }

    public static int getMainThreadId() {
                 return mMainTheadId;
             }

}
