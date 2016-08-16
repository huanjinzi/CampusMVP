package com.campus.huanjinzi.campusmvp.http;


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
    private int mContentlenth = -1;
    private String domain = null;
    private String hostname = null;
    private int i = 1;

    private static HjzHttp ourinstance = new HjzHttp();

    // TODO: 2016/8/14 实现HjzHttp对象的管理（类似线程池），单例模式存在线程安全问题，加锁会影响多线程性能 
    public static HjzHttp getInstance() {
        return ourinstance;
    }

    private HjzHttp() {

        cookiemap = new HashMap<>();
        HttpURLConnection.setFollowRedirects(false);
    }


    @Override
    public InputStream post(Params params) throws Exception {

        System.out.println("第" + i + "次");
        System.out.println(params.getUrl());
        i++;
        OutputStream os = null;
        InputStream in = null;

        try {
            URL url = new URL(params.getUrl());
            hostname = url.getHost();
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
                        System.out.println("set1->" + sb.toString());
                        con.setRequestProperty("Cookie", sb.toString());
                    }
                } else {
                    cookie = cookiemap.get(domain);
                    System.out.println("set2->" + cookie.getName() + "=" + cookie.getValue());
                    con.setRequestProperty("Cookie", cookie.getName() + "=" + cookie.getValue());
                }
            }

            con.setRequestMethod("POST");
            con.setReadTimeout(3 * 1000);
            con.setConnectTimeout(3 * 1000);

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
                        System.out.println("get1->" + httpcookie.getName() + "=" + httpcookie.getValue());
                    }
                    //没有的话就存储hostname和对应的cookie值
                    else {
                        System.out.println("get2->" + httpcookie.getName() + "=" + httpcookie.getValue());
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
        return in;
    }

    @Override
    public InputStream get(Params params) throws Exception {

        System.out.println("第" + i + "次");
        System.out.println(params.getUrl());
        i++;
        InputStream in = null;
        URL url = new URL(params.getUrl());
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
                    System.out.println("set1->" + sb.toString());
                    con.setRequestProperty("Cookie", sb.toString());
                }
            } else {
                cookie = cookiemap.get(domain);
                System.out.println("set2->" + cookie.getName() + "=" + cookie.getValue());
                con.setRequestProperty("Cookie", cookie.getName() + "=" + cookie.getValue());
            }
        }

        con.setRequestMethod("GET");
        con.setReadTimeout(3 * 1000);
        con.setConnectTimeout(3 * 1000);

        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");
        con.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");

        con.setDoInput(true);
        con.connect();


        if (con.getResponseCode() == 200 || con.getResponseCode() == 302) {

            for (String key :
                    con.getHeaderFields().keySet()) {
                System.out.println(key + "=" + con.getHeaderFields().get(key));
            }
            /*是否存在cookie*/
            mContentlenth = con.getContentLength();
            if (con.getHeaderFields().get("Set-Cookie") != null) {
                //cookie存在，获取cookie
                HttpCookie httpcookie = HttpCookie.parse(con.getHeaderFields().get("Set-Cookie").get(0)).get(0);
                //判断cookie有没有domain，有的话就存储domain和对应的cookie值--.swu.edu.cn
                if (httpcookie.getDomain() != null) {
                    domain = httpcookie.getDomain();
                    cookiemap.put(httpcookie.getDomain(), httpcookie);
                    System.out.println("get1->" + httpcookie.getName() + "=" + httpcookie.getValue());
                }
                //没有的话就存储hostname和对应的cookie值
                else {
                    System.out.println("get2->" + httpcookie.getName() + "=" + httpcookie.getValue());
                    cookiemap.put(hostname, httpcookie);
                }
            }
            in = con.getInputStream();

        }

        return in;
    }

    public int getContentLenth() {
        return mContentlenth;
    }
}
