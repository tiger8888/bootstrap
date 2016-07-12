package com.gzs.learn.bootstrap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestCachedThreadPool {
    @Test
    public void testCachedPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        /*
         * ExecutorService executorService = new ThreadPoolExecutor(10, 200, 60, TimeUnit.SECONDS, new
         * SynchronousQueue<Runnable>());
         */
        /*
         * ExecutorService executorService = new ThreadPoolExecutor(100, 200, 60, TimeUnit.SECONDS, new
         * LinkedBlockingQueue<Runnable>());
         */
        long start = System.currentTimeMillis();
        for (int i = 0; i < 300000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(1 * 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
        long cost = System.currentTimeMillis() - start;
        System.out.println("cost is:" + cost);
    }
}

class SimpleThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}

