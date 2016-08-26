package com.campus.huanjinzi.campusmvp.swu;

import android.view.View;

import com.campus.huanjinzi.campusmvp.BasePresenter;

/**
 * Created by huanjinzi on 2016/8/25.
 */
public interface ISwuPresenter {

    void showFragment(int position,View view);
    void login();
    void logout();

}
