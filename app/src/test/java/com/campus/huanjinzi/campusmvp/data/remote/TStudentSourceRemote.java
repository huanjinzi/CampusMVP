package com.campus.huanjinzi.campusmvp.data.remote;

import com.campus.huanjinzi.campusmvp.data.CXParams;
import com.campus.huanjinzi.campusmvp.data.cjbean.CJBean;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.utils.MyLog;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;

/**
 * Created by huanjinzi on 2016/8/24.
 */
public class TStudentSourceRemote {

    public static CJBean getTranscript() throws Exception {

        InputStream in = new FileInputStream("G:\\campus\\CampusMVP\\app\\src\\test\\java\\com\\campus\\huanjinzi\\campusmvp\\raw\\cjbean.txt");
        StringBuilder sb = HjzStreamReader.getString(in, "gb2312");
        Gson gson = new Gson();
        SoftReference<CJBean> cj = new SoftReference<CJBean>(gson.fromJson(sb.toString(), CJBean.class));
        MyLog.log(sb.toString());
        // TODO: 2016/8/20 解析成绩： 1.正则表达式 2.开源json解析库
        //MyLog.log(getClass().getName(), "getBaseInfoMap", "exit()");
        return cj.get();
    }
}
