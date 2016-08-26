package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.data.CXParams;
import com.campus.huanjinzi.campusmvp.data.Student;
import com.campus.huanjinzi.campusmvp.data.StudentFactory;
import com.campus.huanjinzi.campusmvp.data.remote.StudentSourceRemote;
import com.campus.huanjinzi.campusmvp.utils.MyLog;

import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * Created by huanjinzi on 2016/8/13.
 */
public class StudentTest {

    @Test
    public void student() {

        CXParams params = new CXParams();
        params.setUsename("huanjinzi");
        params.setPassword("197325");
        Student student = StudentFactory.getStudent(params);
        System.out.println(student.getName());
        System.out.println(student.getStudent_id());
        System.out.println(student.getMajor());
        System.out.println(student.getInstitution());
        System.out.println(student.getID_card_No());
        System.out.println(student.getDegree());
        System.out.println(student.getPolitical_status());
        System.out.println(student.getNation());
    }
}
