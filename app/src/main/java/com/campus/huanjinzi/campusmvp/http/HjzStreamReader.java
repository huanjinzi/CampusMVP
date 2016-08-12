package com.campus.huanjinzi.campusmvp.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class HjzStreamReader {

    public static StringBuilder getString(InputStream in) throws Exception {
        return getString(in, "utf-8", null, 0, -1);
    }

    public static StringBuilder getString(InputStream in, String charset) throws Exception {
        return getString(in, charset, null, 0, -1);
    }

    public static StringBuilder getString(InputStream in, int start, int end) throws Exception {

        return getString(in, "utf-8", null, start, end);
    }

    public static StringBuilder getString(InputStream in, String charset, String encode, int start, int end) throws Exception {


        int len = 0;
        int contentlenth = end - start;
        //加一次保证读到需要的长度(读够)
        int times = contentlenth / 1024 + 1;
        //skip误差保证读到需要的长度
        long deviation = start - in.skip(start);
        StringBuilder sb = new StringBuilder();
        GZIPInputStream gzipreader = null;
        InputStreamReader reader = null;
        try {
            if (end == -1) {
                if (encode != null && encode.toLowerCase().contains("gzip")) {

                    byte[] bytebuf = new byte[1024];
                    gzipreader = new GZIPInputStream(in);

                    while ((len = gzipreader.read(bytebuf)) != -1) {
                        sb.append(new String(bytebuf, 0, len));
                        // TODO: 2016/8/10 进度读取，需要知道返回值的长度
                    }
                } else {

                    char[] buf = new char[1024];
                    reader = new InputStreamReader(in, charset);
                    while ((len = reader.read(buf)) != -1) {
                        sb.append(buf, 0, len);
                        // TODO: 2016/8/10 进度读取，需要知道返回值的长度
                    }
                }
            } else {

                if (encode != null && encode.toLowerCase().contains("gzip")) {

                    byte[] bytebuf = new byte[1024];
                    gzipreader = new GZIPInputStream(in);

                    for (int i = 0; i < times; i++) {
                        len = gzipreader.read(bytebuf);
                        sb.append(new String(bytebuf, 0, len));
                        // TODO: 2016/8/10 进度读取，需要知道返回值的长度
                    }
                } else {

                    char[] buf = new char[1024];
                    reader = new InputStreamReader(in, charset);
                    for (int i = 0; i < times; i++) {
                        len = reader.read(buf);
                        sb.append(buf, 0, len);
                        // TODO: 2016/8/10 进度读取，需要知道返回值的长度
                    }
                }
            }


        } finally {
            if (gzipreader != null)
                gzipreader.close();
            if (reader != null)
                reader.close();
        }
        return sb;
    }
}
