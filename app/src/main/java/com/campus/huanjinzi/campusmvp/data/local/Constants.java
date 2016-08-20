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

    /*校内门户登录认证*/
    public static final String XNMH_VALIDATE_URL = "http://urp.swu.edu.cn/userPasswordValidate.portal";

    /*校内门户登录的POST表单，对表单进行拼接的方法*/
    public static final String getBaseInfoForm(CXParams params) {
        return "Login.Token1=" + params.getUsename() + "&Login.Token2=" + params.getPassword() + "&" +
                "goto=http://urp.swu.edu.cn/loginSuccess.portal&" +
                "gotoOnFail=http://urp.swu.edu.cn/loginFailure.portal";
    }

    /*校内门户首页的URL*/
    public static final String XNMH_INDEX_URL = "http://urp.swu.edu.cn/index.portal";

    /*学生基本信息*/
    public static final String BASE_INFO_URL = "http://urp.swu.edu.cn/index.portal?.pn=p85";

    /*教务系统首页地址*/
    public static final String JWXT_INDEX_URL = "http://jw.swu.edu.cn/jwglxt/idstar/index.jsp";

    /*教务系统登录认证*/
    public static final String JWXT_VALIDATE_URL = "http://jw.swu.edu.cn/jwglxt/xtgl/login_Ddlogin.html";

    /*成绩查询url*/
    public static final String getCjcxUrl(CXParams params) {
        return "http://jw.swu.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdmKey=N305005&" +
                "sessionUserKey=" + params.getStudent_id();
    }

    public static final String getCjcxForm(CXParams params, int time) {
        Date date = new Date();
        return "xnm=" + params.getYear() + "&xqm=" + params.getTerm() + "&_search=false&nd=" + date.getTime() + "&queryModel.showCount=100&queryModel.currentPage=1&queryModel.sortName=&queryModel.sortOrder=asc&time=" + time;
    }

}
