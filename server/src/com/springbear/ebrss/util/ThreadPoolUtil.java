package com.springbear.ebrss.util;

import java.util.concurrent.*;

/**
 * Thread pool service tools class
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 20:47
 */
public class ThreadPoolUtil {
    /**
     * Thread pool service
     */
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            12,
            24,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(512),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * Execute the thread pool task
     *
     * @param task Thread task
     */
    public static void execute(Runnable task) {
        EXECUTOR_SERVICE.execute(task);
    }

    /**
     * Destroy the thread pool
     */
    public static void shutdown() {
        EXECUTOR_SERVICE.shutdown();
    }
}
