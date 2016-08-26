package com.campus.huanjinzi.campusmvp.swu;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by huanjinzi on 2016/8/25.
 */
public class SwuPresenter implements ISwuPresenter{

    @Override
    public void showFragment(int position, View view) {

        Snackbar.make(view, position+"", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }

}
