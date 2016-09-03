package com.campus.huanjinzi.campusmvp.SwuTask;

import android.content.Context;
import android.view.View;

/**
 * Created by huanjinzi on 2016/8/25.
 */
public interface ISwuPresenter {

    void showFragment(Context context,int position);
    void logTask(Context context,View v);
    void login();
    void logout();

}
