package com.gzs.learn.bootstrap.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class LatchTest {

    @Test
    public void testCountDownLatch() throws InterruptedException, ExecutionException {
        int count = 10;
        CountDownLatch processLatch = new CountDownLatch(count);
        CountDownLatch startLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
        for (int i = 0; i < count; i++) {
            int start = i * 10;
            int end = (i + 1) * 10 - 1;
            ComputeClass call = new ComputeClass(start, end, startLatch, processLatch);
            completionService.submit(call);
        }
        startLatch.countDown();
        processLatch.await();
        int result = 0;
        for (int i = 0; i < count; i++) {
            Future<Integer> future = completionService.take();
            result += future.get().intValue();
        }
        System.out.println(result);
    }

    @Test
    public void testCyclicBarrier() {
        int count = 10;
        int total = 10;
        CyclicBarrierClass cyclicBarrierClass = new CyclicBarrierClass(count);
        for (int i = 0; i < total; i++) {
            cyclicBarrierClass.doWork(() -> {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if ((i + 1) % count == 0) {
                cyclicBarrierClass.waitForNex();
            }
        }
    }
}

class ComputeClass implements Callable<Integer> {
    private int start;
    private int end;
    private CountDownLatch startLatch;
    private CountDownLatch processLatch;

    public ComputeClass(int start, int end, CountDownLatch startLatch, CountDownLatch processLatch) {
        this.start = start;
        this.end = end;
        this.startLatch = startLatch;
        this.processLatch = processLatch;
    }

    @Override
    public Integer call() throws Exception {
        startLatch.await();
        int result = 0;
        for (int i = start; i <= end; i++) {
            result += i;
        }
        processLatch.countDown();
        return result;
    }
}

class CyclicBarrierClass {
    private CyclicBarrier barrier;

    public CyclicBarrierClass(int count) {
        barrier = new CyclicBarrier(count + 1);
    }

    public void doWork(Runnable r) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                r.run();
                try {
                    reportIndex(barrier.await());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void waitForNex() {
        try {
            reportIndex(barrier.await());
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void reportIndex(int index) {
        if (index / 3 == 1) {
            System.out.println("left 30%");
        } else if (index == 0) {
            System.out.println("complete!");
        }
    }

}