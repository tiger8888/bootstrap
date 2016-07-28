package com.gzs.learn.bootstrap.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import org.junit.Test;

public class ReentrantReadWriteLockTest {
    @Test
    public void testReadWriteLock() throws InterruptedException {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();
        int writeThreadCount = 2;
        int readThreadCount = 20;
        List<Thread> threads = new ArrayList<>();
        List<Integer> queue = new ArrayList<>();
        for (int i = 0; i < writeThreadCount; i++) {
            Thread writeThread = new Thread(new WriteThread(writeLock, queue));
            threads.add(writeThread);
        }

        for (int i = 0; i < readThreadCount; i++) {
            Thread writeThread = new Thread(new ReadThread(readLock, queue));
            threads.add(writeThread);
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }
}

class ReadThread implements Runnable {
    public Lock readLock;
    public List<Integer> queue;

    public ReadThread(Lock lock, List<Integer> queue) {
        this.readLock = lock;
        this.queue = queue;
    }

    @Override
    public void run() {
        readLock.lock();
        String queueStr = queue.stream().map(i -> i.toString()).collect(Collectors.joining(","));
        System.out.println(queueStr);
        readLock.unlock();
    }
}

class WriteThread implements Runnable {
    public Lock writeLock;
    public List<Integer> queue;
    public Random random = new Random();

    public WriteThread(Lock lock, List<Integer> queue) {
        this.writeLock = lock;
        this.queue = queue;
    }

    @Override
    public void run() {
        writeLock.lock();
        int count = random.nextInt(10);
        for (int i = 0; i < count; i++) {
            queue.add(random.nextInt(10));
        }
        writeLock.unlock();
    }

}
