package com.campus.huanjinzi.campusmvp.SwuTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.campus.huanjinzi.campusmvp.AboutTask.AboutActivity;
import com.campus.huanjinzi.campusmvp.InfoTask.InfoActivity;
import com.campus.huanjinzi.campusmvp.LogConstants;
import com.campus.huanjinzi.campusmvp.LogTask.LogActivity;
import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.TranscriptTask.TranscriptActivity;
import com.campus.huanjinzi.campusmvp.swuwifi.LoginBean;
import com.campus.huanjinzi.campusmvp.swuwifi.LogoutBean;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

/**
 * Created by huanjinzi on 2016/8/25.
 */
public class SwuPresenter {

    public static final String TAG = "SwuPresenter";
    public static final String HAS_COUNT = "has_count";
    public static final String EX_COUNT = "ex_count";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String SWU_WIFI = "swu-wifi";
    public static final String SWU_WIFI_DORM = "swu-wifi-dorm";

    private String username;
    private String password;

    private SharedPreferences spref;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;
    private AlertDialog dialog_validcode;
    private TaskManager taskManager;
    private Handler mHandler;
    private LoginBean login_bean;
    private LoginBean bean;

    private View view;
    private SwuActivity activity;

    public void setView(View view) {
        this.view = view;
    }

    public SwuPresenter(Context context) {

        activity = (SwuActivity) context;
        bean = new LoginBean();
        spref = activity.getSharedPreferences(MyApp.SPREF, 0);
        taskManager = TaskManager.getInstance();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                SwuFlags.WATER = false;
                activity.refreshState();
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
                if (msg.what == LogConstants.NETWORK_ERROR) {
                    activity.refreshState();
                    snackbar(LogConstants.NETWORK_ERROR_STR);
                } else {
                    Bundle bundle = msg.getData();
                    switch (bundle.getInt(LogConstants.MODE)) {
                        case LogConstants.LOG_IN:
                            login_bean = (LoginBean) bundle.getSerializable(LogConstants.RESULT);
                            if(login_bean == null){
                                snackbar(LogConstants.NETWORK_ERROR_STR);
                            }
                            else
                            {
                                loginResult(login_bean);
                            }
                            break;
                        case LogConstants.LOG_OUT:
                            LogoutBean logoutBean = (LogoutBean) bundle.getSerializable(LogConstants.RESULT);
                            if(logoutBean == null){snackbar(LogConstants.NETWORK_ERROR_STR); }
                            else
                            {
                                logoutResult(logoutBean);
                            }
                            break;
                        case LogConstants.VALIDCODE:
                            Bitmap bitmap = bundle.getParcelable(LogConstants.RESULT);
                            validcodeResult(bitmap);
                    }
                }
            }
        };
    }

    /**
     * list启动页面管理
     */
    public void showFragment(int position) {
        Intent intent;
        switch (position) {
            case 0:
                if (isSwuWifi(activity) == -1) {
                    showWifiSettings();
                } else {
                    startActivity(activity, LogActivity.class, "账号退出", "退出", LogConstants.LOG_OUT);
                }
                break;

            case 1:
                LogActivityLauch();
                break;

            case 2:
                if (activity.getSharedPreferences(MyApp.SPREF, 0).getBoolean(SwuPresenter.HAS_COUNT, false)) {
                    intent = new Intent(activity, TranscriptActivity.class);
                    activity.startActivity(intent);
                } else {
                    Snackbar.make(view, "请登录账号后查询成绩", Snackbar.LENGTH_LONG).setActionTextColor(activity.getResources().getColor(R.color.GREEN)).setAction("现在登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LogActivityLauch();
                        }
                    }).show();
                }
                break;
            case 3:
                startActivity(activity, InfoActivity.class, "个人信息", null, 0);
                break;
            case 4:
                startActivity(activity, AboutActivity.class, "关于软件", null, 0);
                break;

        }
    }

    public void logTask() {

        if (isSwuWifi(activity) == -1) {
            SwuFlags.WATER = false;
            showWifiSettings();
        } else {
            taskManager.setHander(mHandler);
            //1.是否存在已经登陆的账户
            if (spref.getBoolean(HAS_COUNT, false)) {
                username = spref.getString(USERNAME, "");
                password = spref.getString(PASSWORD, "");

                if (SwuFlags.HAS_LOGED) {
                    taskManager.logout_all(username,password);
                } else {
                    /**验证码处理*/
                    if (login_bean != null && login_bean.getValidCodeUrl().length() > 3) {
                        bean.setValidCodeUrl("http://l.swu.edu.cn" + login_bean.getValidCodeUrl());
                        taskManager.getBitmap(bean.getValidCodeUrl());
                    } else {
                        taskManager.login(username, password, "");
                    }
                }
            } else {
                SwuFlags.WATER = false;
                LogActivityLauch();//之前没有账号登陆
            }
        }
    }

    /**
     * 对登陆的结果进行处理
     */
    private void loginResult(LoginBean result) {

        if (result.getResult().equals("success")) {
            // TODO: 2016/10/16 网络测试
            loginSuccess(result);
            //TaskManager.getInstance().NetTest();
        } else {
            loginFail(result);
        }
    }

    /***
     * 登陆成功处理
     */
    private void loginSuccess(LoginBean result) {
        snackbar(LogConstants.LOGIN_SUCCESS_STR);
        SwuFlags.HAS_LOGED = true;
        SwuFlags.GREEN = true;
        SharedPreferences.Editor editor = spref.edit();
        editor.putString(LogConstants.USERINDEX, result.getUserIndex());
        editor.commit();
        activity.refreshState();
    }

    /**
     * 登陆失败处理
     */
    private void loginFail(LoginBean result) {
        SwuFlags.WATER = false;
        activity.refreshState();
        if (result.getMessage().contains(LogConstants.HASLOGED_OTHER_PLACE) || result.getMessage().contains(LogConstants.USERS_LIMITED)) {
            showDialog(new OnCreateDialogListener() {
                @Override
                public String getContent() {
                    return "\n你的账号已在其他地方登录,需要现在退出吗?";
                }

                @Override
                public void onCancel() {
                    Hlog.i(TAG, "onCancel");
                }

                @Override
                public void onOk() {
                    progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("退出中...");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    taskManager.logout_all(username, password);
                }

                @Override
                public void add(AlertDialog.Builder builder) {

                }
            });
        } else if (result.getMessage().contains("验证码错误")) {
            taskManager.getBitmap(bean.getValidCodeUrl());
        } else {

            Hlog.i(TAG, result.getMessage());
            snackbar(result.getMessage());
        }
    }

    /**
     * 账号退出返回结果处理
     *
     * @param result 账号退出请求的返回结果，返回1成功，返回-1失败
     */
    private void logoutResult(LogoutBean result) {

        if (result.getResult().equals("success")) {
            logoutSuccess();
        } else {
            snackbar(LogConstants.LOGOUT_FAIL_STR);
        }
    }

    private void logoutSuccess() {
        SwuFlags.HAS_LOGED = false;
        SwuFlags.GREEN = false;
        activity.refreshState();
        snackbar(LogConstants.LOGOUT_SUCCESS_STR);
    }

    /**验证码处理*/
    private void validcodeResult(final Bitmap bitmap)
    {
        if(!dialog_validcode.isShowing()){
        showDialog(new OnCreateDialogListener() {
            EditText editText = null;
            ImageView imageView = null;
            @Override
            public String getContent() {return "";}
            @Override
            public void onCancel() {
                SwuFlags.WATER  =false;
                activity.refreshState();
            }
            @Override
            public void onOk() {
                SwuFlags.WATER = true;
                activity.refreshState();
                taskManager.login(username,password,editText.getText().toString().trim());}

            @Override
            public void add(AlertDialog.Builder builder) {
                View view = activity.getLayoutInflater().inflate(R.layout.validvode_dialog,null,false);
                editText = (EditText) view.findViewById(R.id.validvode_edittext);
                imageView = (ImageView) view.findViewById(R.id.validvode_bitmap);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taskManager.getBitmap(bean.getValidCodeUrl());
                    }
                });
                imageView.setImageBitmap(bitmap);
                builder.setView(view);
            }
        });
        }
        else
        {
            ImageView image = (ImageView) dialog_validcode.findViewById(R.id.validvode_bitmap);
            image.setImageBitmap(bitmap);
        }
    }

    private void showDialog(final OnCreateDialogListener listener) {
        builder = new AlertDialog.Builder(activity);
        listener.add(builder);
        builder.setMessage(listener.getContent());
        builder.setCancelable(false);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                listener.onCancel();
            }
        });
        builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onOk();
            }
        });
        dialog_validcode = builder.create();
        dialog_validcode.show();
    }

    private void snackbar(String content) {
        Snackbar.make(view, content, Snackbar.LENGTH_LONG).show();
    }

    /**
     * 判断是不是校园wifi的方法
     */
    public static int isSwuWifi(Context activity) {
        WifiManager wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info.getSSID().trim().contains(SWU_WIFI_DORM) || info.getSSID().trim().contains(SWU_WIFI)) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 显示wifi设置界面
     */
    private void showWifiSettings() {
        Snackbar.make(view, activity.getString(R.string.NOT_CONNECT_SWUWIFI), Snackbar.LENGTH_LONG).setActionTextColor(activity.getResources().getColor(R.color.GREEN)).
                setAction(R.string.WIFI_CONNECT, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        activity.startActivity(intent);
                    }
                }).show();
    }

    /**
     * 携带参数启动activity
     */
    private void startActivity(Context context, Class activity, String title, String button, int mode) {
        Intent intent = new Intent(context, activity);
        Bundle bundle = new Bundle();
        bundle.putString(context.getString(R.string.TITLE), title);
        bundle.putString(context.getString(R.string.BUTTON), button);
        bundle.putInt(context.getString(R.string.MODE), mode);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 启动输入账号的activity
     **/
    private void LogActivityLauch() {
        switch (isSwuWifi(activity)) {
            case 1:
                startActivity(activity, LogActivity.class, activity.getString(R.string.COUNT_LOGIN), activity.getString(R.string.LOGIN), LogConstants.LOG_IN);
                break;
            case -1:
                showWifiSettings();
                break;
        }
    }
}
