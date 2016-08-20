package com.campus.huanjinzi.campusmvp.swuwifi.Remote;

import android.util.Xml;

import com.campus.huanjinzi.campusmvp.data.local.Constants;
import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;

import java.io.InputStream;
import java.security.spec.EncodedKeySpec;
import java.util.Date;

/**
 * Created by huanjinzi on 2016/8/16.
 * 校园网在教室和寝室登录wifi的具体实现类
 */
public class WifiLanderTask {

    private Params params = Params.getInstance();
    private static WifiLanderTask wifiLanderTask = new WifiLanderTask();

    public static WifiLanderTask getInstance() {
        return wifiLanderTask;
    }

    private WifiLanderTask() {
    }

    /*退出校园网登录*/
    public boolean logout(String username, String password) throws Exception {
        params.setUrl(Constants.LOGOUT_URL);
        params.setForm(Constants.getLogoutForm(username, password));
        HjzHttp.getInstance().post(params).close();
        return true;
    }

    /*在寝室(Dorm)登录校园网wifi*/
    public boolean loginDorm(String username, String password) throws Exception {
        //// TODO: 2016/8/20 还没有进行功能测试
        params.setUrl(Constants.LOGIN_DORM_URL);
        params.setForm(Constants.getLoginDormForm(username, password));
        InputStream in = HjzHttp.getInstance().post(params);
        HjzStreamReader.getString(in);
        return false;
    }

    /*在教室(Class)登录校园网wifi(GB2312)*/
    public boolean loginClass(String username, String password) throws Exception {

        params.setUrl(Constants.LOGIN_CLASS_URL);
        params.setForm(Constants.getLoginClassForm(username, password));
        InputStream in = HjzHttp.getInstance().post(params);
        if (in != null) in.close();
        params.setUrl(Constants.getLoginClassUrl(username));
        in = HjzHttp.getInstance().get(params);
        if (in == null) return false;
        //StringBuilder sb = HjzStreamReader.getString(in,"gb2312");
        StringBuilder sb = HjzStreamReader.getString(in, "gb2312", null, 2850, 2900);
        return sb.toString().contains(username);
    }
}
