package com.gzs.learn.bootstrap;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * jetty server,just for test env
 * 
 * @author guanzhisong
 * @date 2016年1月4日
 */
public class JettyServer {
    public static void main(String[] args) throws Exception {
        WebAppContext context = new WebAppContext("src/main/webapp", "/");
        Server server = new Server(80);
        server.setHandler(context);
        server.start();
        server.join();
    }
}
