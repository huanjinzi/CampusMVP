package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.swuwifi.Remote.WifiLanderTask;

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
            System.out.println(e.getMessage());
            is = false;
        }
        System.out.println(is);
    }
}
