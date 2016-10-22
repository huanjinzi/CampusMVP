package com.campus.huanjinzi.campusmvp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.campus.huanjinzi.campusmvp.LogTask.LogActivity;
import com.campus.huanjinzi.campusmvp.SwuTask.SwuFlags;
import com.campus.huanjinzi.campusmvp.swuwifi.LoginBean;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

/**
 * Created by huanjinzi on 2016/10/21.
 */

public class LogTaskPresenter {

    public static final String TAG = "LogTaskPresenter";

    private String username;
    private String password;

    private AlertDialog dialog_valid;
    private AlertDialog dialog_haslog;

    private EditText editText;
    private ImageView imageView;

    private View view;

    private ProgressDialog progressDialog;


    private Activity activity;
    private TaskManager taskManager;

    private LoginBean login_bean;
    private LoginBean bean;

    private void loginFail(LoginBean result) {

        if (result.getMessage().contains(LogConstants.HASLOGED_OTHER_PLACE) || result.getMessage().contains(LogConstants.USERS_LIMITED)) {
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

    private void showValidDialog(Context context, Bitmap bitmap) {

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
                    taskManager.login(username,password,editText.getText().toString().trim());
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
                    taskManager.logout_all(username, password);
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

    private void snackbar(String content) {
        Snackbar.make(view, content, Snackbar.LENGTH_LONG).show();
    }

}
