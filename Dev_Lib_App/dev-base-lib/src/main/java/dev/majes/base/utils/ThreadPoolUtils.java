package dev.majes.base.utils;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工具类
 * @author ：majes
 */

public class ThreadPoolUtils {

    //线程池核心线程数
    private static final int CPU_COUNT =
            Runtime.getRuntime().availableProcessors();
    private static int CORE_POOL_SIZE = CPU_COUNT + 1;

    //线程池最大线程数
    private static int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

    //额外线程空状态生存时间
    private static int KEEP_ALIVE_TIME = 10000;

    /**
     *  阻塞队列 当核心线程都被占用 且阻塞队列已满的情况下 才会开启额外线程。
     */

    private static BlockingQueue workQueue = new ArrayBlockingQueue(10);

    //线程池
    private static ThreadPoolExecutor threadPool;

    private ThreadPoolUtils() {
    }

    //线程工厂
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "myThreadPool thread:" + integer.getAndIncrement());
        }
    };

    static {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, workQueue, threadFactory);
    }

    //执行
    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

    //取消
    public static void cancel(FutureTask futureTask) {
        futureTask.cancel(true);
    }

}
