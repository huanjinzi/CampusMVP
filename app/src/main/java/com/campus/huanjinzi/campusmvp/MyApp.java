package com.campus.huanjinzi.campusmvp;

import android.app.Application;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by huanjinzi on 2016/9/2.
 */
public class MyApp extends Application {

    ThreadPoolExecutor pool;

    public ExecutorService getPool() {
        return pool;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        pool.setCorePoolSize(2);
        pool.setKeepAliveTime(20, TimeUnit.SECONDS);
    }
}
