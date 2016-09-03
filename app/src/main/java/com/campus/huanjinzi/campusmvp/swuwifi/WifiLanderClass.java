package com.campus.huanjinzi.campusmvp.swuwifi;

import com.campus.huanjinzi.campusmvp.swuwifi.Remote.WifiLanderTask;

/**
 * Created by huanjinzi on 2016/8/16.
 */
public class WifiLanderClass extends WifiLander {

    /**
     * 教室wifi登录方法
     *
     * @param username 校园网用户名
     * @param password 校园网密码
     * @return 返回true，登录成功；返回false，用户名或密码错误；Exception被捕获，连接服务器失败；
     */
    @Override
    public boolean login(String username, String password) throws Exception {

        return WifiLanderTask.getInstance().loginClass(username, password);
    }
}
