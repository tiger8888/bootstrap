package com.gzs.learn.bootstrap.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.util.CollectionUtils;
import com.google.common.collect.Lists;

/**
 * basic parallel http execute engine
 * 
 * @author guanzhisong
 * @date 2016年5月13日
 */
public class ParallelExecuteEngine {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static CompletionService<Object> cService = new ExecutorCompletionService<Object>(executorService);

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * 执行需要返回值的任务
     * 
     * @param handlers
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static final List<Object> parallelExecute(List<ParallelHandler> handlers)
            throws InterruptedException, ExecutionException {
        if (CollectionUtils.isEmpty(handlers)) {
            return Lists.newArrayList();
        }
        int size = handlers.size();
        for (ParallelHandler handler : handlers) {
            cService.submit(new ExecuteThread(handler));
        }
        List<Object> results = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            results.add(cService.take().get());
        }
        return results;
    }

    /**
     * 执行不需要返回值的任务,通常是一些异步的worker
     * 
     * @param handlers
     */
    public static final void parallelExecuteWorker(List<ParallelHandler> handlers) {
        if (CollectionUtils.isEmpty(handlers)) {
            return;
        }
        for (ParallelHandler handler : handlers) {
            executorService.submit(new Runnable() {
                public void run() {
                    handler.handle();
                }
            });
        }
    }
}

class ExecuteThread implements Callable<Object> {
    private ParallelHandler handler;

    public ExecuteThread(ParallelHandler handler) {
        this.handler = handler;
    }

    @Override
    public Object call() throws Exception {
        return handler.handle();
    }
}