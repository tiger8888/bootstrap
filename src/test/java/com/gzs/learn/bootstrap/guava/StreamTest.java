package com.gzs.learn.bootstrap.guava;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.Test;

public class StreamTest {
    @Test
    public void testCount() {
        Random random1 = new Random(new Date().getTime());
        int len = 100000;
        List<Bar> list = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            int id = i;
            String name = random1.nextInt(len / 100) + "";
            int age = random1.nextInt(len / 100);
            Bar bar = new Bar(id, name, age);
            list.add(bar);
        }
        // Map<String, Integer> mapCount = new HashMap<>();
        long start = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += list.get(i).getAge();
        }
        long cost = System.currentTimeMillis() - start;

        System.out.println("loop cost:" + cost + "," + sum);

        start = System.currentTimeMillis();
        sum = list.stream().mapToInt(bar -> bar.getAge()).sum();
        cost = System.currentTimeMillis() - start;
        System.out.println("loop cost:" + cost + "," + sum);

        start = System.currentTimeMillis();
        sum = list.parallelStream().mapToInt(bar -> bar.getAge()).sum();
        cost = System.currentTimeMillis() - start;
        System.out.println("loop cost:" + cost + "," + sum);

        Map<Integer, List<Bar>> list2 = list.stream().filter(bar -> bar.getId() >= 1000)
                .collect(Collectors.groupingBy(bar -> bar.getAge()));

        list2.forEach((key, list3) -> {
            System.out.println(key + " " + list3.size());
        });
    }
}

class Bar {
    private int id;
    private String name;
    private int age;

    public Bar(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
