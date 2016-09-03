package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.data.CXParams;
import com.campus.huanjinzi.campusmvp.data.local.Constants;
import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;

import org.junit.Test;

import java.io.InputStream;

/**
 * Created by huanjinzi on 2016/8/20.
 */
public class TranscriptTest {

    @Test
    public void getTranscript() throws Exception {
        CXParams params = new CXParams();
        params.setUsename("huanjinzi");
        params.setPassword("197325");
        params.setStudent_id("222013322270099");
        params.setYear("2015");
        params.setTerm("12");
        //CJBean cj = StudentSourceRemote.getInstance().getTranscript(params);
        Params p = Params.getInstance();
        p.setForm(Constants.getCjcxForm(params,0));
        p.setUrl(Constants.getCjcxUrl(params));
        InputStream in = HjzHttp.getInstance().post(p);
        StringBuilder sb = HjzStreamReader.getString(in);
        System.out.println(sb);
        //ArrayList<Items>cj.getItems();
        /*params.setYear("2014");
        params.setTerm("12");
        StudentSourceRemote.getInstance().getTranscript(params);*/
    }
}
