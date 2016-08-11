package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.http.HjzHttp;
import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;
import com.campus.huanjinzi.campusmvp.http.Params;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class HjzHttpTest {

    public static String sendGet(String url, String param, String cookie) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Cookie", cookie);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, String _cookie) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String cookie = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection uconn = realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) uconn;
            // 设置通用的请求属性
            if (_cookie != null) {
                conn.setRequestProperty("Cookie", _cookie);
            }

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Cookie","");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {

                if ((null != key) && key.equals("Set-Cookie")) {
                    cookie = map.get(key).get(0);
                    break;
                }
//                System.out.println(key + "--->" + map.get(key));
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return cookie;
    }

    @Test
    public void testgetdetail() {
        //http://urp.swu.edu.cn/userPasswordValidate.portal

        String cookie = sendPost("http://urp.swu.edu.cn/userPasswordValidate.portal",
                "Login.Token1=huanjinzi&" + "Login.Token2=197325&" +
                        "goto=http%3A%2F%2Furp.swu.edu.cn%2FloginSuccess.portal&" +
                        "gotoOnFail=http%3A%2F%2Furp.swu.edu.cn%2FloginFailure.portal", null);
        cookie = cookie.substring(0, cookie.indexOf(";"));
        System.out.println("cookie = " + cookie);
        //http://jw.swu.edu.cn/jwglxt/xtgl/login_Ddlogin.html
        /*sendGet("http://jw.swu.edu.cn/jwglxt/idstar/index.jsp",null,cookie);
        sendGet("http://jw.swu.edu.cn/jwglxt/xtgl/login_Ddlogin.html",null,cookie);*/
        String result = sendGet("http://urp.swu.edu.cn/index.portal", null, cookie);
        System.out.print("result = " + result);

    }

    @Test
    public void post() throws Exception {
        HjzHttp hjz = new HjzHttp();
        Params params = new Params();

        //params.setUrl("http://service.swu.edu.cn/fee/remote_logout2.jsp"); 退出校园网登录参数
        //String form = "username=huanjinzi&password=197325&B1=确认";

        params.setUrl("http://urp.swu.edu.cn/userPasswordValidate.portal");
        String form = "Login.Token1=huanjinzi&Login.Token2=197325&" +
                "goto=http://urp.swu.edu.cn/loginSuccess.portal&" +
                "gotoOnFail=http://urp.swu.edu.cn/loginFailure.portal";
        params.setForm(form);

        InputStream in = hjz.post(params);
        if (in != null) {
            in.close();
        }
        params.setUrl("http://jw.swu.edu.cn/jwglxt/idstar/index.jsp");
        params.setForm(null);
        in = hjz.get(params);
        if (in != null) {
            in.close();
        }

        params.setUrl(" http://jw.swu.edu.cn/jwglxt/xtgl/login_Ddlogin.html");
        in = hjz.get(params);
        //if(in != null){in.close();}

        StringBuilder sb = HjzStreamReader.getString(in, "UTF-8");
        System.out.println(sb);


    }

    @Test
    public void urltest() throws Exception {
        URL url = new URL(".urp.swu.edu.cn");
        System.out.println(url.getHost());

    }

    @Test
    public void StringBuildertest() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("s");
        sb.append("\n");
        sb.append("b");
        System.out.println(sb.toString());

    }

    @Test
    public void get() throws Exception {
        HjzHttp hjz = new HjzHttp();
        Params params = new Params();
        params.setUrl("http://www.swu.edu.cn");
        InputStream in = hjz.get(params);
        StringBuilder sb = HjzStreamReader.getString(in, "gb2312");
        System.out.println(sb);

    }
}
