package com.campus.huanjinzi.campusmvp.data;

import com.campus.huanjinzi.campusmvp.annotation.Order;
import com.campus.huanjinzi.campusmvp.data.StudentInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by huanjinzi on 2016/11/6.
 */

public class StudentInfoSort {
    public static Field[] get()
    {
        Class bean = StudentInfo.DataBean.GetDataResponseBean.ReturnBean.BodyBean.ItemsBean.class;
        Field[] fields = bean.getDeclaredFields();
        ArrayList<Field> dst = new ArrayList(10);
        Field[] sort = new Field[10];

        Annotation annotation = null;
        for (int i = 0; i < fields.length; i++) {
            annotation = fields[i].getAnnotation(Order.class);

            if (annotation!=null && annotation.annotationType()==Order.class)
            {
                dst.add(fields[i]);
            }
        }
        for (Field field:
                dst) {
            sort[field.getAnnotation(Order.class).value() -1] = field;
        }
        return sort;
    }
}
