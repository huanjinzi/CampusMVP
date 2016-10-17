package com.campus.huanjinzi.campusmvp.swuwifi;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by huanjinzi on 2016/10/15.
 */

public class LoginBean implements Serializable{

    private String userIndex;
    private String result;
    private String message;
    private int keepaliveInterval;
    private String validCodeUrl;

    public static LoginBean objectFromData(String str) {

        return new Gson().fromJson(str, LoginBean.class);
    }

    public String getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(String userIndex) {
        this.userIndex = userIndex;
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

    public int getKeepaliveInterval() {
        return keepaliveInterval;
    }

    public void setKeepaliveInterval(int keepaliveInterval) {
        this.keepaliveInterval = keepaliveInterval;
    }

    public String getValidCodeUrl() {
        return validCodeUrl;
    }

    public void setValidCodeUrl(String validCodeUrl) {
        this.validCodeUrl = validCodeUrl;
    }
}
