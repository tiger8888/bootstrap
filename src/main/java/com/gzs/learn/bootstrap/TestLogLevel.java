package com.gzs.learn.bootstrap;

import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * test log4j buffered  io param
 * @author guanzhisong
 * @date 2016年7月28日
 */
public class TestLogLevel {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(TestLogLevel.class);
        log.info("in info file");
        log.error("in error file");
        // if config buffered io,make sure log flushed to disk
        LogManager.shutdown();
    }
}
