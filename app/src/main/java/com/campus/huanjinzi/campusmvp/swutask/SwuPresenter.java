package com.campus.huanjinzi.campusmvp.SwuTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.campus.huanjinzi.campusmvp.LogTask.LogActivity;
import com.campus.huanjinzi.campusmvp.LogTask.LogPresenter;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.TranscriptTask.TranscriptActivity;

/**
 * Created by huanjinzi on 2016/8/25.
 */
public class SwuPresenter{

    public static final String SPREF = "user";
    public static final String HAS_COUNT = "has_count";
    public static final String HAS_LOGED = "has_loged";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String SUCCESS = "success";
    public static final String LOGOUT_SUCCESS = "logout_success";

    public static final String SWU_WIFI = "swu-wifi";
    public static final String SWU_WIFI_DORM = "swu-wifi-dorm";
    public static final int LOGIN_DORM = 0;
    public static final int LOGIN_CLASS = 1;
    public static final int LOG_OUT = 2;

    private Context context;
    private View view;
    public SwuPresenter(Context context){this.context = context;}


    public void showFragment(int position) {

        switch (position) {
            case 0:
                startActivity(context,"账号退出","退出",LOG_OUT);
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

    public void logTask(View view) {
        this.view = view;
        String username;
        String password;
        SharedPreferences spref = context.getSharedPreferences(SPREF,0);

        /**1.是否存在已经登陆的账户*/


        //存在就读取用户名和密码
        if(hasDefaultCount(context))
        {
            username = spref.getString(USERNAME,"");
            password = spref.getString(PASSWORD,"");

            /**2.已存在的账户是否已经登陆*/
            //已经登陆的话就执行账户退出
            if(spref.getBoolean(HAS_LOGED,false))
            {
                LogPresenter.getInstance().setContext(context);
                LogPresenter.getInstance().logTask(view,username,password,LOG_OUT);
            }
            //没有登陆的话，先进行退出操作，在进行登陆操作
            else
            {
                LogPresenter.getInstance().setContext(context);
                //LogPresenter.getInstance().logTask(view,username,password,LOG_OUT);
                switch (isSwuWifi(context)) {
                    //dorm
                    case 0:
                        LogPresenter.getInstance().logTask(view, username, password, LOGIN_DORM);
                        break;
                    //class
                    case 1:
                        LogPresenter.getInstance().logTask(view, username, password, LOGIN_CLASS);
                        break;
                    //连接到校园网wifi，显示设置wifi界面
                    case -1:
                        showWifiSettings();
                        break;
                }
            }
        }
        //不存在就就启动相应的Activity进行登陆
        else
        {
                switch (isSwuWifi(context)) {
                    //dorm
                    case 0:
                        startActivity(context, context.getString(R.string.COUNT_LOGIN), context.getString(R.string.LOGIN), LOGIN_DORM);
                        break;
                    //class
                    case 1:
                        startActivity(context, context.getString(R.string.COUNT_LOGIN), context.getString(R.string.LOGIN), LOGIN_CLASS);
                        break;
                    //连接到校园网wifi，显示设置wifi界面
                    case -1:
                        showWifiSettings();
                        break;
                }
        }
    }

    /**判断是不是校园wifi的方法*/
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
    /**携带参数启动activity*/
    private void startActivity(Context context,String title,String button,int mode){
        Intent intent = new Intent(context, LogActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.TITLE), title);
        bundle.putString(context.getString(R.string.BUTTON), button);
        bundle.putInt(context.getString(R.string.MODE), mode);
        intent.putExtras(bundle);
        SwuActivity activity = (SwuActivity)context;
        activity.startActivityForResult(intent,0);
    }

    /**判断是否存在已经登陆的账号*/
    private boolean hasDefaultCount(Context context){
        SharedPreferences sp = context.getSharedPreferences(SPREF,0);
        return sp.getBoolean(HAS_COUNT,false);
    }

    /**显示wifi设置界面*/
    private void showWifiSettings(){
        Snackbar.make(view,context.getString(R.string.NOT_CONNECT_SWUWIFI),Snackbar.LENGTH_LONG).setActionTextColor(context.getResources().getColor(R.color.GREEN)).setAction(R.string.WIFI_CONNECT,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);
            }
        }).show();
    }
}
