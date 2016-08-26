package com.campus.huanjinzi.campusmvp;

import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by huanjinzi on 2016/8/20.
 */
public class DecodeTest {

    @Test
    public void decode()throws Exception
    {
        System.out.println(URLEncoder.encode("登录成功","utf-8"));
        System.out.println(URLDecoder.decode("%E7%99%BB%E5%BD%95%E6%88%90%E5%8A%9F","utf-8"));
    }
}
