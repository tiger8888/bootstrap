package com.gzs.learn.bootstrap.concurrent;

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
        //Object
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
    public void testHashCode() {
        String str1 = new String("111");
        String str2 = "111";
        System.out.println(str1.equals(str2));
        System.out.println(str1.hashCode() + "|" + str2.hashCode());
        System.out.println(str1 == str2);
    }
}
