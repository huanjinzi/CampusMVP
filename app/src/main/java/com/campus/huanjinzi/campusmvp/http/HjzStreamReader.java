package com.campus.huanjinzi.campusmvp.http;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public class HjzStreamReader {

    public static StringBuilder getString(InputStream in) throws Exception {
        return getString(in, "utf-8", -1);
    }

    public static StringBuilder getString(InputStream in, String charset) throws Exception {
        return getString(in, charset, -1);
    }

    public static StringBuilder getString(InputStream in, String charset, int StreamLenth) throws Exception {

        int len = 0;
        char[] buf = new char[1024];
        StringBuilder sb = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(in, charset);

        while ((len = reader.read(buf)) != -1) {
            sb.append(buf, 0, len);
            // TODO: 2016/8/10 进度读取，需要知道返回值的长度
        }
        return sb;
    }
}
