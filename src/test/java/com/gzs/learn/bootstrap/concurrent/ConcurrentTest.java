package com.gzs.learn.bootstrap.concurrent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.Test;

public class ConcurrentTest {
    static int x = 0, y = 0, a = 0, b = 0;

    @Test
    public void testInstructionReorder() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            x = y = a = b = 0;

            Runnable r1 = () -> {
                a = 1;
                x = b;
            };
            Thread t1 = new Thread(r1);
            Runnable r2 = () -> {
                b = 1;
                y = a;
            };
            Thread t2 = new Thread(r2);
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            if (x == 0 || y == 0) {
                System.out.println(String.format("x=%d,y=%d", x, y));
            }
        }
    }

    @Test
    public void testInterruptHappensBefore() {
        // Object
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                System.out.println(x);
                x = 8;
            }
        });
        x = 5;
        t1.start();
        t1.interrupt();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("x=" + x);
    }

    @Test
    public void testCompareVolatileAndLock() {
        VolatileFoo volatileFoo = new VolatileFoo();

        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(10) * 1000);
                    } catch (Exception e) {
                    }
                    volatileFoo.increment();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(10) * 1000);
                } catch (Exception e) {
                }
                System.out.println(volatileFoo.getCount());
            }).start();
        }
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLock() throws Exception {
        LockFoo lockFoo = new LockFoo();
        Thread t1 = null, t2 = null;
        int loopTimes = 5;
        t1 = new Thread(() -> {
            for (int i = 0; i < loopTimes; i++) {
                lockFoo.m1();
            }
        });

        t2 = new Thread(() -> {
            for (int i = 0; i < loopTimes; i++) {
                lockFoo.m2();
            }
        });
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("cost:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        t1 = new Thread(() -> {
            for (int i = 0; i < loopTimes; i++) {
                lockFoo.m1();
            }
        });
        t2 = new Thread(() -> {
            for (int i = 0; i < loopTimes; i++) {
                lockFoo.m3();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("cost:" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        t1 = new Thread(() -> {
            for (int i = 0; i < loopTimes; i++) {
                lockFoo.m5();
            }
        });
        t2 = new Thread(() -> {
            for (int i = 0; i < loopTimes; i++) {
                lockFoo.m6();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void testHash() {
        int MAXIMUM_CAPACITY = 1 << 30;
        int c = 17;
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        int r = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        System.out.println(r);
    }

    @Test
    public void testCopyOnWriteList() {
        List<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                a = 1;
                list.set(0, 1);
            }).start();

            new Thread(() -> {
                list.size();
                b = a;
            }).start();

            System.out.println(String.format("(a,b) is (%d,%d)", a, b));
        }
    }

    @Test
    public void testCasCounter() throws InterruptedException {
        int threadCount = 10;
        long start = 0, cost = 0;
        VolatileFoo foo = new VolatileFoo();
        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            es.submit(() -> {
                System.out.println("just init thread pool");
            });
        }

        start = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            es.submit(() -> {
                for (int j = 0; j < threadCount; j++) {
                    foo.increment();
                }
            });
        }
        es.shutdown();
        es.awaitTermination(10, TimeUnit.MINUTES);
        cost = System.currentTimeMillis() - start;
        System.out.println("synchronized cost:" + cost);
        System.out.println(foo.getCount());

        es = Executors.newFixedThreadPool(threadCount);
        AtomicInteger atomicInteger = new AtomicInteger();
        start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            es.submit(() -> {
                for (int j = 0; j < threadCount; j++) {
                    atomicInteger.incrementAndGet();
                }
            });
        }
        es.shutdown();
        es.awaitTermination(10, TimeUnit.MINUTES);
        cost = System.currentTimeMillis() - start;
        System.out.println("cas cost:" + cost);
        System.out.println(atomicInteger);
    }

    @Test
    public void testReentrantLock() {
        Lock lock = new ReentrantLock();
        lock.lock();
        System.out.println("first lock");
        lock.lock();
        System.out.println("second lock");
        lock.unlock();
        lock.unlock();
    }

    @Test
    public void testRejectTask() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 30, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(30));
        for (int i = 0; i < 8000; i++) {
            try {
                executorService.execute(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(i);
                break;
            }
        }
    }

    @Test
    public void testThreadPoolExecutor() {
        int count = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            new Thread(() -> executorService.submit(new TestThread())).start();
        }
        executorService.submit(new TestThread());
        executorService.shutdown();
    }
}

class TestThread implements Runnable {
    @Override
    public void run() {
        System.out.println("test 111");
    }
}

class CopyOnWriteTest extends Thread {
    public int a = 0;
    public int b = 0;

    @Override
    public void run() {
        a = 1;

    }
}

class VolatileFoo {
    private volatile int count = 0;

    public void increment() {
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}

class LockFoo {
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    private int time = 1;

    public void m1() {
        lock1.lock();
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock1.unlock();
        }
    }

    public void m2() {
        lock1.lock();
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock1.unlock();
        }
    }

    public void m3() {
        lock2.lock();
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
        }
    }

    public void m4() {
        lock2.lock();
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock2.unlock();
        }
    }

    public synchronized void m5() {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void m6() {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}