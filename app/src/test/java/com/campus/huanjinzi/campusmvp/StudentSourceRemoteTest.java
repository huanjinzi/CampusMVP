package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;

import org.junit.Test;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huanjinzi on 2016/8/16.
 */
public class StudentSourceRemoteTest {
    @Test
    public void layout1() throws Exception {
        HjzHttp hjz = HjzHttp.getInstance();
        Params params = new Params();
        params.setUrl("http://service.swu.edu.cn/fee/remote_logout2.jsp");
        params.setForm("username=huanjinzi&password=197325&B1=确认");
        InputStream in = hjz.post(params);
        StringBuilder sb = HjzStreamReader.getString(in, "gb2312");

        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]{4}[a-zA-Z]+\\d*");
        Matcher matcher = pattern.matcher(sb);

        matcher.find();
        System.out.println(matcher.group());
    }
}
