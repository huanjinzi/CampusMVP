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
    public static final String SPREF = "user";
    public static boolean HAS_LOGED = false;
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
