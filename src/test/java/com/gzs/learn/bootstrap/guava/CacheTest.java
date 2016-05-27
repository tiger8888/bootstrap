package com.gzs.learn.bootstrap.guava;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class CacheTest {

    @Test
    public void cacheTest() throws Exception {
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(10, TimeUnit.SECONDS)
                .build();
        cache.put("key", "val");
        System.out.println(cache.getIfPresent("key"));
        for (int i = 0; i < 8; i++) {
            String key = "key" + i;
            String val = "val" + i;
            cache.put(key, val);
            System.out.println(cache.getIfPresent(key));
        }
        System.out.println(cache.getIfPresent("key"));
        Thread.sleep(1000 * 12);
        System.out.println(cache.getIfPresent("key"));
    }

    @Test
    public void testOrderFilter() {
        Foo foo1 = new Foo(1, "1", "1");
        Foo foo2 = new Foo(1, "0", "1");
        Foo foo3 = new Foo(1, "2", "2");
        Foo foo4 = new Foo(0, "-1", "1");
        List<Foo> list = Lists.asList(foo1, new Foo[] { foo2, foo3, foo4 });
        Ordering<Foo> natuarl = Ordering.natural();
        list = natuarl.sortedCopy(list);
        System.out.println(list.get(0).getName());
        System.out.println(list.get(1).getName());
        System.out.println(list.get(2).getName());

        System.out.println(list);
    }
}

class Foo implements Comparable<Foo> {
    private int id;
    private String name;
    private String val;

    public Foo(int id, String name, String val) {
        this.id = id;
        this.name = name;
        this.val = val;
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

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public int compareTo(Foo o) {
        return ComparisonChain.start().compare(this.getId(), o.getId()).compare(this.getName(), o.getName())
                .compare(this.val, o.getVal()).result();
    }

}
