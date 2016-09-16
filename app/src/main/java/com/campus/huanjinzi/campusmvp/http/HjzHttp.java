package com.campus.huanjinzi.campusmvp.http;

import com.campus.huanjinzi.campusmvp.utils.Hlog;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class HjzHttp implements IHjzHttp {


    private HashMap<String, HttpCookie> cookiemap;
    private String hostname = null;

    private static HjzHttp ourinstance = new HjzHttp();

    // TODO: 2016/8/14 实现HjzHttp对象的管理（类似线程池），单例模式存在线程安全问题，加锁会影响多线程性能 
    public static HjzHttp getInstance() {
        return ourinstance;
    }

    private HjzHttp() {

        cookiemap = new HashMap<>();
        /*关闭重定向 302*/
        HttpURLConnection.setFollowRedirects(false);
    }
//https://uaaap.swu.edu.cn/cas/login?service=http://i.swu.edu.cn%2FPersonalApplications%2FviewPage%3Factive_nav_num%3D1&CTgtId=TGT-10442-Tui03eiPdb4lOdef0gUF1eGVdOQU2mUOyCNhHfd91ILAJjxmwi-http://222.198.120.206:8082/cas

    @Override
    public InputStream post(Params params) throws Exception {

        OutputStream os = null;
        InputStream in = null;

        try {
            URL url = new URL(params.getUrl());
            hostname = url.getHost();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            /*判断请求是否需要cookie，需要在setRequestMethod("POST")之前*/
            if (cookiemap != null) {
                HttpCookie cookie = null;
                if (cookiemap.get(hostname) != null) {

                    StringBuilder sb = new StringBuilder();
                    cookie = cookiemap.get(hostname);
                    sb.append(cookie.getName() + "=" + cookie.getValue());
                    con.setRequestProperty("Cookie", sb.toString());
                }

                con.setRequestProperty("Connection","keep-alive");
                con.setRequestMethod("POST");
                con.setReadTimeout(10 * 1000);
                con.setConnectTimeout(10 * 1000);

                con.setDoOutput(true);
                con.setDoInput(true);

                os = con.getOutputStream();
                if (params.getForm() != null) {
                    os.write(params.getForm().getBytes());
                }

                if (con.getResponseCode() == 200) {

                /*是否存在cookie*/
                    if (con.getHeaderFields().get("Set-Cookie") != null) {
                        //cookie存在，获取cookie
                        HttpCookie httpcookie = HttpCookie.parse(con.getHeaderFields().get("Set-Cookie").get(0)).get(0);
                        cookiemap.put(hostname, httpcookie);
                    }
                    in = con.getInputStream();
                }
            }
        } finally {
            if (os != null) {
                os.close();
            }
        }
        return in;
    }

    @Override
    public InputStream get(Params params) throws Exception {

        InputStream in = null;
        URL url = new URL(params.getUrl());
        hostname = url.getHost();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        /*判断请求是否需要cookie，需要在setRequestMethod("POST")之前*/
        if (cookiemap != null) {
            HttpCookie cookie = null;
            if (cookiemap.get(hostname) != null) {

                StringBuilder sb = new StringBuilder();
                cookie = cookiemap.get(hostname);
                sb.append(cookie.getName() + "=" + cookie.getValue());
                con.setRequestProperty("Cookie", sb.toString());
            }
        }

        con.setRequestMethod("GET");
        con.setReadTimeout(5 * 1000);
        con.setConnectTimeout(5 * 1000);

        con.setDoInput(true);
        con.connect();


        if (con.getResponseCode() == 200 || con.getResponseCode() == 302) {

            /*是否存在cookie*/
            if (con.getHeaderFields().get("Set-Cookie") != null) {
                //cookie存在，获取cookie
                HttpCookie httpcookie = HttpCookie.parse(con.getHeaderFields().get("Set-Cookie").get(0)).get(0);
                cookiemap.put(hostname, httpcookie);
            }
            in = con.getInputStream();
        }

        return in;
    }

}
