package com.gzs.learn.bootstrap.util;

import java.util.concurrent.Future;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

public class AsyncWebUtil {
    // private static final Logger logger = LoggerFactory.getLogger(AsyncWebUtil.class);
    private static CloseableHttpAsyncClient asyncClient = HttpClientFactory.getAsyncHttpclient();
    static {
        asyncClient.start();
    }

    public static Future<HttpResponse> doGet(String url) {
        HttpGet get = new HttpGet(url);
        return asyncClient.execute(get, null);
    }
}