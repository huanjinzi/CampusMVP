package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.swuwifi.Remote.WifiLanderTask;
import com.campus.huanjinzi.campusmvp.utils.MyLog;

import org.junit.Test;

/**
 * Created by huanjinzi on 2016/8/16.
 */
public class WifiLanderTaskTest {

    @Test
    public void loginClass() throws Exception {
        boolean is = false;
        try {
            is = WifiLanderTask.getInstance().loginClass("huanjinzi", "197325");
        } catch (Exception e) {

            MyLog.log(e.getClass().getName());
            is = false;
        }
        System.out.println(is);
    }

    @Test
    public void loginDorm() throws Exception {
        boolean is = false;
        try {
            is = WifiLanderTask.getInstance().loginDorm("huanjinzi", "197325");
        } catch (Exception e) {
            MyLog.log(e.getClass().getName());
            is = false;
        }
        System.out.println(is);
    }

    @Test
    public void logout() throws Exception {

            WifiLanderTask.getInstance().logout("huanjinzi", "197325");
    }
}
