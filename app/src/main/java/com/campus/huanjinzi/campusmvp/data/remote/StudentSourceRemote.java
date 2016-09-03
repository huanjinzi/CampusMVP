package com.campus.huanjinzi.campusmvp.data.remote;

import com.campus.huanjinzi.campusmvp.data.CXParams;
import com.campus.huanjinzi.campusmvp.data.ResponseBean;
import com.campus.huanjinzi.campusmvp.data.StudentCj;
import com.campus.huanjinzi.campusmvp.data.StudentInfo;
import com.campus.huanjinzi.campusmvp.data.StudentInfo.DataBean.GetDataResponseBean.ReturnBean.BodyBean.ItemsBean;
import com.campus.huanjinzi.campusmvp.data.local.Constants;
import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;

import java.io.InputStream;

/**
 * Created by huanjinzi on 2016/8/12.
 */
public class StudentSourceRemote  {

    private static StudentSourceRemote ourInstance = new StudentSourceRemote();
    private Params params = Params.getInstance();
    private HjzHttp hjz = HjzHttp.getInstance();
    private  StringBuilder sb;
    private InputStream in;

    private StudentSourceRemote() {
    }
    public static StudentSourceRemote getInstance() {
        return ourInstance;
    }

    /**获取基本信息*/
    public ItemsBean getStudentInfo(CXParams cjcxParams) throws Exception {

        params.setUrl(Constants.YZSFWMH_URL);
        params.setForm(Constants.getIDForm(cjcxParams));

        in = hjz.post(params);
        sb = HjzStreamReader.getString(in);

        ResponseBean responseBean = ResponseBean.objectFromData(sb.toString());
        cjcxParams.setStudent_id(responseBean.getData().getGetUserInfoByUserNameResponse().getReturnX().getInfo().getId());
        params.setForm(Constants.getInfoForm(cjcxParams));

        in = hjz.post(params);
        sb = HjzStreamReader.getString(in);
        StudentInfo info = StudentInfo.objectFromData(sb.toString());

        return info.getData().getGetDataResponse().getReturnX().getBody().getItems().get(0);
    }

    /**获取成绩的json数据*/
    public StudentCj getTranscript(CXParams cjcxParams) throws Exception {

        params.setForm(Constants.getCJForm(cjcxParams));
        in = hjz.post(params);
        sb  = HjzStreamReader.getString(in);
        return StudentCj.objectFromData(sb.toString());
    }
}
