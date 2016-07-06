package com.gzs.learn.bootstrap.concurrent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

public class WaitTest {

    @Test
    public void testWaitNotify() {
        Object lock = new Object();
        List<Thread> waitThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread waitThread = new WaitThread(lock);
            waitThread.start();
            waitThreads.add(waitThread);
        }
        Thread notifyThread = new NotifyThread(waitThreads);

        notifyThread.start();
        try {
            notifyThread.join();
            for (Thread t : waitThreads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WaitThread extends Thread {
    private Object lock;

    public WaitThread(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if (lock == null) {
            return;
        }
        int len = 10;
        while (len > 0) {
            LockSupport.park(lock);
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " recv notify for lock:" + DateFormatUtils.format(new Date(), "HH:mm:ss"));
            len--;
        }
    }
}

class NotifyThread extends Thread {
    private List<Thread> locakThreads;

    public NotifyThread(List<Thread> lockThreads) {
        this.locakThreads = lockThreads;
    }

    @Override
    public void run() {
        int len = 10;
        while (len > 0) {
            try {
                for (int i = 0; i < locakThreads.size(); i++) {
                    LockSupport.unpark(locakThreads.get(i));
                    Thread.sleep(1 * 1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            len--;
        }
    }
}