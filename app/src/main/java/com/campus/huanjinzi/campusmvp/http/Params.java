package com.campus.huanjinzi.campusmvp.http;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class Params {

    private String url = null;
    private String form = null;//请求提交的表单
    private String cookie = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
