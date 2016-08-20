package com.campus.huanjinzi.campusmvp.data;

import java.util.HashMap;

/**
 * Created by huanjinzi on 2016/8/12.
 */
public interface StudentSource {

    HashMap<String, String> getBaseInfoMap(CXParams params) throws Exception;
}
