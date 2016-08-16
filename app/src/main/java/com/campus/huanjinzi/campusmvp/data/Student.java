package com.campus.huanjinzi.campusmvp.data;

import java.util.UUID;

/**
 * Created by huanjinzi on 2016/8/12.
 */
public final class Student {

    private String name = null;//姓名
    private String student_id = null;//学号
    private String sex = null;//性别
    private String institution = null;//学院
    private String nation = null;//民族
    private String major = null;//专业
    private String ID_card_No = null;//身份证号码
    private String phonenum = null;//电话号码
    private String middleschool = null;//高中学校
    private String birthday = null;//生日
    private int intoscore = -1;//入学分数
    private int credit = -1;//已获学分
    private String netusername = null;//校园网账号
    private String netpassword = null;//校园网密码
    private String political_status = null;//z政治面貌
    private String mUUID;//唯一码

    public String getPolitical_status() {
        return political_status;
    }

    public void setPolitical_status(String political_status) {
        this.political_status = political_status;
    }

    public void setNetusername(String netusername) {
        this.netusername = netusername;
    }

    public Student() {

        mUUID = UUID.randomUUID().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setID_card_No(String ID_card_No) {
        this.ID_card_No = ID_card_No;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setMiddleschool(String middleschool) {
        this.middleschool = middleschool;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setIntoscore(int intoscore) {
        this.intoscore = intoscore;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setNetpassword(String netpassword) {
        this.netpassword = netpassword;
    }

    public String getmUUID() {
        return mUUID;
    }

    public String getNetusername() {
        return netusername;
    }

    public String getNetpassword() {
        return netpassword;
    }

    public String getName() {
        return name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getSex() {
        return sex;
    }

    public String getInstitution() {
        return institution;
    }

    public String getNation() {
        return nation;
    }

    public String getMajor() {
        return major;
    }

    public String getID_card_No() {
        return ID_card_No;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getMiddleschool() {
        return middleschool;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getIntoscore() {
        return intoscore;
    }

    public int getCredit() {
        return credit;
    }

    @Override
    public String toString() {
        return netusername + name;
    }


}
