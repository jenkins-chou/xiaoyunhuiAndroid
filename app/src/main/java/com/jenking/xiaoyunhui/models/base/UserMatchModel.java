package com.jenking.xiaoyunhui.models.base;

public class UserMatchModel {
    private String user_match_id;
    private String user_id;
    private String match_id;
    private String user_match_del;

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

    public String getUser_match_del() {
        return user_match_del;
    }

    public void setUser_match_del(String user_match_del) {
        this.user_match_del = user_match_del;
    }

    @Override
    public String toString() {
        return "UserMatchModel{" +
                "user_match_id='" + user_match_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", match_id='" + match_id + '\'' +
                ", user_match_del='" + user_match_del + '\'' +
                '}';
    }
}
