package com.campus.huanjinzi.campusmvp.http;

import java.io.InputStream;

/**
 * Created by huanjinzi on 2016/8/10.
 */

public interface IHjzHttp
{
    /*post请求*/
    InputStream post(Params params) throws Exception;
    /*get请求*/
    InputStream get(Params params) throws Exception;
}
