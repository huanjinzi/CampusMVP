package com.campus.huanjinzi.campusmvp.swuwifi;

import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.Params;

/**
 * Created by huanjinzi on 2016/8/13.
 */
public abstract class WifiLander {

    /**
     * wifi退出方法
     *
     * @param username 校园网用户名
     * @param password 校园网密码
     * @return 返回true，登录成功；返回false，用户名或密码错误；Exception被捕获，连接服务器失败
     */

    public boolean logout(String username, String password) throws Exception {

        Params params = new Params();
        params.setUrl("http://service.swu.edu.cn/fee/remote_logout2.jsp");
        params.setForm("username=" + username + "&password=" + password + "&B1=确认");

        return false;
    }

    /**
     * wifi登录方法
     *
     * @param username 校园网用户名
     * @param password 校园网密码
     * @return 返回true，登录成功；返回false，连接服务器失败；返回null，用户名或密码错误
     */

    abstract boolean login(String username, String password) throws Exception;
}
