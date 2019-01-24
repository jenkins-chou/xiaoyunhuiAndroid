package com.jenking.xiaoyunhui.models.base;

public class ClassModel {
    private String class_id;
    private String class_name;
    private String class_create_time;
    private String school_id;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_create_time() {
        return class_create_time;
    }

    public void setClass_create_time(String class_create_time) {
        this.class_create_time = class_create_time;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "class_id='" + class_id + '\'' +
                ", class_name='" + class_name + '\'' +
                ", class_create_time='" + class_create_time + '\'' +
                ", school_id='" + school_id + '\'' +
                '}';
    }
}
