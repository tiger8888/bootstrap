package com.gzs.learn.bootstrap.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环境判别工具类,针对于一些代码中需要根据不同环境不同逻辑部分切分
 * 
 * @author guanzhisong
 * @date 2016年3月2日
 */
public class ApplicationUtil {
    private static final String ENV_DEV = "dev";
    private static final String ENV_TEST = "test";
    private static final String ENV_PRODUCT = "product";

    private Logger log = LoggerFactory.getLogger(ApplicationUtil.class);
    private Properties prop;

    private ApplicationUtil() {
        prop = new Properties();
        try (InputStream in =
                ApplicationUtil.class.getResourceAsStream("/conf/config.properties")) {
            prop.load(in);
        } catch (IOException e) {
            log.warn("load env mode error,please notice this error,some probleam may occur");
        }
    }

    /**
     * 获取配置环境
     * 
     * @return
     */
    public String getEnv() {
        return prop.getProperty("mode", "dev");
    }

    public boolean isDev() {
        return prop.getProperty("mode").equalsIgnoreCase(ENV_DEV);
    }

    public boolean isTest() {
        return prop.getProperty("mode").equalsIgnoreCase(ENV_TEST);
    }

    public boolean isProduct() {
        return prop.getProperty("mode").equalsIgnoreCase(ENV_PRODUCT);
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    public String getProperty(String key, String defaultVal) {
        return prop.getProperty(key, defaultVal);
    }

    private static class ContextHolder {
        private static ApplicationUtil applicationUtil = new ApplicationUtil();
    }

    public static ApplicationUtil getInstance() {
        return ContextHolder.applicationUtil;
    }
}
