package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.swuwifi.Remote.SwuWifiLandTask;
import com.campus.huanjinzi.campusmvp.swuwifi.Remote.WifiLanderTask;
import com.campus.huanjinzi.campusmvp.utils.Hlog;

import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by huanjinzi on 2016/8/16.
 */
public class WifiLanderTaskTest {

    @Test
    public void loginClass() throws Exception {

    }

    @Test
    public void logout() throws Exception {

        SwuWifiLandTask task = SwuWifiLandTask.getInstance();
        task.logout_all("huanjinzi","197325");
    }

    @Test
    public void url()throws Exception{
        String url = URLDecoder.decode("userId=huanjinzi&password=197325&service=%25E9%25BB%2598%25E8%25AE%25A4&queryString=wlanuserip%253D27d5e2cac58389a92b07baca5aa617ee%2526wlanacname%253Dc3d7ed6d307ae29d%2526ssid%253D46be4f158ac727af%2526nasip%253Df9dbb3fe11a1f4e3b5cce4a65fc79cf9%2526mac%253D9bca081b48d1f514ce2f43e9408158aa%2526t%253Dwireless-v2%2526url%253D720c545b4e107cdd144930fa89edaf88f2ce18d55abc293a&operatorPwd=&operatorUserId=&validcode=");
        System.out.println(url);
    }

    @Test
    public void logout1()throws Exception{
        SwuWifiLandTask task = SwuWifiLandTask.getInstance();
        task.logout_all("huanjinzi","197325");
    }

    //http://service2.swu.edu.cn/selfservice/module/userself/web/userself_ajax.jsf?methodName=indexBean.kickUserBySelfForAjax
}
