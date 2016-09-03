package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.data.CXParams;
import com.campus.huanjinzi.campusmvp.data.StudentCj;
import com.campus.huanjinzi.campusmvp.data.StudentInfo.DataBean.GetDataResponseBean.ReturnBean.BodyBean.ItemsBean;
import com.campus.huanjinzi.campusmvp.data.remote.StudentSourceRemote;

import org.junit.Test;

/**
 * Created by huanjinzi on 2016/8/16.
 */
public class StudentSourceRemoteTest {
    @Test
    public void getcj() throws Exception {
        StudentSourceRemote remote = StudentSourceRemote.getInstance();
        CXParams cxparams = new CXParams();
        cxparams.setUsename("huanjinzi");
        cxparams.setPassword("197325");
        ItemsBean info = remote.getStudentInfo(cxparams);
        StudentCj cj = remote.getTranscript(cxparams);
    }
}
