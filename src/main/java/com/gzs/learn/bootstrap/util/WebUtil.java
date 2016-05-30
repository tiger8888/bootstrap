package com.gzs.learn.bootstrap.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
    private static HttpClient client = HttpClientFactory.get();

    /**
     * get 请求
     *
     * @param url
     * @param headers
     * @return
     */
    public static <T> Response<T> doGet(String url, Class<? extends T> cls, Header... headers) {
        try {
            HttpUriRequest request = RequestBuilder.get(url).setConfig(HttpClientFactory.getDefaultRequestConfig())
                    .build();
            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), cls);
        } catch (Exception e) {
            logger.error("Catch exception in method [get], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * 获取list列表
     * 
     * @param url
     * @param reference
     * @param headers
     * @return
     */
    public static <T> Response<T> doGet(String url, TypeReference<List<T>> reference, Header... headers) {
        try {
            HttpUriRequest request = RequestBuilder.get(url).setConfig(HttpClientFactory.getDefaultRequestConfig())
                    .build();
            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            HttpResponse response = client.execute(request);
            return getResult(response, reference);
        } catch (Exception e) {
            logger.error("Catch exception in method [get], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * post请求
     * 
     * @param url
     * @param pairs
     * @param cls
     * @param headers
     * @return
     */
    public static <T> Response<T> doPost(String url, List<? extends NameValuePair> pairs, Class<? extends T> cls,
            Header... headers) {
        try {
            HttpUriRequest request;
            if (pairs != null && pairs.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, Charsets.UTF_8);
                request = RequestBuilder.post(url).setEntity(entity)
                        .setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            } else {
                request = RequestBuilder.post(url).setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            }

            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), cls);
        } catch (Exception e) {
            logger.error("Catch exception in method [post], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * post请求
     * 
     * @param url
     * @param pairs
     * @param reference
     * @param headers
     * @return
     */
    public static <T> Response<T> doPost(String url, List<? extends NameValuePair> pairs,
            TypeReference<List<T>> reference, Header... headers) {
        try {
            HttpUriRequest request;
            if (pairs != null && pairs.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, Charsets.UTF_8);
                request = RequestBuilder.post(url).setEntity(entity)
                        .setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            } else {
                request = RequestBuilder.post(url).setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            }

            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), reference);
        } catch (Exception e) {
            logger.error("Catch exception in method [post], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * post body请求体
     * 
     * @param url
     * @param requestBody
     * @param cls
     * @param headers
     * @return
     */
    public static <T> Response<T> doPost(String url, String requestBody, Class<? extends T> cls, Header... headers) {
        try {
            HttpUriRequest request = null;
            if (StringUtils.isNotBlank(requestBody)) {
                StringEntity entity = new StringEntity(requestBody, Charsets.UTF_8);
                request = RequestBuilder.post(url).setEntity(entity)
                        .setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            } else {
                request = RequestBuilder.post(url).setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            }
            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), cls);
        } catch (Exception e) {
            logger.error("Catch exception in method [post], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * post body请求体
     * 
     * @param url
     * @param requestBody
     * @param reference
     * @param headers
     * @return
     */
    public static <T> Response<T> doPost(String url, String requestBody, TypeReference<List<T>> reference,
            Header... headers) {
        try {
            HttpUriRequest request = null;
            if (StringUtils.isNotBlank(requestBody)) {
                StringEntity entity = new StringEntity(requestBody, Charsets.UTF_8);
                request = RequestBuilder.post(url).setEntity(entity)
                        .setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            } else {
                request = RequestBuilder.post(url).setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            }
            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), reference);
        } catch (Exception e) {
            logger.error("Catch exception in method [post], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * put请求
     * 
     * @param url
     * @param pairs
     * @param cls
     * @param headers
     * @return
     */
    public static <T> Response<T> doPut(String url, List<? extends NameValuePair> pairs, Class<? extends T> cls,
            Header... headers) {
        try {
            HttpUriRequest request;
            if (pairs != null && pairs.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, Charsets.UTF_8);
                request = RequestBuilder.put(url).setEntity(entity)
                        .setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            } else {
                request = RequestBuilder.post(url).setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            }

            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), cls);
        } catch (Exception e) {
            logger.error("Catch exception in method [put], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * put请求
     * 
     * @param url
     * @param pairs
     * @param cls
     * @param headers
     * @return
     */
    public static <T> Response<T> doPut(String url, List<? extends NameValuePair> pairs,
            TypeReference<List<T>> reference, Header... headers) {
        try {
            HttpUriRequest request;
            if (pairs != null && pairs.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, Charsets.UTF_8);
                request = RequestBuilder.put(url).setEntity(entity)
                        .setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            } else {
                request = RequestBuilder.post(url).setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            }

            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), reference);
        } catch (Exception e) {
            logger.error("Catch exception in method [put], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * do put请求体
     * 
     * @param url
     * @param requestBody
     * @param cls
     * @param headers
     * @return
     */
    public static <T> Response<T> doPut(String url, String requestBody, Class<? extends T> cls, Header... headers) {
        try {
            HttpUriRequest request;
            if (StringUtils.isNotBlank(requestBody)) {
                StringEntity entity = new StringEntity(requestBody, Charsets.UTF_8);
                request = RequestBuilder.put(url).setEntity(entity)
                        .setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            } else {
                request = RequestBuilder.post(url).setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            }

            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), cls);
        } catch (Exception e) {
            logger.error("Catch exception in method [put], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * do put请求体
     * 
     * @param url
     * @param requestBody
     * @param cls
     * @param headers
     * @return
     */
    public static <T> Response<T> doPut(String url, String requestBody, TypeReference<List<T>> reference,
            Header... headers) {
        try {
            HttpUriRequest request;
            if (StringUtils.isNotBlank(requestBody)) {
                StringEntity entity = new StringEntity(requestBody, Charsets.UTF_8);
                request = RequestBuilder.put(url).setEntity(entity)
                        .setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            } else {
                request = RequestBuilder.post(url).setConfig(HttpClientFactory.getDefaultRequestConfig()).build();
            }

            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), reference);
        } catch (Exception e) {
            logger.error("Catch exception in method [put], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * delete方法
     *
     * @param url
     * @param headers
     * @return
     */
    public static <T> Response<T> doDelete(String url, Class<? extends T> cls, Header... headers) {
        try {
            HttpUriRequest request = RequestBuilder.delete(url).setConfig(HttpClientFactory.getDefaultRequestConfig())
                    .build();
            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            return getResult(client.execute(request), cls);
        } catch (Exception e) {
            logger.error("Catch exception in method [delete], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * head 请求
     * 
     * @param url
     * @param headers
     * @return
     */
    public static Integer doHead(String url, Header... headers) {
        try {
            HttpUriRequest request = RequestBuilder.head(url).setConfig(HttpClientFactory.getDefaultRequestConfig())
                    .build();
            if (headers != null && headers.length > 0) {
                request.setHeaders(headers);
            }
            HttpResponse response = client.execute(request);
            return response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            logger.error("Catch exception in method [head], " + "url={}, e={}.", url,
                    Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    /**
     * 构造http url get 请求串
     *
     * @param url
     * @param pairs
     * @return
     */
    public static String buildUrl(String url, List<? extends NameValuePair> pairs) {
        if (CollectionUtils.isEmpty(pairs)) {
            return url;
        }
        return url + "?" + pairs.stream().filter((entry) -> StringUtils.isNotBlank(entry.getValue())).map((entry) -> {
            return entry.getName() + "=" + entry.getValue();
        }).collect(Collectors.joining("&"));
    }

    /**
     * map to list<NameValuePair>
     *
     * @param params
     * @return
     */
    public static List<NameValuePair> convertMap2Pair(Map<String, ? extends Object> params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        return params.entrySet().stream().filter((entry) -> entry.getValue() != null).map((entry) -> {
            return new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
        }).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private static <T> Response<T> getResult(HttpResponse response, Class<? extends T> cls) {
        int code = 0;
        boolean success = false;
        T obj = null;
        ErrorMsg err = null;
        try {
            code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String resp = entity == null ? "" : EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            if (code / 100 == 2) {
                success = true;
                if (cls.getName().equals("java.lang.String")) {
                    obj = (T) resp;
                } else {
                    obj = JSON.parseObject(resp, cls);
                }
            } else {
                err = JSON.parseObject(resp, ErrorMsg.class);
            }
        } catch (Exception e) {
            logger.error("Catch exception when getResult ,e={}.", Throwables.getStackTraceAsString(e));
        }
        return new Response<T>(success, code, obj, err);
    }

    private static <T> Response<T> getResult(HttpResponse response, TypeReference<List<T>> reference) {
        int code = 0;
        boolean success = false;
        List<T> list = null;
        ErrorMsg err = null;
        try {
            code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String resp = entity == null ? "" : EntityUtils.toString(entity, Charsets.UTF_8);
            if (code / 100 == 2) {
                success = true;
                list = JSON.parseObject(resp, reference);
            } else {
                err = JSON.parseObject(resp, ErrorMsg.class);
            }
        } catch (Exception e) {
            logger.error("Catch exception when getResult List ,e={}.", Throwables.getStackTraceAsString(e));
        }
        return new Response<T>(success, code, list, err);
    }

    public static class Response<T> {
        public boolean success;
        public int httpCode;
        public T entity;
        public List<T> list;
        public ErrorMsg err;

        public Response() {
        }

        public Response(boolean success, int httpCode, T entity, ErrorMsg err) {
            this.success = success;
            this.httpCode = httpCode;
            this.entity = entity;
            this.err = err;
        }

        public Response(boolean success, int httpCode, List<T> list, ErrorMsg err) {
            this.success = success;
            this.httpCode = httpCode;
            this.list = list;
            this.err = err;
        }
    }

    public static class ErrorMsg {
        private String err;
        private String msg;

        public String getErr() {
            return err;
        }

        public void setErr(String err) {
            this.err = err;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}