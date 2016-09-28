package com.campus.huanjinzi.campusmvp.swuwifi;

import com.campus.huanjinzi.campusmvp.swuwifi.Remote.WifiLanderTask;

/**
 * Created by huanjinzi on 2016/8/13.
 * 校园wifi登录最顶层的类，logout方法已经实现，login分别由WifiLanderDorm和WifiLanderClass实现各自
 * 的登录，具体登录细节都由WifiLanderTask类实现，具体参见WifiLanderTask类
 */
public abstract class WifiLander {

    /**
     * 校园网wifi退出的方法
     *
     * @param username 校园网用户名
     * @param password 校园网密码
     * @return 返回true，登录成功；Exception被捕获，连接服务器失败；由于是登录成功之后才会退出，所以用
     * 户名和密码一定是正确的，只要连接服务器成功就退出成功了
     */
    public static int logout(String username, String password) throws Exception {

        return WifiLanderTask.getInstance().logout(username, password);
    }

    /**
     * wifi登录方法
     *
     * @param username 校园网用户名
     * @param password 校园网密码
     * @return 返回true，登录成功；返回false，用户名或密码错误；Exception被捕获，连接服务器失败；
     */
    public abstract int login(String username, String password) throws Exception;
}
