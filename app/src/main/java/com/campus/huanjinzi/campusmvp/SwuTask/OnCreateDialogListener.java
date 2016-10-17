package com.campus.huanjinzi.campusmvp.SwuTask;

import android.support.v7.app.AlertDialog;

/**
 * Created by huanjinzi on 2016/10/16.
 */

public interface OnCreateDialogListener {
     String getContent();
    void onCancel();
    void onOk();
    void add(AlertDialog.Builder builder);
}
