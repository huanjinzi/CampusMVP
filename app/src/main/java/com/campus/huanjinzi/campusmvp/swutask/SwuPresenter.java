package com.campus.huanjinzi.campusmvp.SwuTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.campus.huanjinzi.campusmvp.InfoTask.InfoActivity;
import com.campus.huanjinzi.campusmvp.LogTask.LogActivity;
import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.TranscriptTask.TranscriptActivity;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by huanjinzi on 2016/8/25.
 */
public class SwuPresenter {

    public static final String TAG = "SwuPresenter";
    public static final String HAS_COUNT = "has_count";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String RESULT = "result";
    public static final String MODE = "mode";

    public static final String SWU_WIFI = "swu-wifi";
    public static final String SWU_WIFI_DORM = "swu-wifi-dorm";

    public static final int LOGIN_DORM = 0;
    public static final int LOGIN_CLASS = 1;
    public static final int LOG_OUT = 2;
    public static final int LOGIN_TEST = 3;

    private String username;
    private String password;

    private SharedPreferences spref;
    private TaskManager taskManager;
    private Handler mHandler;
    private Context context;
    private boolean isLogTaskDone = false;
    private View view;

    public void setView(View view) {
        this.view = view;
    }
    public SwuPresenter(Context context) {
        this.context = context;
        spref = context.getSharedPreferences(MyApp.SPREF, 0);
        taskManager = TaskManager.getInstance();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                switch (bundle.getInt(SwuPresenter.MODE)) {
                    case LOG_OUT:
                        logoutResult(bundle.getInt(SwuPresenter.RESULT));
                        break;
                    case LOGIN_DORM:
                        loginResult(bundle.getInt(SwuPresenter.RESULT));
                        break;
                    case LOGIN_CLASS:
                        loginResult(bundle.getInt(SwuPresenter.RESULT));
                        break;
                    case LOGIN_TEST:
                        networktest(bundle.getBoolean("pass"));
                        break;
                }
            }
        };
    }
    /**list启动页面管理*/
    public void showFragment(int position) {
        Intent intent;
        switch (position) {
            case 0:
                if (isSwuWifi(context) == -1)
                {
                    showWifiSettings();
                }
                else
                {
                    startActivity(context,LogActivity.class, "账号退出", "退出", LOG_OUT);
                }
                break;

            case 1:
                LogActivityLauch();
                break;

            case 2:
                if (context.getSharedPreferences(MyApp.SPREF, 0).getBoolean(SwuPresenter.HAS_COUNT, false))
                {
                    intent = new Intent(context, TranscriptActivity.class);
                    context.startActivity(intent);
                }
                else
                {
                    Snackbar.make(view, "请登录账号后查询成绩", Snackbar.LENGTH_LONG).setActionTextColor(context.getResources().getColor(R.color.GREEN)).setAction("现在登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LogActivityLauch();
                        }
                    }).show();
                }
                break;
            case 3:
                startActivity(context,InfoActivity.class, "个人信息", null, 0);
                break;
            case 4:
                startActivity(context,InfoActivity.class, "关于软件", null, 0);
                break;

        }
    }

    public void logTask() {

        if (isSwuWifi(context) == -1)
        {
            context.sendBroadcast(new Intent(SwuActivity.LOGTASK_DONE));
            showWifiSettings();//连接到校园网wifi，显示设置wifi界面
        }
        else
        {
            taskManager.setHander(mHandler);
            //1.是否存在已经登陆的账户
            if (hasCount(context))
            {
                username = spref.getString(USERNAME, "");
                password = spref.getString(PASSWORD, "");

                //2.已存在的账户是否已经登陆
                if (Flags.getInstance().isHAS_LOGED())
                {
                    taskManager.logout(username, password, LOG_OUT);
                }
                else
                {
                    switch (isSwuWifi(context))
                    {
                        case 0://dorm
                            Hlog.i(TAG, "执行Login操作(Dorm)");
                            taskManager.login(username, password, LOGIN_DORM);
                            break;
                        case 1://class
                            Hlog.i(TAG, "执行Login操作(Class)");
                            taskManager.login(username, password, LOGIN_CLASS);
                            break;
                    }
                }
            }
            else
            {
                context.sendBroadcast(new Intent(SwuActivity.LOGTASK_DONE));
                LogActivityLauch();//之前没有账号登陆
            }
        }
    }

    /**
     * 携带参数启动activity
     */
    private void startActivity(Context context, Class activity,String title, String button, int mode) {
        Intent intent = new Intent(context, activity);
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.TITLE), title);
        bundle.putString(context.getString(R.string.BUTTON), button);
        bundle.putInt(context.getString(R.string.MODE), mode);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 判断是否存在已经登陆的账号
     */
    private boolean hasCount(Context context) {
        SharedPreferences sp = context.getSharedPreferences(MyApp.SPREF, 0);
        return sp.getBoolean(HAS_COUNT, false);
    }

    /**
     * 显示wifi设置界面
     */
    private void showWifiSettings() {
        isLogTaskDone = true;
        Snackbar.make(view, context.getString(R.string.NOT_CONNECT_SWUWIFI), Snackbar.LENGTH_LONG).setActionTextColor(context.getResources().getColor(R.color.GREEN)).
                setAction(R.string.WIFI_CONNECT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);}
        }).show();
    }

    /**
     * 启动输入账号的activity
     **/
    private void LogActivityLauch() {
        switch (isSwuWifi(context)) {
            case 0://dorm
                Hlog.i(TAG, "启动LogActivity(Dorm)");
                startActivity(context, LogActivity.class,context.getString(R.string.COUNT_LOGIN), context.getString(R.string.LOGIN), LOGIN_DORM);
                break;
            case 1://class
                Hlog.i(TAG, "(启动LogActivity(Class)");
                startActivity(context, LogActivity.class,context.getString(R.string.COUNT_LOGIN), context.getString(R.string.LOGIN), LOGIN_CLASS);
                break;
            case -1://连接到校园网wifi，显示设置wifi界面
                Hlog.i(TAG, "没有连接到校园网wifi");
                showWifiSettings();
                break;
        }
    }
    /**
     * 判断是不是校园wifi的方法
     */
    public static int isSwuWifi(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info.getSSID().trim().contains(SWU_WIFI_DORM)) {
            return 0;
        } else if (info.getSSID().trim().contains(SWU_WIFI)) {
            return 1;
        } else {
            return -1;
        }
    }
    /**
     * 对登陆的结果进行处理
     */
    private void loginResult(int result) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        switch (result) {

            case 0:
                context.sendBroadcast(new Intent(SwuActivity.LOGTASK_DONE));
                Snackbar.make(view, "账号在其他地方登陆,现在退出？", Snackbar.LENGTH_LONG).setActionTextColor(context.getResources().getColor(R.color.GREEN)).setAction("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taskManager.logout(username, password, LOG_OUT);
                    }
                }).show();
                break;
            case 1:
                //测试网络是否可用
                AsyncTask<Boolean,Object,Boolean> task = new NetPing();
                task.execute(true);

                break;
            case -1:
                context.sendBroadcast(new Intent(SwuActivity.LOGTASK_DONE));
                Flags.getInstance().setHAS_LOGED(false);
                manager.reassociate();
                Snackbar.make(view, "登陆失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case -2:
                context.sendBroadcast(new Intent(SwuActivity.LOGTASK_DONE));
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }


    /**
     * 账号退出返回结果处理
     *
     * @param result 账号退出请求的返回结果，返回1成功，返回0失败，返回-1网络错误
     */
    private void logoutResult(int result) {
        context.sendBroadcast(new Intent(SwuActivity.LOGTASK_DONE));
        switch (result) {
            case 0:
                Snackbar.make(view, "退出账号失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case 1:
                Flags.getInstance().setHAS_LOGED(false);
                Intent intent = new Intent(SwuActivity.LOGOUT_SUCCESS);
                context.sendBroadcast(intent);
                Snackbar.make(view, "账号退出成功", Snackbar.LENGTH_LONG).show();
                break;
            case -1:
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    public boolean Ping(String str) {
        boolean resault = false;
        Process p;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 3 -w 5 " + str);
            int status = p.waitFor();

            if (status == 0) {
                resault = true;
            } else {
                resault = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resault;
    }

    private class NetPing extends AsyncTask<Boolean, Object, Boolean> {
        @Override
        protected Boolean doInBackground(Boolean...b) {
            return Boolean.valueOf(Ping("www.baidu.com"));
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt(MODE,LOGIN_TEST);
            bundle.putBoolean("pass",aBoolean.booleanValue());
            message.setData(bundle);
            mHandler.sendMessage(message);
        }
    }
    /**network test*/
    private void networktest(boolean pass){
        context.sendBroadcast(new Intent(SwuActivity.LOGTASK_DONE));
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(pass)
        {
            Snackbar.make(view, "账号登陆成功", Snackbar.LENGTH_LONG).show();
            Flags.getInstance().setHAS_LOGED(true);
            Intent intent = new Intent(SwuActivity.LOGIN_SUCCESS);
            context.sendBroadcast(intent);
        }
        else
        {
            Snackbar.make(view, "登陆失败，请稍后重试", Snackbar.LENGTH_LONG).show();
            manager.reassociate();
        }
    }
}
