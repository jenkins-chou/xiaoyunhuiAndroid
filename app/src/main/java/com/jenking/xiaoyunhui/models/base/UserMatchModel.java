package com.jenking.xiaoyunhui.models.base;

public class UserMatchModel {
    private String user_match_id;
    private String user_id;
    private String match_id;
    private String user_match_status;
    private String score_id;
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

    @Override
    public String toString() {
        return "UserMatchModel{" +
                "user_match_id='" + user_match_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", match_id='" + match_id + '\'' +
                ", user_match_status='" + user_match_status + '\'' +
                ", score_id='" + score_id + '\'' +
                ", user_match_del='" + user_match_del + '\'' +
                '}';
    }
}
