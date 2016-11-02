package com.campus.huanjinzi.campusmvp.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class HjzHttp implements IHjzHttp {

    private StringBuilder cookie = new StringBuilder();
    public HjzHttp setCookie(String cookie) {this.cookie.append(cookie);return ourinstance;}
    private static HjzHttp ourinstance = new HjzHttp();

    // TODO: 2016/8/14 实现HjzHttp对象的管理（类似线程池），单例模式存在线程安全问题，加锁会影响多线程性能 
    public static HjzHttp getInstance() {
        return ourinstance;
    }

    private HjzHttp() {
        /*关闭重定向 302*/
        HttpURLConnection.setFollowRedirects(false);
    }

    @Override
    public InputStream post(Params params) throws Exception {

        OutputStream os = null;
        InputStream in = null;

        try {
            URL url = new URL(params.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            /*判断请求是否需要cookie，需要在setRequestMethod("POST")之前*/
            if (cookie.length() != 0) {
                con.setRequestProperty("Cookie",cookie.toString());
            }
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestMethod("POST");
            con.setReadTimeout(7 * 1000);
            con.setConnectTimeout(7 * 1000);

            con.setDoOutput(true);
            con.setDoInput(true);

            con.connect();

            os = con.getOutputStream();
            if (params.getForm() != null) {
                os.write(params.getForm().getBytes());
            }

            if (con.getResponseCode() == 200) {

                if(con.getHeaderFields().get("Set-Cookie") != null){
                    if(cookie.length() < 2){
                        cookie.append(con.getHeaderFields().get("Set-Cookie").get(0));
                    }
                    else {
                        cookie.append(";"+con.getHeaderFields().get("Set-Cookie").get(0));
                    }

                }
                    in = con.getInputStream();
            }

        }
        finally {
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
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        /*判断请求是否需要cookie，需要在setRequestMethod("POST")之前*/
        if (cookie != null) {
            con.setRequestProperty("Cookie", cookie.toString());

        }

        con.setRequestMethod("GET");
        con.setReadTimeout(5 * 1000);
        con.setConnectTimeout(5 * 1000);

        con.setDoInput(true);
        con.connect();

        if (con.getResponseCode() == 200 || con.getResponseCode() == 302) {
            in = con.getInputStream();
        }
        return in;
    }
}
