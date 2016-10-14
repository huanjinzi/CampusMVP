package com.campus.huanjinzi.campusmvp.LogTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.SwuTask.Flags;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuActivity;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuPresenter;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

/**
 * Created by huanjinzi on 2016/9/3.
 */
public class LogPresenter {

    public static final String TAG = "LogPresenter";
    public void setContext(Context context) {
        this.context = context;
    }

    private Context context;
    private Handler mHander;
    private TaskManager taskManager;
    private View view;

    private String musername;
    private String mpassword;
    public LogPresenter(Context context) {
        this.context = context;

        taskManager = TaskManager.getInstance();
        mHander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                /**通过handler来与ui线程交互*/
                switch (bundle.getInt(SwuPresenter.MODE)) {

                    case SwuPresenter.LOG_OUT:
                        //logout登陆结果的处理
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

    public void logTask(View view, String username, String password, int mode) {
        this.view = view;
        musername = username;
        mpassword = password;

        taskManager.setHander(mHander);
        switch (mode) {
            case SwuPresenter.LOG_OUT:

                taskManager.logout(username, password, mode);
                break;
            case SwuPresenter.LOGIN_DORM:

                taskManager.login(username, password, mode);
                break;
            case SwuPresenter.LOGIN_CLASS:

                taskManager.login(username, password, mode);
                break;
        }
    }
    /**
     * 账号退出返回结果处理
     *
     * @param result 账号退出请求的返回结果，返回1成功，返回0失败，返回-1网络错误
     */
    private void logoutResult(int result) {
        context.sendBroadcast(new Intent(LogActivity.TASK_DONE));
        switch (result) {
            case 0:
                Snackbar.make(view, "退出账号失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case 1:
                {
                    Snackbar.make(view, "账号退出成功", Snackbar.LENGTH_LONG).show();
                    Flags.getInstance().setHAS_LOGED(false);

                    if(context.getSharedPreferences(MyApp.SPREF,0).getString(SwuPresenter.USERNAME,"").trim().equals(musername.trim()))
                    {
                        LogActivity activity = (LogActivity) context;
                        activity.finishAfterTransition();
                        Intent intent = new Intent(context,SwuActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(SwuActivity.GREEN,false);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
                break;
            case -1:
                Snackbar.make(view, "退出失败", Snackbar.LENGTH_LONG).show();
                break;
            case -2:
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }
    /**
     * 对登陆的结果进行处理
     */
    private void loginResult(int result) {
        context.sendBroadcast(new Intent(LogActivity.TASK_DONE));
        Intent intent;
        switch (result) {
            case 0:
                Snackbar.make(view, "账号在其他地方登陆,现在退出？", Snackbar.LENGTH_LONG).setActionTextColor(context.getResources().getColor(R.color.GREEN)).setAction("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taskManager.logout(musername, mpassword, SwuPresenter.LOG_OUT);
                    }
                }).show();
                break;
            case 1:

                SharedPreferences sp = context.getSharedPreferences(MyApp.SPREF, 0);
                SharedPreferences.Editor editor = sp.edit();

                editor.putString(SwuPresenter.USERNAME, musername);
                editor.putString(SwuPresenter.PASSWORD, mpassword);
                editor.putBoolean(SwuPresenter.HAS_COUNT, true);
                editor.putBoolean(SwuPresenter.EX_COUNT, true);
                editor.commit();

                LogActivity activity = (LogActivity) context;
                activity.finishAfterTransition();
                intent = new Intent(context,SwuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(SwuActivity.GREEN,true);
                bundle.putBoolean(SwuActivity.WATER,false);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case -1:
                Snackbar.make(view, "登陆失败，请确认用户名和密码正确，稍后再尝试登录!", Snackbar.LENGTH_LONG).show();
                break;
            case -2:
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

}