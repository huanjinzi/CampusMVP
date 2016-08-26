package com.campus.huanjinzi.campusmvp.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class HjzStreamReader {

    public static StringBuilder getString(InputStream in) throws Exception {
        return getString(in, "utf-8", null);
    }

    public static StringBuilder getString(InputStream in, String charset) throws Exception {
        return getString(in, charset, null);
    }


    public static StringBuilder getString(InputStream in, String charset, String encode) throws Exception {


        int len = 0;
        StringBuilder sb = new StringBuilder();
        GZIPInputStream gzipreader = null;
        InputStreamReader reader = null;
        try {
                if (encode != null && encode.toLowerCase().contains("gzip")) {

                    byte[] bytebuf = new byte[1024];
                    gzipreader = new GZIPInputStream(in);

                    while ((len = gzipreader.read(bytebuf)) != -1) {
                        sb.append(new String(bytebuf, 0, len));
                        // TODO: 2016/8/10 进度读取，需要知道返回值的长度
                    }
                } else {
                    char[] buf = new char[2048];
                    reader = new InputStreamReader(in, charset);
                    while ((len = reader.read(buf)) != -1) {
                        sb.append(buf, 0, len);
                        // TODO: 2016/8/10 进度读取，需要知道返回值的长度
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
