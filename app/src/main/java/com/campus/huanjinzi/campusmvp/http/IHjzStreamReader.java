package com.campus.huanjinzi.campusmvp.http;

import java.io.InputStream;

/**
 * Created by huanjinzi on 2016/8/10.
 */
public interface IHjzStreamReader {

    /*从流中读取字符串*/
    String getString(InputStream in);

}
