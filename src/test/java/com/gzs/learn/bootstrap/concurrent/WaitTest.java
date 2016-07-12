package com.gzs.learn.bootstrap.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.Test;

public class WaitTest {
    private static final int THREAD_COUNT = 4;

    @Test
    public void testWaitNotify() {
        Lock lock = new ReentrantLock();

        List<Thread> waitThreads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread waitThread = new WaitThread(lock);
            waitThread.setName("lock-thread-" + i);
            waitThread.start();
            waitThreads.add(waitThread);
        }
        try {
            for (int i = 0; i < waitThreads.size(); i++) {
                Thread t = waitThreads.get(i);
                if (i % 2 == 0) {
                    // interrupt thread
                    t.interrupt();
                }
            }
            for (Thread t : waitThreads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WaitThread extends Thread {
    private Lock lock;

    public WaitThread(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if (lock == null) {
            return;
        }
        try {
            lock.lockInterruptibly();
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class WaitThreadWithTryLock extends Thread {
    private Lock lock;

    public WaitThreadWithTryLock(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.tryLock(10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}