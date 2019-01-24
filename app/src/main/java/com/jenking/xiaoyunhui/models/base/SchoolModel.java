package com.jenking.xiaoyunhui.models.base;

public class SchoolModel {
    private String school_id;
    private String school_logo;
    private String school_name;
    private String school_address;
    private String school_manager;
    private String school_abstract;
    private String school_detail;
    private String school_remark;
    private String school_del;
    private String school_create_time;

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getSchool_logo() {
        return school_logo;
    }

    public void setSchool_logo(String school_logo) {
        this.school_logo = school_logo;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSchool_address() {
        return school_address;
    }

    public void setSchool_address(String school_address) {
        this.school_address = school_address;
    }

    public String getSchool_manager() {
        return school_manager;
    }

    public void setSchool_manager(String school_manager) {
        this.school_manager = school_manager;
    }

    public String getSchool_abstract() {
        return school_abstract;
    }

    public void setSchool_abstract(String school_abstract) {
        this.school_abstract = school_abstract;
    }

    public String getSchool_detail() {
        return school_detail;
    }

    public void setSchool_detail(String school_detail) {
        this.school_detail = school_detail;
    }

    public String getSchool_remark() {
        return school_remark;
    }

    public void setSchool_remark(String school_remark) {
        this.school_remark = school_remark;
    }

    public String getSchool_del() {
        return school_del;
    }

    public void setSchool_del(String school_del) {
        this.school_del = school_del;
    }

    public String getSchool_create_time() {
        return school_create_time;
    }

    public void setSchool_create_time(String school_create_time) {
        this.school_create_time = school_create_time;
    }

    @Override
    public String toString() {
        return "SchoolModel{" +
                "school_id='" + school_id + '\'' +
                ", school_logo='" + school_logo + '\'' +
                ", school_name='" + school_name + '\'' +
                ", school_address='" + school_address + '\'' +
                ", school_manager='" + school_manager + '\'' +
                ", school_abstract='" + school_abstract + '\'' +
                ", school_detail='" + school_detail + '\'' +
                ", school_remark='" + school_remark + '\'' +
                ", school_del='" + school_del + '\'' +
                ", school_create_time='" + school_create_time + '\'' +
                '}';
    }
}
