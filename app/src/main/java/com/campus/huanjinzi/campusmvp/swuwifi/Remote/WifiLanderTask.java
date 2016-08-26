package com.campus.huanjinzi.campusmvp.swuwifi.Remote;

import com.campus.huanjinzi.campusmvp.data.local.Constants;
import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;
import com.campus.huanjinzi.campusmvp.utils.MyLog;
import java.io.InputStream;
import java.net.URLDecoder;

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
        boolean status = false;
        params.setUrl(Constants.LOGIN_DORM_URL);
        params.setForm(Constants.getLoginDormForm(username, password));
        InputStream in = HjzHttp.getInstance().post(params);
        StringBuilder sb = HjzStreamReader.getString(in);
        MyLog.log(sb.toString());
        try {
            status = sb.substring(4100,4400).contains(URLDecoder.decode(Constants.DORM_SUCCESS,"utf-8"));
        }
        catch (Exception e) {
            if (e instanceof StringIndexOutOfBoundsException) {
                status = false;
            }
        }
        return status;
    }
    /*在教室(Class)登录校园网wifi(GB2312)*/
    public boolean loginClass(String username, String password) throws Exception {

        boolean status = false;
        params.setUrl(Constants.LOGIN_CLASS_URL);
        params.setForm(Constants.getLoginClassForm(username, password));
        InputStream in = HjzHttp.getInstance().post(params);
        if (in != null) in.close();
        params.setUrl(Constants.getLoginClassUrl(username));
        in = HjzHttp.getInstance().get(params);
        if (in == null) return false;
        StringBuilder sb = HjzStreamReader.getString(in,"gb2312");
        try {
            status = sb.substring(1500).contains(username);
        }
        catch (Exception e) {
            if (e instanceof StringIndexOutOfBoundsException) {
                status = false;
            }
        }
        return status;
    }
}
