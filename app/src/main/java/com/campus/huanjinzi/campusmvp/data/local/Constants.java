package com.campus.huanjinzi.campusmvp.data.local;

import com.campus.huanjinzi.campusmvp.data.CXParams;

import java.util.Date;

/**
 * Created by huanjinzi on 2016/8/20.
 */
public final class Constants {

    /*1.final修饰的类不能被继承
    * 2.final修饰的方法不能被覆写
    * 3.局部匿名内部类使用局部变量时，局部变量要用final修饰
    * 4.final修饰的变量相当于一个常量*/

    /*私有化构造方法，避免被创建实例，只存在Constants.class实例*/
    private Constants() {
    }
    /*成绩查询url*/
    public static final String getCjcxUrl(CXParams params) {
        return "http://jw.swu.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdmKey=N305005&" +
                "sessionUserKey=" + params.getStudent_id();
    }

    /*教务系统成绩查询表单*/
    public static final String getCjcxForm(CXParams params, int time) {
        Date date = new Date();
        return "xnm=" + params.getYear() + "&xqm=" + params.getTerm() + "&_search=false&nd=" + date.getTime() + "&queryModel.showCount=100&queryModel.currentPage=1&queryModel.sortName=&queryModel.sortOrder=asc&time=" + time;
    }

    public static final String getLoginPostForm(String username, String password,String qurey,String validcode) {

        return "userId="+username+"&password="+password+"&service=%25E9%25BB%2598%25E8%25AE%25A4&queryString="+qurey+"&operatorPwd=&operatorUserId=&validcode="+validcode;
       }

    /*获取基本信息的 url*/
    public static final String YZSFWMH_URL = "http://i.swu.edu.cn/remote/service/process";

    /*获取学号的表单*/
    public static final String getIDForm(CXParams params) {
        return "serviceInfo=%7B%22serviceAddress%22%3A%22https%3A%2F%2Fuaaap.swu.edu.cn%2Fcas%2Fws%2FacpInfoManagerWS%22%2C%22serviceType%22%3A%22soap%22%2C%22serviceSource%22%3A%22td%22%2C%22paramDataFormat%22%3A%22xml%22%2C%22httpMethod%22%3A%22POST%22%2C%22soapInterface%22%3A%22getUserInfoByUserName%22%2C%22params%22%3A%7B%22userName%22%3A%22"+params.getUsename()+"%22%2C%22passwd%22%3A%22"+params.getPassword()+"%22%2C%22clientId%22%3A%22yzsfwmh%22%2C%22clientSecret%22%3A%221qazz%40WSX3edc%24RFV%22%2C%22url%22%3A%22http%3A%2F%2Fi.swu.edu.cn%22%7D%2C%22cDataPath%22%3A%5B%5D%2C%22namespace%22%3A%22%22%2C%22xml_json%22%3A%22%22%7D";
    }
    /*获取成绩的表单*/
    public static final String getCJForm(CXParams params) {
        return "serviceInfo=%7B%22serviceAddress%22%3A%22dataCenter2.0%2Fsoap%2Fds%2FdataService%22%2C%22serviceType%22%3A%22soap%22%2C%22serviceSource%22%3A%22ds%22%2C%22paramDataFormat%22%3A%22xml%22%2C%22httpMethod%22%3A%22POST%22%2C%22soapInterface%22%3A%22getData%22%2C%22params%22%3A%7B%22arg0%22%3A%7B%22Body%22%3A%7B%22tablename%22%3A%22dataout.v_bks_cj%22%2C%22desc%22%3A%22xnm%2Cxqdm%22%2C%22condition%22%3A%7B%22selects%22%3A%5B%7B%22select%22%3A%5B%7B%22column%22%3A%22xh%22%2C%22opt%22%3A%22EQ%22%2C%22value%22%3A%22"+params.getStudent_id()+"%22%7D%5D%7D%5D%7D%7D%7D%7D%2C%22cDataPath%22%3A%5B%22arg0%22%5D%2C%22namespace%22%3A%22%22%2C%22xml_json%22%3A%22%22%7D";}

    /*获取基本信息的表单*/
    public static final String getInfoForm(CXParams params) {
        return "serviceInfo=%7B%22serviceAddress%22%3A%22dataCenter2.0%2Fsoap%2Fds%2FdataService%22%2C%22serviceType%22%3A%22soap%22%2C%22serviceSource%22%3A%22ds%22%2C%22paramDataFormat%22%3A%22xml%22%2C%22httpMethod%22%3A%22POST%22%2C%22soapInterface%22%3A%22getData%22%2C%22params%22%3A%7B%22arg0%22%3A%7B%22Body%22%3A%7B%22tablename%22%3A%22dataout.v_xs_info%22%2C%22condition%22%3A%7B%22selects%22%3A%5B%7B%22select%22%3A%5B%7B%22column%22%3A%22id%22%2C%22opt%22%3A%22EQ%22%2C%22value%22%3A%22"+params.getStudent_id()+"%22%7D%5D%7D%5D%7D%7D%7D%7D%2C%22cDataPath%22%3A%5B%22arg0%22%5D%2C%22namespace%22%3A%22%22%2C%22xml_json%22%3A%22%22%7D";}

}
