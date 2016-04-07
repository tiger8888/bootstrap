package com.gzs.learn.bootstrap.jmx;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.swing.JDialog;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class Start {
    private static String MBEAN_NAME = "com.gzs.learn.bootstrap.jmx:type=JmxMBean";

    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        Jmx impl = new Jmx();
        mbs.registerMBean(impl, new ObjectName(MBEAN_NAME));
        // 创建适配器，用于能够通过浏览器访问MBean
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.setPort(9797);
        mbs.registerMBean(adapter, new ObjectName("MyappMBean:name=htmladapter,port=9797"));
        adapter.start();

        // 由于是为了演示保持程序处于运行状态，创建一个图形窗口
        javax.swing.JDialog dialog = new JDialog();
        dialog.setName("jmx test");
        dialog.setVisible(true);
    }
}
