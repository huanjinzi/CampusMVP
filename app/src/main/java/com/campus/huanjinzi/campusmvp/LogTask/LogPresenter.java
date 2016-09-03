package com.campus.huanjinzi.campusmvp.LogTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuActivity;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuPresenter;
import com.campus.huanjinzi.campusmvp.swuwifi.WifiLander;
import com.campus.huanjinzi.campusmvp.swuwifi.WifiLanderClass;
import com.campus.huanjinzi.campusmvp.swuwifi.WifiLanderDorm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by huanjinzi on 2016/9/3.
 */
public class LogPresenter {
    public static final String RESULT = "result";
    public static final String MODE = "mode";

    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;


    private ExecutorService pool;
    private Handler mHander;
    private View view;

    private String musername;
    private String mpassword;

    private static final LogPresenter presenter = new LogPresenter();

    public static LogPresenter getInstance() {
        return presenter;
    }

    private LogPresenter() {

        pool = Executors.newSingleThreadExecutor();

        mHander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                /**通过handler来与ui线程交互*/
                switch (bundle.getInt(MODE)) {

                    case SwuPresenter.LOG_OUT:
                        //logout登陆结果的处理
                        logoutResult(bundle.getInt(RESULT));
                        break;
                    case SwuPresenter.LOGIN_DORM:
                        loginResult(bundle.getInt(RESULT));
                        break;
                    case SwuPresenter.LOGIN_CLASS:
                        loginResult(bundle.getInt(RESULT));
                        break;
                }
            }
        };
    }

    public void logTask(View view, String username, String password, int mode) {
        this.view = view;
        musername = username;
        mpassword = password;
        switch (mode) {
            case SwuPresenter.LOG_OUT:
                logout(username, password, mode);
                break;
            case SwuPresenter.LOGIN_DORM:
                login(username, password, mode);
                break;
            case SwuPresenter.LOGIN_CLASS:
                login(username, password, mode);
                break;
        }
    }

    /**
     * 账号退出方法
     */
    private void logout(final String username, final String password, final int mode) {

        pool.submit(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                Bundle bundle = new Bundle();
                try {
                    boolean result = WifiLander.logout(username, password);
                    if (result) {
                        bundle.putInt(RESULT, 1);
                    } else {
                        bundle.putInt(RESULT, 0);
                    }
                    bundle.putInt(MODE, mode);
                    message.setData(bundle);
                    mHander.sendMessage(message);

                } catch (Exception e) {
                    bundle.putInt(MODE, mode);
                    bundle.putInt(RESULT, -1);
                    message.setData(bundle);
                    mHander.sendMessage(message);
                }
            }
        });
    }

    /**
     * 账号退出返回结果处理
     *
     * @param result 账号退出请求的返回结果，返回1成功，返回0失败，返回-1网络错误
     */
    private void logoutResult(int result) {
        switch (result) {
            case 0:
                Snackbar.make(view, "退出账号失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case 1:
                if(context instanceof SwuActivity){
                    SwuActivity activity = (SwuActivity) context;
                    SharedPreferences.Editor editor = activity.getSharedPreferences(SwuPresenter.SPREF,0).edit();
                    editor.putBoolean(SwuPresenter.HAS_LOGED,false);
                    editor.commit();
                    activity.getTabs().setBackgroundColor(activity.getResources().getColor(R.color.GREY_900));}
                else {
                    LogActivity activity = (LogActivity) context;
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(SwuPresenter.LOGOUT_SUCCESS,true);
                    intent.putExtras(bundle);
                    activity.setResult(0,intent);
                    activity.finish();
                }
                Snackbar.make(view, "账号退出成功", Snackbar.LENGTH_LONG).show();
                break;
            case -1:
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * 账号登陆的方法
     */
    private void login(final String username, final String password, final int mode) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                WifiLander lander;
                switch (mode) {
                    //在教室里面登陆
                    case SwuPresenter.LOGIN_CLASS:
                        lander = new WifiLanderClass();
                        logBefore(lander, username, password, mode);
                        break;

                    case SwuPresenter.LOGIN_DORM:
                        lander = new WifiLanderDorm();
                        logBefore(lander, username, password, mode);
                        break;
                }
            }
        });
    }

    /**
     * 登陆之前处理
     */
    private void logBefore(WifiLander lander, String username, String password, int mode) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        boolean result;
        try {
            result = lander.login(username, password);
            if (result) {
                bundle.putInt(RESULT, 1);
            } else {
                bundle.putInt(RESULT, 0);
            }
            bundle.putInt(MODE, mode);
            message.setData(bundle);
            mHander.sendMessage(message);
        } catch (Exception e) {
            bundle.putInt(RESULT, -1);
            bundle.putInt(MODE, mode);
            message.setData(bundle);
            mHander.sendMessage(message);
        }
    }

    /**
     * 对登陆的结果进行处理
     */
    private void loginResult(int result) {
        switch (result) {
            case 0:
                Snackbar.make(view, "登陆失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case 1:
                SharedPreferences sp = context.getSharedPreferences(SwuPresenter.SPREF, 0);
                SharedPreferences.Editor editor = sp.edit();
                if (sp.getBoolean(SwuPresenter.HAS_COUNT, false)) {
                    SwuActivity activity = (SwuActivity) context;
                    editor.putBoolean(SwuPresenter.HAS_COUNT, true);
                    editor.putBoolean(SwuPresenter.HAS_LOGED, true);
                    editor.commit();
                    activity.getTabs().setBackgroundColor(context.getResources().getColor(R.color.GREEN));
                } else {
                    editor.putString(SwuPresenter.USERNAME, musername);
                    editor.putString(SwuPresenter.PASSWORD, mpassword);
                    editor.putBoolean(SwuPresenter.HAS_COUNT, true);
                    editor.putBoolean(SwuPresenter.HAS_LOGED, true);
                    editor.commit();
                    Intent intent = new Intent(context, SwuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(SwuPresenter.SUCCESS, true);
                    intent.putExtras(bundle);
                    LogActivity activity = (LogActivity) context;
                    activity.setResult(0, intent);
                    activity.finish();
                }
                Snackbar.make(view, "账号登陆成功", Snackbar.LENGTH_LONG).show();
                break;
            case -1:
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

}