package com.jenking.xiaoyunhui.models.base;

public class UserMatchModel extends UserModel{
    private String user_match_id;
    private String match_id;
    private String user_match_status;
    private String score_id;
    private String user_match_del;
    private String user_group;
    private String user_order;

    //扩展字段
    private String school_name;
    private String class_name;

    public String getUser_match_id() {
        return user_match_id;
    }

    public void setUser_match_id(String user_match_id) {
        this.user_match_id = user_match_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getUser_match_status() {
        return user_match_status;
    }

    public void setUser_match_status(String user_match_status) {
        this.user_match_status = user_match_status;
    }

    public String getScore_id() {
        return score_id;
    }

    public void setScore_id(String score_id) {
        this.score_id = score_id;
    }

    public String getUser_match_del() {
        return user_match_del;
    }

    public void setUser_match_del(String user_match_del) {
        this.user_match_del = user_match_del;
    }

    public String getUser_group() {
        return user_group;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }

    public String getUser_order() {
        return user_order;
    }

    public void setUser_order(String user_order) {
        this.user_order = user_order;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Override
    public String toString() {
        return "UserMatchModel{" +
                "user_match_id='" + user_match_id + '\'' +
                ", match_id='" + match_id + '\'' +
                ", user_match_status='" + user_match_status + '\'' +
                ", score_id='" + score_id + '\'' +
                ", user_match_del='" + user_match_del + '\'' +
                ", user_group='" + user_group + '\'' +
                ", user_order='" + user_order + '\'' +
                ", school_name='" + school_name + '\'' +
                ", class_name='" + class_name + '\'' +
                '}';
    }
}
