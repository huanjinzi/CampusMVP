package com.campus.huanjinzi.campusmvp.SwuTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.campus.huanjinzi.campusmvp.LogTask.LogActivity;
import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.TranscriptTask.TranscriptActivity;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

/**
 * Created by huanjinzi on 2016/8/25.
 */
public class SwuPresenter {

    public static final String TAG = "SwuPresenter";
    public static final String HAS_COUNT = "has_count";
    public static final String HAS_LOGED = "has_loged";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String RESULT = "result";
    public static final String MODE = "mode";

    public static final String SUCCESS = "success";
    public static final String LOGOUT_SUCCESS = "logout_success";
    public static final String LOGOUT_SUCCESS_SAME = "logout_success_same";

    public static final String SWU_WIFI = "swu-wifi";
    public static final String SWU_WIFI_DORM = "swu-wifi-dorm";

    public static final int LOGIN_DORM = 0;
    public static final int LOGIN_CLASS = 1;
    public static final int LOG_OUT = 2;

    private String username;
    private String password;

    private SharedPreferences spref;
    private TaskManager taskManager;
    private Handler mHandler;
    private Context context;

    public void setLogTaskDone(boolean logTaskDone) {isLogTaskDone = logTaskDone;}

    private boolean isLogTaskDone = false;

    public void setView(View view) {
        this.view = view;
    }

    private View view;

    public SwuPresenter(Context context) {
        this.context = context;
        spref = context.getSharedPreferences(MyApp.SPREF, 0);
        taskManager = TaskManager.getInstance();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                switch (bundle.getInt(SwuPresenter.MODE)) {
                    case SwuPresenter.LOG_OUT:
                        logoutResult(bundle.getInt(SwuPresenter.RESULT));
                        break;
                    case SwuPresenter.LOGIN_DORM:
                        loginResult(bundle.getInt(SwuPresenter.RESULT));
                        break;
                    case SwuPresenter.LOGIN_CLASS:
                        loginResult(bundle.getInt(SwuPresenter.RESULT));
                        break;
                }
            }
        };
    }

    /**
     * 账号退出返回结果处理
     *
     * @param result 账号退出请求的返回结果，返回1成功，返回0失败，返回-1网络错误
     */
    private void logoutResult(int result) {
        isLogTaskDone = true;
        SwuActivity activity = (SwuActivity)context;
        switch (result) {
            case 0:
                Hlog.i(TAG, "(业务->logout)Logout完成，退出账号失败，请稍后重试");
                Snackbar.make(view, "退出账号失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case 1:
                activity = (SwuActivity) context;
                SharedPreferences.Editor editor = activity.getSharedPreferences(MyApp.SPREF, 0).edit();
                editor.putBoolean(SwuPresenter.HAS_LOGED, false);
                editor.commit();
                activity.getTabs().setBackgroundColor(activity.getResources().getColor(R.color.GREY_900));

                Hlog.i(TAG, "(业务->logout)Logout完成，账号退出成功");
                Snackbar.make(view, "账号退出成功", Snackbar.LENGTH_LONG).show();
                break;
            case -1:
                Hlog.i(TAG, "(业务->logout)Logout完成，网络超时");
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * 对登陆的结果进行处理
     */
    private void loginResult(int result) {
        isLogTaskDone = true;
        SwuActivity activity = (SwuActivity)context;
        SharedPreferences.Editor editor;
        switch (result) {
            case 0:
                Hlog.i(TAG, "(业务->login)在其他地方已经登陆");
                Snackbar.make(view, "账号在其他地方登陆,现在退出？", Snackbar.LENGTH_LONG).setActionTextColor(context.getResources().getColor(R.color.GREEN)).setAction("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taskManager.logout(username, password, LOG_OUT);
                    }
                }).show();
                break;
            case 1:
                editor = spref.edit();
                activity = (SwuActivity) context;
                editor.putBoolean(SwuPresenter.HAS_LOGED, true);
                editor.commit();
                activity.getTabs().setBackgroundColor(context.getResources().getColor(R.color.GREEN));
                Hlog.i(TAG, "(业务->login)账号登陆成功,HAS_LOGED = true");
                Snackbar.make(view, "账号登陆成功", Snackbar.LENGTH_LONG).show();
                break;
            case -1:
                Hlog.i(TAG, "(业务->login)登陆失败，请稍后重试");
                editor = spref.edit();
                editor.putBoolean(SwuPresenter.HAS_LOGED, false);
                editor.commit();
                Snackbar.make(view, "登陆失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case -2:
                Hlog.i(TAG, "(业务->login)网络超时");
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    public void showFragment(int position) {

        switch (position) {
            case 0:
                startActivity(context, "账号退出", "退出", LOG_OUT);
                break;
            case 1:
                break;
            case 2:
                Intent intent = new Intent(context, TranscriptActivity.class);
                context.startActivity(intent);
                break;
            case 3:
                break;
            case 4:
                break;

        }
    }

    public void logTask() {
        /**1.是否存在已经登陆的账户*/
        taskManager.setHander(mHandler);
        //存在就读取用户名和密码
        if (hasDefaultCount(context)) {
            Hlog.i(TAG, "(业务->logTask)存在账户,获取用户名和密码");
            username = spref.getString(USERNAME, "");
            password = spref.getString(PASSWORD, "");
            Hlog.i(TAG, "username =" + username);
            Hlog.i(TAG, "password =" + password);

            /**2.已存在的账户是否已经登陆*/
            //已经登陆的话就执行账户退出
            if (spref.getBoolean(HAS_LOGED, false)) {
                Hlog.i(TAG, "(业务->logout)存在账户并且已经登陆,执行Logout操作");
                taskManager.logout(username, password, LOG_OUT);
            }
            //没有登陆的话，先进行退出操作，在进行登陆操作
            else {

                switch (isSwuWifi(context)) {
                    //dorm
                    case 0:
                        Hlog.i(TAG, "(业务->login)存在账户但是没有登陆,执行Login操作(Dorm)");
                        taskManager.login(username, password, LOGIN_DORM);
                        break;
                    //class
                    case 1:
                        Hlog.i(TAG, "(业务->login)存在账户但是没有登陆,执行Login操作(Class)");
                        taskManager.login(username, password, LOGIN_CLASS);
                        break;
                    //连接到校园网wifi，显示设置wifi界面
                    case -1:
                        Hlog.i(TAG, "(业务->login)存在账户但是没有登陆,执行Login操作,没有连接到校园网wifi");
                        showWifiSettings();
                        break;
                }
            }
        }
        //不存在就就启动相应的Activity进行登陆
        else {
            switch (isSwuWifi(context)) {
                //dorm
                case 0:
                    Hlog.i(TAG, "(业务->logTask)不存在账户,启动登陆的Log Activity(Dorm)");
                    startActivity(context, context.getString(R.string.COUNT_LOGIN), context.getString(R.string.LOGIN), LOGIN_DORM);
                    break;
                //class
                case 1:
                    Hlog.i(TAG, "(业务->logTask)不存在账户,启动登陆的Log Activity(Class)");
                    startActivity(context, context.getString(R.string.COUNT_LOGIN), context.getString(R.string.LOGIN), LOGIN_CLASS);
                    break;
                //连接到校园网wifi，显示设置wifi界面
                case -1:
                    Hlog.i(TAG, "(业务->logTask)不存在账户,启动登陆的Log Activity,没有连接到校园网wifi");
                    showWifiSettings();
                    break;
            }
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
     * 携带参数启动activity
     */
    private void startActivity(Context context, String title, String button, int mode) {
        Intent intent = new Intent(context, LogActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.TITLE), title);
        bundle.putString(context.getString(R.string.BUTTON), button);
        bundle.putInt(context.getString(R.string.MODE), mode);
        intent.putExtras(bundle);
        SwuActivity activity = (SwuActivity) context;
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 判断是否存在已经登陆的账号
     */
    private boolean hasDefaultCount(Context context) {
        SharedPreferences sp = context.getSharedPreferences(MyApp.SPREF, 0);
        return sp.getBoolean(HAS_COUNT, false);
    }

    /**
     * 显示wifi设置界面
     */
    private void showWifiSettings() {
        Snackbar.make(view, context.getString(R.string.NOT_CONNECT_SWUWIFI), Snackbar.LENGTH_LONG).setActionTextColor(context.getResources().getColor(R.color.GREEN)).setAction(R.string.WIFI_CONNECT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
            }
        }).show();
    }

    /**
     * 重新启动进行登陆
     */
    public void firstlaunch() {
        taskManager.setHander(mHandler);
        Hlog.i(TAG, "(业务->first launch)");
        username = spref.getString(USERNAME, "");
        password = spref.getString(PASSWORD, "");
        Hlog.i(TAG, "username =" + username);
        Hlog.i(TAG, "password =" + password);

        switch (isSwuWifi(context)) {
            //dorm
            case 0:
                taskManager.login(username, password, LOGIN_DORM);
                break;
            //class
            case 1:
                taskManager.login(username, password, LOGIN_CLASS);
                break;
            //连接到校园网wifi，显示设置wifi界面
            case -1:
                Hlog.i(TAG, "没有连接到校园网wifi");
                showWifiSettings();
                break;
        }
    }

    /**
     * 查询登陆任务完成没有
     */
    public boolean isLogTaskDone() {

        if (isLogTaskDone)
        {
            isLogTaskDone = false;
            return true;
        }
        else
        {
            return false;
        }
    }
}
