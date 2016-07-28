package com.gzs.learn.bootstrap.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class ParkUnParkTest {
    @Test
    public void testPark() {
        CountDownLatch latch = new CountDownLatch(1);
        int before = 2, after = 3;
        Thread t1 = new Thread(new ParkInnerThread(Thread.currentThread(), before, after, latch));
        t1.start();
        LockSupport.park();
        System.out.println("exec before");
        latch.countDown();
        LockSupport.park();
        System.out.println("exec after");
    }
}

class ParkInnerThread implements Runnable {
    private Thread thread;
    private int beforePermit;
    private int afterPermitt;
    private CountDownLatch latch;

    public ParkInnerThread(Thread t, int before, int after, CountDownLatch latch) {
        this.thread = t;
        this.beforePermit = before;
        this.afterPermitt = after;
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("in park inner thread");
        for (int i = 0; i < beforePermit; i++) {
            LockSupport.unpark(thread);
        }
        System.out.println("unpark call ");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < afterPermitt; i++) {
            LockSupport.unpark(thread);
        }
        System.out.println();
    }
}
