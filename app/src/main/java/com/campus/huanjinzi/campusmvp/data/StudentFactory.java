package com.campus.huanjinzi.campusmvp.data;

import com.campus.huanjinzi.campusmvp.data.remote.StudentSourceRemote;

import java.util.HashMap;

/**
 * Created by huanjinzi on 2016/8/12.
 */
public class StudentFactory {

    private StudentFactory() {
    }

    public static Student getStudent(String username, String password) {

        HashMap<String, String> map = null;
        Student student = null;

        if (isLocal(username)) {

            return null;

        } else {
            StudentSourceRemote studentSourceRemote = StudentSourceRemote.getInstance();
            try {
                map = studentSourceRemote.getBaseInfoMap(username, password);
            } catch (Exception e) {
                map.put("Exception", "" + e.getMessage());
            }
            if (map != null) {
                /*      <b>学号
                <b>姓名
                <b>民族
                <b>性别
                <b>政治面貌
                <b>身份证
                <b>学院
                <b>专业
                <b>类别*/
                student = new Student();
                student.setNetusername(username);
                student.setNetpassword(password);
                student.setStudent_id(map.get("学号"));
                student.setName(map.get("姓名"));
                student.setNation(map.get("民族"));
                student.setSex(map.get("性别"));
                student.setPolitical_status(map.get("政治面貌"));
                student.setID_card_No(map.get("身份证"));
                student.setInstitution(map.get("学院"));
                student.setMajor(map.get("专业"));
            }

            return student;
        }

    }

    private static boolean isLocal(String username) {
        return false;
    }
}
