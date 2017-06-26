package com.example.admin.mydemo.design;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author：Marlborn
 * Email：marlborn@foxmail.com
 */
public class PoolInstance {
    private static PoolInstance       poolInstance;
    private        ThreadPoolExecutor threadPoolExecutor;

    private PoolInstance() {
        //1、自定制线程池,参考的是：AsyncTask的配置方式
        //        createMyPool();
        //2、使用api提供的4中线程池

        createApiPool();
    }

    private void createApiPool() {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    private void createMyPool() {
        //1、核心线程数 cpu个数+1
        int coreSize = Runtime.getRuntime().availableProcessors() + 1;
        //2、最大任务线程数  cpu个数的2倍+1
        int coreMaxSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        //3、超时时长
        long keepAliveTime = 60L;
        //4、超时时长单位

        //5、任务队列

        BlockingQueue blockingQueue = new LinkedBlockingDeque();
        //6、线程工厂


        threadPoolExecutor = new ThreadPoolExecutor(coreSize, coreMaxSize, keepAliveTime,
                TimeUnit.SECONDS, blockingQueue, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {

                return new Thread(runnable);
            }
        });

    }

    public static PoolInstance getPoolInstance() {
        if (poolInstance == null) {
            synchronized (PoolInstance.class) {
                if (poolInstance == null) {
                    poolInstance = new PoolInstance();
                }
            }
        }
        return poolInstance;
    }

    private ThreadPoolExecutor getThreadPoolExecutor() {

        if (threadPoolExecutor == null) {
            threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
        }
        return this.threadPoolExecutor;
    }

    private void _startThread(Runnable runnable) {
        getPoolInstance().getThreadPoolExecutor().execute(runnable);
    }

    //对外提供使用方法


    public void startThread(Runnable runnable){
       _startThread(runnable);
    }
}
