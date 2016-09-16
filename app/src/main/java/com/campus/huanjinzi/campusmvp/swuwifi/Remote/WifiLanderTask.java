package com.campus.huanjinzi.campusmvp.swuwifi.Remote;

import com.campus.huanjinzi.campusmvp.data.local.Constants;
import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;
import com.campus.huanjinzi.campusmvp.utils.Hlog;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * Created by huanjinzi on 2016/8/16.
 * 校园网在教室和寝室登录wifi的具体实现类
 */
public class WifiLanderTask {

    private static final String TAG = "WifiLanderTask";
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
        InputStream in = HjzHttp.getInstance().post(params);
        StringBuilder sb = HjzStreamReader.getString(in,"gb2312");
        if(sb.toString().contains(Constants.NOT_LOGIN)){return true;}
        else if(sb.toString().contains(Constants.LOG_OUT_SUCCESS)){return true;}
        return false;
    }

    /*在寝室(Dorm)登录校园网wifi*/
    public int loginDorm(String username, String password) throws Exception {

        boolean status = false;
        params.setUrl(Constants.LOGIN_DORM_URL);
        params.setForm(Constants.getLoginDormForm(username, password));
        InputStream in = HjzHttp.getInstance().post(params);
        StringBuilder sb = HjzStreamReader.getString(in);
        status = sb.toString().contains(Constants.DORM_SUCCESS);
        if(status)
        {
            return 1;
        }
        status = sb.toString().contains(Constants.HAS_LOGED_DORRM);
        if(status)
        {
            return 0;
        }
        return -1;
    }
    /*在教室(Class)登录校园网wifi(GB2312)*/
    public int loginClass(String username, String password) throws Exception {
        params.setUrl(Constants.LOGIN_CLASS_URL);
        params.setForm(Constants.getLoginClassForm(username, password));
        InputStream in = HjzHttp.getInstance().post(params);
        if (in != null) in.close();
        params.setUrl(Constants.getLoginClassUrl(username));
        in = HjzHttp.getInstance().get(params);
        if(in == null){ return -1;}
        StringBuilder sb = HjzStreamReader.getString(in,"gb2312");
        if(sb.toString().contains(username)){
            return 1;
        }
        else if(sb.toString().contains(Constants.HAS_LOGED_CLASS)){
            return 0;
        }
        return -1;
    }
}
