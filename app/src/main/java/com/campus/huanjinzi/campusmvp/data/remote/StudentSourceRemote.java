package com.campus.huanjinzi.campusmvp.data.remote;

import com.campus.huanjinzi.campusmvp.data.CXParams;
import com.campus.huanjinzi.campusmvp.data.StudentSource;
import com.campus.huanjinzi.campusmvp.data.Transcript;
import com.campus.huanjinzi.campusmvp.data.local.Constants;
import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;
import com.campus.huanjinzi.campusmvp.utils.MyLog;

import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huanjinzi on 2016/8/12.
 */
public class StudentSourceRemote implements StudentSource {

    private static StudentSourceRemote ourInstance = new StudentSourceRemote();
    private Params params = Params.getInstance();
    private HjzHttp hjz = HjzHttp.getInstance();
    private int time = 0;

    private StudentSourceRemote() {
    }
    public static StudentSourceRemote getInstance() {
        return ourInstance;
    }

    /*获取基本信息*/
    @Override
    public HashMap<String, String> getBaseInfoMap(CXParams cjcxParams) throws Exception {
        MyLog.log(this.getClass().getName(), "getBaseInfoMap", "in()");
        HashMap<String, String> map = null;
        InputStream in = null;

        validate(cjcxParams, in);
        //专门用来发送流不需读取的请求
        send(Constants.XNMH_INDEX_URL, in);

        params.setUrl(Constants.BASE_INFO_URL);
        in = hjz.get(params);
        StringBuilder sb = HjzStreamReader.getString(in, 18646, 20500);

        /*匹配“<b>学号”格式的字符串*/
        Pattern p1 = Pattern.compile("<b>[\\u4E00-\\u9FA5]+");
        Pattern p2 = Pattern.compile("&nbsp;[0-9\\u4E00-\\u9FA5A-Z]+");
        Matcher m1 = p1.matcher(sb);
        Matcher m2 = p2.matcher(sb);

        /*Matcher的find()方法返回的是一个boolean值，返回为true则表示找到对应的字符串模式
        * 再调用Matcher的group()返回找到的字符串*/
        while (m2.find() && m1.find()) {
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(m1.group().substring(3), m2.group().substring(6));
        }
        /*for (String key:map.keySet())
        {
            System.out.println(key+"="+map.get(key));
        }*/
        return map;
    }

    public Transcript getTranscript(CXParams cjcxParams) throws Exception {

        InputStream in = null;
        if (time == 0) {
            validate(cjcxParams, in);
            //专门用来发送流不需读取的请求
            send(Constants.JWXT_INDEX_URL, in);
            send(Constants.JWXT_VALIDATE_URL, in);
        }
        params.setUrl(Constants.getCjcxUrl(cjcxParams));
        params.setForm(Constants.getCjcxForm(cjcxParams, time));
        time++;
        in = HjzHttp.getInstance().post(params);
        StringBuilder sb = HjzStreamReader.getString(in, "UTF-8");
        // TODO: 2016/8/20 解析成绩： 1.正则表达式 2.开源json解析库
        System.out.println(sb);
        MyLog.log(this.getClass().getName(), "getBaseInfoMap", "exit()");
        return null;
    }

    /*password validate*/
    private void validate(CXParams cjcxParams, InputStream in) throws Exception {
        params.setUrl(Constants.XNMH_VALIDATE_URL);
        params.setForm(Constants.getBaseInfoForm(cjcxParams));
        in = hjz.post(params);
        close(in);
    }

    /*发送指定url请求,专门用来发送流不需读取的请求*/
    private void send(String url, InputStream in) throws Exception {
        params.setUrl(url);
        in = hjz.get(params);
        close(in);
    }

    /*流不为空则关闭流*/
    private void close(InputStream in) throws Exception {
        if (in != null) {
            in.close();
        }
    }
}
