package com.campus.huanjinzi.campusmvp.swuwifi;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by huanjinzi on 2016/10/15.
 */

public class LogoutBean implements Serializable{

    private String result;
    private String message;

    public static LogoutBean objectFromData(String str) {

        return new Gson().fromJson(str, LogoutBean.class);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
