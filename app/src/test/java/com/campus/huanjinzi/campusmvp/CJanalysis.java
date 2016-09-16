package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.http.HjzStreamReader;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by huanjinzi on 2016/9/6.
 */
public class CJanalysis {

    @Test
    public void analysis() throws Exception {

        File file = new File("C:\\Users\\huanjinzi\\Desktop\\yh.txt");
        FileInputStream in = new FileInputStream(file);
        StringBuilder sb = HjzStreamReader.getString(in, "gb2312");
        Cj cj = Cj.objectFromData(sb.toString());
        List<Cj.ItemsBean> list = cj.getItems();
        double xf_sum = 0;
        double xf_jd_sum = 0;
        double jd_sum = 0;
        double xf = 0;
        double jd = 0;
        int i = 0;
        System.out.println(list.size());
        for (Cj.ItemsBean items : list
                ) {
            if (items.getKcxzmc().contains("学科必修课") || items.getKcxzmc().contains("专业必修课") ||
                    items.getKcxzmc().contains("实践必修课")) {
                xf = Double.parseDouble(items.getXf());
                jd = Double.parseDouble(items.getJd());
                if (jd < 0.1) {

                } else {
                    System.out.println(items.getKcmc() +"--------成绩："+items.getCj()+ "(学分):" + xf + "（绩点）：" + items.getJd());
                    xf_jd_sum += xf * jd;
                    xf_sum += xf;
                    i++;
                    jd_sum += jd;
                }
            }
        }
        System.out.println("平均绩点：" + xf_jd_sum / xf_sum);
        System.out.println("课程数：" + i);
        System.out.println("总绩点：" + jd_sum);
    }
}
