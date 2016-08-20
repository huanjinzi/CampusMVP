package com.campus.huanjinzi.campusmvp.utils;

/**
 * Created by huanjinzi on 2016/8/20.
 */
public final class MyLog {

    private MyLog() {

    }

    public static void log(String sclass, String smethod, String msg) {
        System.out.println(sclass + "." + smethod + "()");
        System.out.println(msg + "\n");
    }

    public static void log(String msg) {
        System.out.println(msg + "\n");
    }
}
