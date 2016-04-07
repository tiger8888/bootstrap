package com.gzs.learn.bootstrap;

public class StringTest {
    public static void main(String[] args) {
        String str1 = new StringBuilder("123").append("e").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("voi").append("d").toString();
        System.out.println(str2.intern() == str2);

        Object[] a = new Object[] {new Object()};
        System.out.println(a.getClass().getName());
        Object object = new Object();
        System.out.println(object.getClass().getName());
    }
}
