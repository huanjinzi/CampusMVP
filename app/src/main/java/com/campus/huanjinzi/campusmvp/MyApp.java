package com.campus.huanjinzi.campusmvp;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.campus.huanjinzi.campusmvp.SwuTask.SwuActivity;

import java.security.PublicKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by huanjinzi on 2016/9/2.
 */
public class MyApp extends Application {
    public static final String SPREF = "user";
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
