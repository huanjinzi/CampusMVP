package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huanjinzi on 2016/10/16.
 */

public class IPtest {
    @Test
    public void ip()throws Exception{
        File file = new File("C:\\Users\\huanjinzi\\Desktop\\test.txt");
        //Matcher matcher = Matcher.;
        FileInputStream fis = new FileInputStream(file);
        StringBuilder sb = HjzStreamReader.getString(fis,"GBK");

        Pattern pattern = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
        Matcher matcher = pattern.matcher(sb.toString());
        while (matcher.find())
        {
            System.out.println(matcher.group());
        }
    }
}
