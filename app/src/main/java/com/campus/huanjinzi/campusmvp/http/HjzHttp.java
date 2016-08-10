package com.campus.huanjinzi.campusmvp.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class HjzHttp implements IHjzHttp {

    private int StreamLenth = -1;//post或者get得到的InputStream的长度

    public int getStreamLenth() {
        return StreamLenth;
    }

    @Override
    public InputStream post(Params params) throws Exception {

        OutputStream os = null;
        InputStream in = null;

        try {
            URL url = new URL(params.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setReadTimeout(4 * 1000);
            con.setConnectTimeout(6 * 1000);

            con.setDoOutput(true);
            con.setDoInput(true);

            os = con.getOutputStream();
            os.write(params.getForm().getBytes());

            if (con.getResponseCode() == 200) {
                StreamLenth = con.getContentLength();
                in = con.getInputStream();
            }
        } finally {
            os.close();
        }
        return in;
    }

    @Override
    public InputStream get(Params params) throws Exception {

        InputStream in = null;
        URL url = new URL(params.getUrl());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setReadTimeout(4 * 1000);
        con.setConnectTimeout(6 * 1000);

        con.setDoInput(true);
        con.connect();

        if (con.getResponseCode() == 200) {
            StreamLenth = con.getContentLength();
            in = con.getInputStream();
        }

        return in;
    }
}
