package com.campus.huanjinzi.campusmvp;


import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;

import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class HjzHttpTest {

    @Test
    public void urppost() throws Exception {

        HashMap<String, String> map = new HashMap<>();
        HjzHttp hjz = HjzHttp.getInstance();
        Params params = Params.getInstance();

        //params.setUrl("http://service.swu.edu.cn/fee/remote_logout2.jsp"); 退出校园网登录参数
        //String form = "username=huanjinzi&password=197325&B1=确认";
        //http://urp.swu.edu.cn/userPasswordValidate.portal
        params.setUrl("http://urp.swu.edu.cn/userPasswordValidate.portal");
        String form = "Login.Token1=huanjinzi&Login.Token2=19325&" +
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

        StringBuilder sb = HjzStreamReader.getString(in);
        //StringBuilder sb = HjzStreamReader.getString(in,18646,20000);

        System.out.println(sb);

        Pattern p1 = Pattern.compile("<b>[\\u4E00-\\u9FA5]+");
        Pattern p2 = Pattern.compile("&nbsp;[0-9\\u4E00-\\u9FA5A-Z]+");

        Matcher m1 = p1.matcher(sb);
        Matcher m2 = p2.matcher(sb);

        while (m2.find() && m1.find()) {
            map.put(m1.group().substring(3), m2.group().substring(6));

        }

        for (String key :
                map.keySet()) {
            System.out.println(key + "=" + map.get(key));
        }


        /*      <b>学号
                <b>姓名
                <b>民族
                <b>性别
                <b>政治面貌
                <b>身份证
                <b>学院
                <b>专业
                <b>类别*/

    }

    @Test
    public void jwpost() throws Exception {
        HjzHttp hjz = HjzHttp.getInstance();
        Params params = Params.getInstance();

        //params.setUrl("http://service.swu.edu.cn/fee/remote_logout2.jsp"); 退出校园网登录参数
        //String form = "username=huanjinzi&password=197325&B1=确认";

        params.setUrl("http://urp.swu.edu.cn/userPasswordValidate.portal");
        String form = "Login.Token1=huanjinzi&Login.Token2=197325&" +
                "goto=http://urp.swu.edu.cn/loginSuccess.portal&" +
                "gotoOnFail=http://urp.swu.edu.cn/loginFailure.portal";
        params.setForm(form);

        InputStream in = hjz.post(params);
        if (in != null) {
            in.close();
        }
        params.setUrl("http://jw.swu.edu.cn/jwglxt/idstar/index.jsp");
        params.setForm(null);
        in = hjz.get(params);
        if (in != null) {
            in.close();
        }

        params.setUrl(" http://jw.swu.edu.cn/jwglxt/xtgl/login_Ddlogin.html");
        in = hjz.get(params);
        if (in != null) {
            in.close();
        }

        Date date = new Date();
        params.setUrl("http://jw.swu.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdmKey=N305005&" +
                "sessionUserKey=" + "222013322270099");
        params.setForm("xnm=2015&xqm=12&_search=false&nd=" + date.getTime() + "&queryModel.showCount=15&queryModel.currentPage=1&queryModel.sortName=&queryModel.sortOrder=asc&time=0");
        in = HjzHttp.getInstance().post(params);
        //if(in != null){in.close();}
        StringBuilder sb = HjzStreamReader.getString(in, "UTF-8");
        System.out.println(sb);


    }
    @Test
    public void get() throws Exception {
        HjzHttp hjz = HjzHttp.getInstance();
        Params params = Params.getInstance();
        params.setUrl("http://cn.bing.com/");
        InputStream in = hjz.get(params);
        StringBuilder sb = HjzStreamReader.getString(in);
        System.out.println(sb);

    }

    @Test
    public void github()throws Exception{
        HjzHttp hjz = HjzHttp.getInstance();
        Params params = Params.getInstance();
        params.setUrl("http://github.com/huanjinzi/CampusMVP/blob/master/app/src/main/java/com/campus/huanjinzi/campusmvp/data/StudentSource.java");
        InputStream in = hjz.get(params);
        StringBuilder sb = HjzStreamReader.getString(in, "gb2312");
        System.out.println(sb);
    }
}
