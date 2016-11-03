package com.campus.huanjinzi.campusmvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.campus.huanjinzi.campusmvp.utils.Hlog;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BASE_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Hlog.i(TAG,"onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Hlog.i(TAG,"onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Hlog.i(TAG,"onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Hlog.i(TAG,"onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Hlog.i(TAG,"onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Hlog.i(TAG,"onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Hlog.i(TAG,"onDestroy()");
    }
}
