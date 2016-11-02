package com.campus.huanjinzi.campusmvp.swuwifi.Remote;

import com.campus.huanjinzi.campusmvp.data.local.Constants;
import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;
import com.campus.huanjinzi.campusmvp.swuwifi.LoginBean;
import com.campus.huanjinzi.campusmvp.swuwifi.LogoutBean;
import com.campus.huanjinzi.campusmvp.swuwifi.SwuServiceBean;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huanjinzi on 2016/10/15.
 */

public class SwuWifiLandTask {
    private Params params = Params.getInstance();
    private static final SwuWifiLandTask task = new SwuWifiLandTask();

    private SwuWifiLandTask() {
    }

    public static SwuWifiLandTask getInstance() {
        return task;
    }

    public LoginBean login(String username, String password, String validcode) throws Exception {
        //http://123.123.123.123/
        params.setUrl("http://www.baidu.com");
        InputStream in = HjzHttp.getInstance().get(params);
        StringBuilder sb = HjzStreamReader.getString(in);

        if (sb.toString().contains("百度一下"))
        {
            LoginBean login = new LoginBean();
            login.setResult("success");
            login.setMessage("登录成功!");
            return login;
        }
        else if (sb.toString().contains("http://222.198.127.170/eportal/index.jsp")) {

            String qurey = sb.substring(73, sb.length() - 12);

            /*url两次编码*/
            qurey = URLEncoder.encode(qurey, "utf-8");
            qurey = URLEncoder.encode(qurey, "utf-8");

            params.setUrl("http://222.198.127.170/eportal/InterFace.do?method=login");
            params.setForm(Constants.getLoginPostForm(username, password, qurey, validcode));

            in = HjzHttp.getInstance().post(params);
            sb = HjzStreamReader.getString(in);
        }
        return LoginBean.objectFromData(sb.toString());
    }

    public LogoutBean logout(String userIndex) throws Exception {
        params.setUrl("http://login.swu.edu.cn/eportal/InterFace.do?method=logout");
        params.setForm(userIndex);
        InputStream in = HjzHttp.getInstance().post(params);
        StringBuilder sb = HjzStreamReader.getString(in, "GBK");
        return LogoutBean.objectFromData(sb.toString());
    }

    public String logout_all(String username, String password) throws Exception {
        StringBuilder sb = null;
        params.setUrl("http://service2.swu.edu.cn/selfservice/module/scgroup/web/login_judge.jsf");
        params.setForm("name=" + username + "&password=" + password);
        InputStream in = HjzHttp.getInstance().setCookie("rmbUser=true; userName=" + username + "; passWord=" + password + ";oldpassWord=" + password).post(params);
        in.close();
        params.setUrl("http://service2.swu.edu.cn/selfservice/module/userself/web/userself_ajax.jsf?methodName=indexBean.refreshSelfIndexData");
        in = HjzHttp.getInstance().get(params);
        sb = HjzStreamReader.getString(in, "GBK");
        SwuServiceBean bean = SwuServiceBean.objectFromData(sb.toString());

        if (bean.getOnlineNum() >= 1) {
            params.setUrl("http://service2.swu.edu.cn/selfservice/module/webcontent/web/onlinedevice_list.jsf");
            in = HjzHttp.getInstance().get(params);
            sb = HjzStreamReader.getString(in, "GBK");

            params.setUrl("http://service2.swu.edu.cn/selfservice/module/userself/web/userself_ajax.jsf?methodName=indexBean.kickUserBySelfForAjax");
            params.setForm("key=" + username + ":" + getIP(sb.toString()));
            in = HjzHttp.getInstance().post(params);
            sb = HjzStreamReader.getString(in, "GBK");
            if (sb.toString().contains("下线成功")) {
                return "success";
            } else {
                return "fail";
            }
        }
        return "success";
    }

    private String getIP(String str) {
        Pattern pattern = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
        Matcher matcher = pattern.matcher(str);
        matcher.find();

        return matcher.group();
    }
}
