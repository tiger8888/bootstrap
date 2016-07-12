package com.gzs.learn.bootstrap;

import org.junit.Test;

public class TryCatchFinallyTest {

    @Test
    public void testTryException() throws Exception {
        try {
            System.out.println("in try");
            throw new Exception("error");
        } finally {
            System.out.println("in finally");
        }
    }

    @Test
    public void testTryMethod() {
        System.out.println(testMethod());
    }

    private int testMethod() {
        try {
            return testMethod2();
        } finally {
            System.out.println("in finally");
        }
    }

    private int testMethod2() {
        System.out.println("in method2");
        return 0;
    }
}
