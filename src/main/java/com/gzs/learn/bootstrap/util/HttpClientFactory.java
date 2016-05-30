package com.gzs.learn.bootstrap.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.HttpContext;

public class HttpClientFactory {
    public final static int CONNECTION_TIMEOUT = 10 * 1000;
    private static HttpClient defaultClient;
    private static CloseableHttpAsyncClient asyncClient;

    public synchronized static HttpClient get() {
        if (defaultClient == null) {
            defaultClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                    .setUserAgent("Home Cooked HTTP Client")
                    .setRetryHandler(new StandardHttpRequestRetryHandler(3, true))
                    .setKeepAliveStrategy(keepAliveStrategy)
                    .setServiceUnavailableRetryStrategy(new DefaultServiceUnavailableRetryStrategy())
                    .setMaxConnPerRoute(32).setMaxConnTotal(256).build();
        }
        return defaultClient;
    }

    public synchronized static CloseableHttpAsyncClient getAsyncHttpclient() {
        if (asyncClient == null) {
            asyncClient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig)
                    .setUserAgent("Home Cooked HTTP Client").setKeepAliveStrategy(keepAliveStrategy)
                    .setMaxConnPerRoute(32).setMaxConnTotal(256).build();
        }
        return asyncClient;
    }

    static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_TIMEOUT)
            .setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(CONNECTION_TIMEOUT).build();

    static ConnectionKeepAliveStrategy keepAliveStrategy = (resp, context) -> 30 * 1000;

    public static RequestConfig getDefaultRequestConfig() {
        return requestConfig;
    }
}

class DefaultServiceUnavailableRetryStrategy implements ServiceUnavailableRetryStrategy {

    public final static int MAX_RETRIES = 3;
    public final static int RETRY_INTERVAL = 1000;

    @Override
    public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
        return executionCount <= MAX_RETRIES
                && response.getStatusLine().getStatusCode() >= HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

    @Override
    public long getRetryInterval() {
        return RETRY_INTERVAL;
    }
}
