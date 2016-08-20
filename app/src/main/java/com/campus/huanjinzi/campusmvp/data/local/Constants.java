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

    /*校内门户登录认证url*/
    public static final String XNMH_VALIDATE_URL = "http://urp.swu.edu.cn/userPasswordValidate.portal";

    /*校内门户登录的表单*/
    public static final String getBaseInfoForm(CXParams params) {
        return "Login.Token1=" + params.getUsename() + "&Login.Token2=" + params.getPassword() + "&" +
                "goto=http://urp.swu.edu.cn/loginSuccess.portal&" +
                "gotoOnFail=http://urp.swu.edu.cn/loginFailure.portal";
    }

    /*校内门户首页的url*/
    public static final String XNMH_INDEX_URL = "http://urp.swu.edu.cn/index.portal";

    /*学生基本信息url*/
    public static final String BASE_INFO_URL = "http://urp.swu.edu.cn/index.portal?.pn=p85";

    /*教务系统首页url*/
    public static final String JWXT_INDEX_URL = "http://jw.swu.edu.cn/jwglxt/idstar/index.jsp";

    /*教务系统登录认证url*/
    public static final String JWXT_VALIDATE_URL = "http://jw.swu.edu.cn/jwglxt/xtgl/login_Ddlogin.html";

    /*成绩查询url*/
    public static final String getCjcxUrl(CXParams params) {
        return "http://jw.swu.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdmKey=N305005&" +
                "sessionUserKey=" + params.getStudent_id();
    }

    /*成绩查询表单*/
    public static final String getCjcxForm(CXParams params, int time) {
        Date date = new Date();
        return "xnm=" + params.getYear() + "&xqm=" + params.getTerm() + "&_search=false&nd=" + date.getTime() + "&queryModel.showCount=100&queryModel.currentPage=1&queryModel.sortName=&queryModel.sortOrder=asc&time=" + time;
    }

    /*退出校园网url*/
    public static final String LOGOUT_URL = "http://service.swu.edu.cn/fee/remote_logout2.jsp";

    /*退出校园网表单*/
    public static final String getLogoutForm(String username, String password) {
        return "username=" + username + "&password=" + password + "&B1=确认";
    }

    /*在寝室(Dorm)登录校园网wifi url*/
    public static final String LOGIN_DORM_URL = "http://222.198.120.8:8080/loginPhoneServlet";

    /*在寝室(Dorm)登录校园网wifi表单*/
    public static final String getLoginDormForm(String username, String password) {
        Date date = new Date();
        return "loginTime=" + date.getTime() + "&username=" + username + "&password" + password;
    }

    /*在教室(Class)登录校园网wifi url*/
    public static final String LOGIN_CLASS_URL = "http://202.202.96.57:9010/login/login1.jsp";

    public static final String getLoginClassUrl(String username) {
        return "http://202.202.96.59:9080/index.jsp?username=" + username;
    }

    /*在教室(Class)登录校园网wifi表单*/
    public static final String getLoginClassForm(String username, String password) {
        return "username=" + username + "&password=" + password + "&if_login=Y&B2=%B5%C7%C2%BC%28Login%29";
    }
}
