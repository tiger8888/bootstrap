package com.gzs.learn.bootstrap.jmx;

public interface JmxMBean {
    public void setName(String name);

    public String getName();

    public String status();

    public void start();

    public void stop();
}
