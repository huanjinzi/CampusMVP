package com.campus.huanjinzi.campusmvp;

import com.campus.huanjinzi.campusmvp.data.Student;
import com.campus.huanjinzi.campusmvp.data.StudentFactory;

import org.junit.Test;

/**
 * Created by huanjinzi on 2016/8/13.
 */
public class StudentTest {

    @Test
    public void student() {
        Student student = StudentFactory.getStudent("huanjinzi", "197325");
        System.out.println(student.getName());
        System.out.println(student.getStudent_id());
        System.out.println(student.getMajor());
        System.out.println(student.getInstitution());
        System.out.println(student.getID_card_No());
    }
}
