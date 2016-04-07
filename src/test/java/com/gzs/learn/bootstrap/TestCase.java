package com.gzs.learn.bootstrap;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

public class TestCase {
    private String jdbcUrl =
            "jdbc:mysql://localhost:3306/jskz_2.0?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
    private String user = "root";
    private String password = "123456";
    private String driverClass = "com.mysql.jdbc.Driver";
    private int initialSize = 10;
    private int minPoolSize = 10;
    private int maxPoolSize = 50;
    private int maxActive = 50;
    private String validationQuery = "SELECT 1";
    private int threadCount = 50;
    private int loopCount = 10;
    final int LOOP_COUNT = 1000 * 1 * 1 / threadCount;

    private static AtomicLong physicalConnStat = new AtomicLong(0);


    @Test
    public void test_druid() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        driverClass = "com.mysql.jdbc.Driver";
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minPoolSize);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(false);

        for (int i = 0; i < loopCount; ++i) {
            p0(dataSource, "druid", threadCount);
        }
        System.out.println();
    }


    @Test
    public void test_dbcp() throws Exception {
        final BasicDataSource dataSource = new BasicDataSource();

        dataSource.setInitialSize(initialSize);
        dataSource.setMaxTotal(maxActive);
        dataSource.setMinIdle(minPoolSize);
        dataSource.setMaxIdle(maxPoolSize);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);

        for (int i = 0; i < loopCount; ++i) {
            p0(dataSource, "dbcp", threadCount);
        }
        System.out.println();
    }

    private void p0(final DataSource dataSource, String name, int threadCount) throws Exception {
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(threadCount);
        final CountDownLatch dumpLatch = new CountDownLatch(1);

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; ++i) {
            Thread thread = new Thread() {

                public void run() {
                    try {
                        startLatch.await();

                        for (int i = 0; i < LOOP_COUNT; ++i) {
                            Connection conn = dataSource.getConnection();
                            conn.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    endLatch.countDown();

                    try {
                        dumpLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            threads[i] = thread;
            thread.start();
        }
        long startMillis = System.currentTimeMillis();
        long startYGC = TestUtil.getYoungGC();
        long startFullGC = TestUtil.getFullGC();
        startLatch.countDown();
        endLatch.await();

        long[] threadIdArray = new long[threads.length];
        for (int i = 0; i < threads.length; ++i) {
            threadIdArray[i] = threads[i].getId();
        }
        ThreadInfo[] threadInfoArray =
                ManagementFactory.getThreadMXBean().getThreadInfo(threadIdArray);
        dumpLatch.countDown();

        long blockedCount = 0;
        long waitedCount = 0;
        for (int i = 0; i < threadInfoArray.length; ++i) {
            ThreadInfo threadInfo = threadInfoArray[i];
            blockedCount += threadInfo.getBlockedCount();
            waitedCount += threadInfo.getWaitedCount();
        }

        long millis = System.currentTimeMillis() - startMillis;
        long ygc = TestUtil.getYoungGC() - startYGC;
        long fullGC = TestUtil.getFullGC() - startFullGC;

        System.out.println("thread " + threadCount + " " + name + " millis : "
                + NumberFormat.getInstance().format(millis) + "; YGC " + ygc + " FGC " + fullGC
                + " blocked " + NumberFormat.getInstance().format(blockedCount) //
                + " waited " + NumberFormat.getInstance().format(waitedCount) + " physicalConn "
                + physicalConnStat.get());
    }
}
