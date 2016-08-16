package com.campus.huanjinzi.campusmvp.data;

import java.util.HashMap;

/**
 * Created by huanjinzi on 2016/8/12.
 */
public interface StudentSource {

    HashMap<String, String> getBaseInfoMap(String username, String password) throws Exception;

    boolean logout(String username, String password) throws Exception;

}
