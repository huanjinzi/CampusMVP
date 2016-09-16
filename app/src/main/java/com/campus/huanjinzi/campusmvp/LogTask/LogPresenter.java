package com.campus.huanjinzi.campusmvp.LogTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.R;
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
        Hlog.i(TAG,"(业务LogTask)username = "+username);
        Hlog.i(TAG,"(业务LogTask)password = "+password);
        taskManager.setHander(mHander);
        switch (mode) {
            case SwuPresenter.LOG_OUT:
                Hlog.i(TAG,"(业务LogTask),进行退出操作");
                taskManager.logout(username, password, mode);
                break;
            case SwuPresenter.LOGIN_DORM:
                Hlog.i(TAG,"(业务LogTask),login dorm");
                taskManager.login(username, password, mode);
                break;
            case SwuPresenter.LOGIN_CLASS:
                Hlog.i(TAG,"(业务LogTask),login class");
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

        switch (result) {
            case 0:
                Hlog.i(TAG,"(业务Logout),退出账号失败，请稍后重试");
                Snackbar.make(view, "退出账号失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case 1:
                {
                    Snackbar.make(view, "账号退出成功", Snackbar.LENGTH_LONG).show();
                    Hlog.i(TAG,"(业务Logout),账号退出成功");
                    LogActivity activity = (LogActivity) context;
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    if(context.getSharedPreferences(MyApp.SPREF,0).getString(SwuPresenter.USERNAME,"").trim().equals(musername.trim()))
                    {
                        bundle.putBoolean(SwuPresenter.LOGOUT_SUCCESS,true);
                        bundle.putBoolean(SwuPresenter.LOGOUT_SUCCESS_SAME,true);
                        Hlog.i(TAG,"(业务Logout),账号退出成功,LOGOUT_SUCCESS_SAME = true,tabs变成灰色背景");
                    }
                    else
                    {
                        bundle.putBoolean(SwuPresenter.LOGOUT_SUCCESS,true);
                        bundle.putBoolean(SwuPresenter.LOGOUT_SUCCESS_SAME,false);
                        Hlog.i(TAG,"(业务Logout),账号退出成功,LOGOUT_SUCCESS_SAME = false");
                    }
                    intent.putExtras(bundle);
                    activity.setResult(0,intent);
                    Hlog.i(TAG,"(业务Logout),账号退出成功，activity.finish");

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
                Hlog.i(TAG,"(业务Login),登陆成功");
                SharedPreferences sp = context.getSharedPreferences(MyApp.SPREF, 0);
                SharedPreferences.Editor editor = sp.edit();
                 {
                    editor.putString(SwuPresenter.USERNAME, musername);
                    editor.putString(SwuPresenter.PASSWORD, mpassword);
                    editor.putBoolean(SwuPresenter.HAS_COUNT, true);
                    editor.putBoolean(SwuPresenter.HAS_LOGED, true);
                    editor.commit();
                     Hlog.i(TAG,"(业务Login),登陆成功，保存musername ="+musername);
                     Hlog.i(TAG,"(业务Login),登陆成功，保存mpassword ="+mpassword);
                    intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(SwuPresenter.SUCCESS, true);
                    intent.putExtras(bundle);
                    LogActivity activity = (LogActivity) context;
                    activity.setResult(0, intent);
                     Hlog.i(TAG,"(业务Login),登陆成功，activity.finish()");
                    activity.finish();
                }
                break;
            case -1:
                Snackbar.make(view, "登陆失败，请稍后重试", Snackbar.LENGTH_LONG).show();
                break;
            case -2:
                Snackbar.make(view, "网络超时", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

}