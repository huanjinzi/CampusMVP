package com.campus.huanjinzi.campusmvp.http;

import com.campus.huanjinzi.campusmvp.utils.MyLog;
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
    private String domain = null;
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


    @Override
    public InputStream post(Params params) throws Exception {
        MyLog.log(this.getClass().getName(), "post", "in()");
        MyLog.log(this.getClass().getName(), "post", params.getUrl());
        OutputStream os = null;
        InputStream in = null;

        try {
            URL url = new URL(params.getUrl());
            hostname = url.getHost();
            MyLog.log(hostname);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            /*判断请求是否需要cookie，需要在setRequestMethod("POST")之前*/
            if (cookiemap != null && cookiemap.get(domain) != null) {
                HttpCookie cookie = null;
                if (cookiemap.get(hostname) != null) {
                    if (hostname.contains(domain)) {
                        cookie = cookiemap.get(domain);
                        StringBuilder sb = new StringBuilder();
                        sb.append(cookie.getName() + "=" + cookie.getValue() + ";");

                        cookie = cookiemap.get(hostname);
                        sb.append(cookie.getName() + "=" + cookie.getValue());
                        MyLog.log("Request-Cookie:" + sb.toString());
                        con.setRequestProperty("Cookie", sb.toString());
                    }
                } else {
                    cookie = cookiemap.get(domain);
                    MyLog.log("Request-Cookie:" + cookie.getName() + "=" + cookie.getValue());
                    con.setRequestProperty("Cookie", cookie.getName() + "=" + cookie.getValue());
                }
            }

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
                /*for (String key:
                        con.getHeaderFields().keySet()) {
                    System.out.println(key +"="+con.getHeaderFields().get(key));
                }*/
                /*是否存在cookie*/
                if (con.getHeaderFields().get("Set-Cookie") != null) {
                    //cookie存在，获取cookie
                    HttpCookie httpcookie = HttpCookie.parse(con.getHeaderFields().get("Set-Cookie").get(0)).get(0);
                    //判断cookie有没有domain，有的话就存储domain和对应的cookie值--.swu.edu.cn
                    if (httpcookie.getDomain() != null) {
                        domain = httpcookie.getDomain();
                        cookiemap.put(httpcookie.getDomain(), httpcookie);
                        MyLog.log("Response-Cookie:" + httpcookie.getName() + "=" + httpcookie.getValue());
                    }
                    //没有的话就存储hostname和对应的cookie值
                    else {
                        MyLog.log("Response-Cookie:" + httpcookie.getName() + "=" + httpcookie.getValue());
                        cookiemap.put(hostname, httpcookie);
                    }
                }
                in = con.getInputStream();
            }
        } finally {
            if (os != null) {
                os.close();
            }
        }
        MyLog.log(this.getClass().getName(), "post", "exit()");
        return in;
    }

    @Override
    public InputStream get(Params params) throws Exception {

        MyLog.log(this.getClass().getName(), "get", "in()");
        MyLog.log(this.getClass().getName(), "get", params.getUrl());
        InputStream in = null;
        URL url = new URL(params.getUrl());
        hostname = url.getHost();
        MyLog.log(hostname);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        /*判断请求是否需要cookie，需要在setRequestMethod("POST")之前*/
        if (cookiemap != null && cookiemap.get(domain) != null) {
            HttpCookie cookie = null;
            if (cookiemap.get(hostname) != null) {
                if (hostname.contains(domain)) {
                    cookie = cookiemap.get(domain);
                    StringBuilder sb = new StringBuilder();
                    sb.append(cookie.getName() + "=" + cookie.getValue() + ";");

                    cookie = cookiemap.get(hostname);
                    sb.append(cookie.getName() + "=" + cookie.getValue());
                    MyLog.log("Request-Cookie:" + sb.toString());
                    con.setRequestProperty("Cookie", sb.toString());
                }
            } else {
                cookie = cookiemap.get(domain);
                MyLog.log("Request-Cookie:" + cookie.getName() + "=" + cookie.getValue());
                con.setRequestProperty("Cookie", cookie.getName() + "=" + cookie.getValue());
            }
        }

        con.setRequestMethod("GET");
        con.setReadTimeout(5 * 1000);
        con.setConnectTimeout(5 * 1000);

        con.setDoInput(true);
        con.connect();


        if (con.getResponseCode() == 200 || con.getResponseCode() == 302) {

            /*for (String key :
                    con.getHeaderFields().keySet()) {
                System.out.println(key + "=" + con.getHeaderFields().get(key));
            }*/

            /*是否存在cookie*/
            if (con.getHeaderFields().get("Set-Cookie") != null) {
                //cookie存在，获取cookie
                HttpCookie httpcookie = HttpCookie.parse(con.getHeaderFields().get("Set-Cookie").get(0)).get(0);
                //判断cookie有没有domain，有的话就存储domain和对应的cookie值--.swu.edu.cn
                if (httpcookie.getDomain() != null) {
                    domain = httpcookie.getDomain();
                    cookiemap.put(httpcookie.getDomain(), httpcookie);
                    MyLog.log("Response-Cookie:" + httpcookie.getName() + "=" + httpcookie.getValue());

                }
                //没有的话就存储hostname和对应的cookie值
                else {
                    MyLog.log("Response-Cookie:" + httpcookie.getName() + "=" + httpcookie.getValue());
                    cookiemap.put(hostname, httpcookie);
                }
            }
            in = con.getInputStream();

        }
        MyLog.log(this.getClass().getName(), "get", "exit()");
        return in;
    }

}
