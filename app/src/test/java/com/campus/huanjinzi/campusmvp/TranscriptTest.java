package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.data.CXParams;
import com.campus.huanjinzi.campusmvp.data.remote.StudentSourceRemote;

import org.junit.Test;

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
        StudentSourceRemote.getInstance().getTranscript(params);
        params.setYear("2014");
        params.setTerm("3");
        StudentSourceRemote.getInstance().getTranscript(params);
    }
}
