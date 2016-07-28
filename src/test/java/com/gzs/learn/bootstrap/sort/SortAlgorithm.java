package com.gzs.learn.bootstrap.sort;

import org.junit.Test;

public class SortAlgorithm {
    private int[] array = { 9, 0, 12, 3, 5, 6, 90, 7, 2, 1 };

    @Test
    public void bubbleSort() {
        int len = array.length;
        for (int i = len - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
        print(array);
    }

    @Test
    public void insertSort() {
        int pivot = array[0];
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {

            }
        }
    }

    public void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
