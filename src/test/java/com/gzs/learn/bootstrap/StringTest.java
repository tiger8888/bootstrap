package com.gzs.learn.bootstrap;

import org.junit.Test;

public class StringTest {
    public static void main(String[] args) {
        String str1 = new StringBuilder("123").append("e").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("voi").append("d").toString();
        System.out.println(str2.intern() == str2);

        Object[] a = new Object[] { new Object() };
        System.out.println(a.getClass().getName());
        Object object = new Object();
        System.out.println(object.getClass().getName());
    }

    @Test
    public void longToStrTest() {
        long test = 123456L;
        StringBuffer sb = new StringBuffer();
        longToStr(sb, test);
        System.out.println(sb.toString());
    }

    private int longToStr(StringBuffer sb, long val) {
        int count = 0;

        do {
            sb.append(val % 10);
            val = val / 10;
            count++;
        } while (val / 10 > 0);
        return count;

    }
}
