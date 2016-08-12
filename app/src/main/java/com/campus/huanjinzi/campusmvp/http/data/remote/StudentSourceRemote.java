package com.campus.huanjinzi.campusmvp.http.data.remote;

import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;
import com.campus.huanjinzi.campusmvp.http.data.StudentSource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huanjinzi on 2016/8/12.
 */
public class StudentSourceRemote implements StudentSource {

    @Override
    public HashMap<String, String> getBaseInfoMap(String username, String password) throws Exception {

        HashMap<String, String> map = null;
        HjzHttp hjz = new HjzHttp();
        Params params = new Params();
        params.setUrl("http://urp.swu.edu.cn/userPasswordValidate.portal");
        String form = "Login.Token1=" + username + "&Login.Token2=" + password + "&" +
                "goto=http://urp.swu.edu.cn/loginSuccess.portal&" +
                "gotoOnFail=http://urp.swu.edu.cn/loginFailure.portal";
        params.setForm(form);
        InputStream in = hjz.post(params);
        if (in != null) {
            in.close();
        }
        params.setUrl("http://urp.swu.edu.cn/index.portal");
        params.setForm(null);
        in = hjz.get(params);
        if (in != null) {
            in.close();
        }

        params.setUrl("http://urp.swu.edu.cn/index.portal?.pn=p85");
        in = hjz.get(params);

        StringBuilder sb = HjzStreamReader.getString(in, 18646, 20000);

        Pattern p1 = Pattern.compile("<b>[\\u4E00-\\u9FA5]+");
        Pattern p2 = Pattern.compile("&nbsp;[0-9\\u4E00-\\u9FA5A-Z]+");

        Matcher m1 = p1.matcher(sb);
        Matcher m2 = p2.matcher(sb);

        while (m2.find() && m1.find()) {
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(m1.group().substring(3), m2.group().substring(6));

        }

        /*for (String key:
                map.keySet()) {
            System.out.println(key+"="+map.get(key));
        }*/


        /*      <b>学号
                <b>姓名
                <b>民族
                <b>性别
                <b>政治面貌
                <b>身份证
                <b>学院
                <b>专业
                <b>类别*/
        return map;
    }
}
