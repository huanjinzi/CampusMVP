package com.campus.huanjinzi.campusmvp.LogTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.campus.huanjinzi.campusmvp.SwuTask.SwuFlags;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuPresenter;
import com.campus.huanjinzi.campusmvp.TaskManager;
import com.campus.huanjinzi.campusmvp.swuwifi.LoginBean;
import com.campus.huanjinzi.campusmvp.swuwifi.LogoutBean;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

import static com.campus.huanjinzi.campusmvp.LogConstants.EX_COUNT;
import static com.campus.huanjinzi.campusmvp.LogConstants.HAS_COUNT;
import static com.campus.huanjinzi.campusmvp.LogConstants.LOGIN_SUCCESS_STR;
import static com.campus.huanjinzi.campusmvp.LogConstants.PASSWORD;
import static com.campus.huanjinzi.campusmvp.LogConstants.USERNAME;

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
    private SharedPreferences sp;

    private LoginBean login_bean;
    private LoginBean bean;

    private AlertDialog dialog_valid;
    private AlertDialog dialog_haslog;

    private EditText editText;
    private ImageView imageView;

    private String musername;
    private String mpassword;

    public LogPresenter(Context context) {
        bean = new LoginBean();
        activity = (LogActivity) context;
        taskManager = TaskManager.getInstance();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                activity.sendBroadcast(new Intent(LogActivity.TASK_DONE));
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
                if (msg.what == LogConstants.NETWORK_ERROR) {
                    snackbar(LogConstants.NETWORK_ERROR_STR);
                } else {
                    Bundle bundle = msg.getData();
                    /**通过handler来与ui线程交互*/
                    switch (bundle.getInt(LogConstants.MODE)) {
                        case LogConstants.LOG_IN:
                            login_bean = (LoginBean) bundle.getSerializable(LogConstants.RESULT);
                            if (login_bean == null) {
                                snackbar(LogConstants.NETWORK_ERROR_STR);
                            } else {
                                loginResult(login_bean);
                            }
                            break;
                        case LogConstants.LOG_OUT:
                            logoutResult((LogoutBean) bundle.getSerializable(LogConstants.RESULT));
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
        sp = activity.getSharedPreferences(MyApp.SPREF,0);
        taskManager.setHander(mHandler);
        switch (mode) {
            case LogConstants.LOG_IN:
                /**验证码处理*/
                if (login_bean != null && login_bean.getValidCodeUrl() != null && login_bean.getValidCodeUrl().length() > 3) {
                    bean.setValidCodeUrl(activity.getString(R.string.HTTP_SWU) + login_bean.getValidCodeUrl());
                    taskManager.getBitmap(bean.getValidCodeUrl());
                } else {
                    taskManager.login(username, password, "");
                }
                break;
            case LogConstants.LOG_OUT:
                taskManager.logout_all(username, password);
                break;
        }
    }

    /**
     * 对登陆的结果进行处理
     */
    private void loginResult(LoginBean result) {
        if (result.getResult() != null && result.getResult().equals(activity.getString(R.string.SUCCESS))) {
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
    private void logoutResult(LogoutBean result) {
        if (result.getResult().equals("success")) {
            snackbar(result.getMessage());

            if( musername.equals(sp.getString(USERNAME,"")) )
            {
                SwuFlags.WATER = false;
                SwuFlags.GREEN = false;
            }
        } else {
            snackbar(result.getMessage());
        }
    }

    private void validcodeResult(final Bitmap bitmap) {
        showValidDialog(activity,bitmap);
    }

    private void snackbar(String content) {
        Snackbar.make(view, content, Snackbar.LENGTH_LONG).show();
    }

    /**
     * 保存用户名和密码和相关配置，个人信息查询，成绩查询
     */
    private void saveUser() {
        SwuFlags.HAS_LOGED = true;
        SwuFlags.GREEN = true;
        SwuFlags.WATER = false;
        snackbar(LOGIN_SUCCESS_STR);
        SharedPreferences sp = activity.getSharedPreferences(MyApp.SPREF, 0);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(USERNAME, musername);
        editor.putString(PASSWORD, mpassword);
        editor.putBoolean(HAS_COUNT, true);
        editor.putBoolean(EX_COUNT, true);
        editor.commit();

        activity.finish();
    }

    /**
     * 登陆失败处理
     */
    private void loginFail(LoginBean result) {
        if ((result.getMessage().contains(LogConstants.HASLOGED_OTHER_PLACE) || result.getMessage().contains(LogConstants.USERS_LIMITED))) {
            showHas(activity);
        } else if (result.getMessage().contains(activity.getString(R.string.VALIDCODE_ERROR))) {
            editText.setText("");
            editText.setHintTextColor(activity.getResources().getColor(R.color.RED));
            editText.setHint("验证码错误");
            taskManager.getBitmap(bean.getValidCodeUrl());
        } else {
            if(editText != null)
            {
                editText.setText("");
                editText.setHintTextColor(Color.GRAY);
                editText.setHint("输入验证码");
            }
            Hlog.i(TAG, result.getMessage());
            snackbar(result.getMessage());
        }
    }

    private void showValidDialog(Context context,Bitmap bitmap) {

        AlertDialog.Builder builder = null;

        if(dialog_valid == null) {
            if (builder == null) {builder = new AlertDialog.Builder(context);}
            builder.setMessage("验证码");
            builder.setCancelable(false);
            View view = activity.getLayoutInflater().inflate(R.layout.validvode_dialog, null, false);
            editText = (EditText) view.findViewById(R.id.validvode_edittext);
            imageView = (ImageView) view.findViewById(R.id.validvode_bitmap);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar(activity.getString(R.string.REFRESH_VALIDCODE));
                    taskManager.getBitmap(bean.getValidCodeUrl());
                }});
            imageView.setImageBitmap(bitmap);
            builder.setView(view);

            builder.setPositiveButton(activity.getString(R.string.CANCEL), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.sendBroadcast(new Intent(LogActivity.TASK_DONE));
                    dialog.cancel();
                }
            });
            builder.setNegativeButton(activity.getString(R.string.OK), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.sendBroadcast(new Intent(LogActivity.TASK_START));
                    taskManager.login(musername,mpassword,editText.getText().toString().trim());
                }});
            dialog_valid = builder.create();
            dialog_valid.show();
        }
        else
        {
            imageView.setImageBitmap(bitmap);
            dialog_valid.show();
        }
    }

    public void showHas(Context context) {
        /**dialog_haslog = null 就创建*/
        if (dialog_haslog == null) {

            AlertDialog.Builder builder = null;
            if (builder == null) {
                builder = new AlertDialog.Builder(context);
            }
            builder.setMessage(context.getString(R.string.HAS_LOGED_OTHER_PLACE));
            builder.setCancelable(false);
            builder.setPositiveButton(activity.getString(R.string.CANCEL), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(activity.getString(R.string.OK), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage(activity.getString(R.string.OUTING));
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    taskManager.logout_all(musername, mpassword);
                }
            });
            dialog_haslog = builder.create();
            dialog_haslog.show();
        }
        else
        {

            dialog_haslog.show();
        }
    }
}