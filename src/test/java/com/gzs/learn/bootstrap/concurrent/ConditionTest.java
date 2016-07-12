package com.gzs.learn.bootstrap.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class ConditionTest {

    @Test
    public void testCondition() throws InterruptedException {
        ProducerQueue<Integer> queue = new ProducerQueue<>();
        List<Thread> threads = new ArrayList<>();
        int threadCount = 3;
        for (int i = 0; i < threadCount; i++) {
            final int p = i;
            Thread t = new Thread(() -> {
                try {
                    queue.put(p);
                    // Thread.sleep(200);
                    // System.out.println(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.setName("produce-thread-" + i);
            threads.add(t);
        }
        for (int i = 0; i < threadCount; i++) {
            Thread t2 = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + queue.take().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t2.setName("consumer-thread-" + i);
            threads.add(t2);
        }

        for (Thread t : threads) {
            t.start();
        }
        Thread.sleep(100);
    }
}

class ProducerQueue<T> {
    private T[] data;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();
    private int defaultSize = 10;
    private int head = 0, tail = 0, count = 0;

    @SuppressWarnings("unchecked")
    public ProducerQueue() {
        this.data = (T[]) new Object[defaultSize];
    }

    public void put(T t) throws InterruptedException {
        try {
            lock.lock();
            while (count == data.length) {
                // queue is full
                notFull.await();
            }
            data[head++] = t;

            if (head >= data.length) {
                head = 0;
            }
            count++;
            // signal notEmpty condition
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        try {
            lock.lock();
            while (count == 0) {
                // queue is empty
                notEmpty.await();
            }
            T t = data[tail++];
            if (tail >= data.length) {
                tail = 0;
            }
            count--;
            // signal notFull condition
            notFull.signalAll();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public int getSize() {
        try {
            lock.lock();
            return count;
        } finally {
            lock.unlock();
        }
    }

}
