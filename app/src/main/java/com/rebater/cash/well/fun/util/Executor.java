package com.rebater.cash.well.fun.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Executor {
    public static ExecutorService executorService;

    /* renamed from: bs.yf.a$a  reason: collision with other inner class name */
    public static class C0534a implements RejectedExecutionHandler {
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            LogInfo.e( "Common ThreadPool rejected");
        }
    }

    public static ExecutorService getExecutorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(4, 6, 60, TimeUnit.SECONDS, new LinkedBlockingDeque(30), new C0534a());
        }
        return executorService;
    }
}
