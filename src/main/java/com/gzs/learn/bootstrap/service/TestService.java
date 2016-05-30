package com.gzs.learn.bootstrap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import com.gzs.learn.bootstrap.util.AsyncWebUtil;
import com.gzs.learn.bootstrap.util.ParallelExecuteEngine;
import com.gzs.learn.bootstrap.util.ParallelHandler;
import com.gzs.learn.bootstrap.util.WebUtil;

@Component
public class TestService {
    public static final String URL1 = "http://uapi1.jiashuangkuaizi.com/test2/a1";
    public static final String URL2 = "http://uapi1.jiashuangkuaizi.com/test2/a2";
    public static final String URL3 = "http://uapi1.jiashuangkuaizi.com/test2/a3";

    public String testParall() throws Exception {
        List<ParallelHandler> handlers = new ArrayList<>();
        handlers.add(() -> WebUtil.doGet(URL1, String.class).entity);
        handlers.add(() -> WebUtil.doGet(URL2, String.class).entity);
        handlers.add(() -> WebUtil.doGet(URL3, String.class).entity);

        List<Object> list = ParallelExecuteEngine.parallelExecute(handlers);

        return list.stream().map(obj -> obj.toString()).collect(Collectors.joining());
    }

    public String testAsync() throws Exception {
        List<Future<HttpResponse>> list = new ArrayList<>();
        list.add(AsyncWebUtil.doGet(URL1));
        list.add(AsyncWebUtil.doGet(URL2));
        list.add(AsyncWebUtil.doGet(URL3));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(EntityUtils.toString(list.get(i).get().getEntity()));
        }
        return sb.toString();
    }

    public String testSequence() throws Exception {
        List<String> results = Lists.newArrayList(WebUtil.doGet(URL1, String.class).entity,
                WebUtil.doGet(URL2, String.class).entity, WebUtil.doGet(URL3, String.class).entity);
        return results.stream().map(obj -> obj.toString()).collect(Collectors.joining());
    }

}
