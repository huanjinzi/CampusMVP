package com.campus.huanjinzi.campusmvp.LogTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.campus.huanjinzi.campusmvp.LogConstants;
import com.campus.huanjinzi.campusmvp.MyApp;
import com.campus.huanjinzi.campusmvp.R;
import com.campus.huanjinzi.campusmvp.SwuTask.Flags;
import com.campus.huanjinzi.campusmvp.SwuTask.OnCreateDialogListener;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuActivity;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuFlags;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuPresenter;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.swuwifi.LoginBean;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

/**
 * Created by huanjinzi on 2016/9/3.
 */
public class LogPresenter {

    public static final String TAG = "LogPresenter ";
    private Handler mHandler;
    private TaskManager taskManager;
    private View view;
    private LogActivity activity;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;
    private AlertDialog dialog_validcode;
    private LoginBean login_bean;
    private LoginBean bean;

    private String musername;
    private String mpassword;

    public LogPresenter(Context context) {
        bean = new LoginBean();
        activity = (LogActivity) context;
        builder = new AlertDialog.Builder(activity);
        taskManager = TaskManager.getInstance();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                activity.sendBroadcast(new Intent(LogActivity.TASK_DONE));
                if (progressDialog != null){progressDialog.cancel();}
                if (msg.what == LogConstants.NETWORK_ERROR)
                {
                    snackbar(LogConstants.NETWORK_ERROR_STR);
                }
                else {
                    Bundle bundle = msg.getData();
                    /**通过handler来与ui线程交互*/
                    switch (bundle.getInt(LogConstants.MODE)) {
                        case LogConstants.LOG_IN:
                            login_bean = (LoginBean) bundle.getSerializable(LogConstants.RESULT);
                            loginResult(login_bean);
                            break;
                        case LogConstants.LOG_OUT:
                            logoutResult(bundle.getInt(LogConstants.RESULT));
                            break;
                        case LogConstants.VALIDCODE:
                            Bitmap bitmap = bundle.getParcelable(LogConstants.RESULT);
                            validcodeResult(bitmap);
                            break;
                    }
                }
            }
        };
    }

    public void logTask(View view, String username, String password, int mode) {

        this.view = view;
        musername = username;
        mpassword = password;
        taskManager.setHander(mHandler);
        switch (mode) {
            case LogConstants.LOG_IN:
                /**验证码处理*/
                if(login_bean != null && login_bean.getValidCodeUrl().length() > 3)
                {
                    bean.setValidCodeUrl("http://l.swu.edu.cn"+login_bean.getValidCodeUrl());
                    taskManager.getBitmap(bean.getValidCodeUrl());
                }
                else
                {
                    taskManager.login(username, password,"");
                }
                break;
            case LogConstants.LOG_OUT:
                taskManager.logout_all(username,password);
                break;
        }
    }

    /**
     * 对登陆的结果进行处理
     */
    private void loginResult(LoginBean result) {
        activity.sendBroadcast(new Intent(LogActivity.TASK_DONE));
        if (result.getResult().equals("success")) {
            saveUser();
        } else {
            loginFail(result);
        }
    }

    /**
     * 账号退出返回结果处理
     *
     * @param result 账号退出请求的返回结果，返回1成功，返回0失败，返回-1网络错误
     */
    private void logoutResult(int result) {
        activity.sendBroadcast(new Intent(LogActivity.TASK_DONE));
        switch (result) {
            case 0:
                snackbar(LogConstants.LOGOUT_FAIL_STR);
                break;
            case 1: {
                snackbar(LogConstants.LOGOUT_SUCCESS_STR);
                logoutSuccess();
            }
            break;
        }
    }

    private void validcodeResult(final Bitmap bitmap)
    {
        showDialog(new OnCreateDialogListener() {
            EditText editText = null;
            ImageView imageView = null;
            @Override
            public String getContent() {return "";}
            @Override
            public void onCancel() {}
            @Override
            public void onOk() {taskManager.login(musername,mpassword,editText.getText().toString().trim());}

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

    private void snackbar(String content){Snackbar.make(view, content, Snackbar.LENGTH_LONG).show();}
    /**保存用户名和密码和相关配置，个人信息查询，成绩查询*/
    private void saveUser()
    {
        SwuFlags.HAS_LOGED = true;
        SwuFlags.GREEN = true;
        SwuFlags.WATER = false;
        snackbar(LogConstants.LOGIN_SUCCESS_STR);
        SharedPreferences sp = activity.getSharedPreferences(MyApp.SPREF, 0);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(SwuPresenter.USERNAME, musername);
        editor.putString(SwuPresenter.PASSWORD, mpassword);
        editor.putBoolean(SwuPresenter.HAS_COUNT, true);
        editor.putBoolean(SwuPresenter.EX_COUNT, true);
        editor.commit();

        activity.finish();
    }
    /**登陆失败处理*/
    private void loginFail(LoginBean result)
    {
        if(result.getMessage().contains(LogConstants.HASLOGED_OTHER_PLACE ) || result.getMessage().contains(LogConstants.USERS_LIMITED) )
        {
            showDialog(new OnCreateDialogListener() {
                @Override
                public String getContent()
                {
                    return "\n你的账号已在其他地方登录,需要现在退出吗?";
                }
                @Override
                public void onCancel()
                {
                    Hlog.i(TAG,"onCancel");
                }

                @Override
                public void onOk()
                {
                    progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("退出中...");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    taskManager.logout_all(musername,mpassword);
                }

                @Override
                public void add(AlertDialog.Builder builder) {

                }
            });
        }
        else if(result.getMessage().contains("验证码错误"))
        {
            taskManager.getBitmap(bean.getValidCodeUrl());
        }
        else
        {

            Hlog.i(TAG,result.getMessage());
            snackbar(result.getMessage());
        }
    }

    /**退出成功处理*/
    private void logoutSuccess()
    {
        Flags.getInstance().setHAS_LOGED(false);
        if (activity.getSharedPreferences(MyApp.SPREF, 0).getString(SwuPresenter.USERNAME, "").trim().equals(musername.trim()))
        {
            activity.finishAfterTransition();
            Intent intent = new Intent(activity, SwuActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(SwuActivity.GREEN, false);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }
    }

    private void showDialog(final OnCreateDialogListener listener)
    {
        listener.add(builder);
        builder.setMessage(listener.getContent());
        builder.setCancelable(false);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.sendBroadcast(new Intent(LogActivity.TASK_DONE));
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
        if(dialog_validcode == null) {dialog_validcode =  builder.create();}
        dialog_validcode.show();
    }

}