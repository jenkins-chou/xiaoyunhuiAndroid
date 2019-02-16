package com.jenking.xiaoyunhui.models.base;

public class ScoreModel {
    public String score_id;
    public String match_id;
    public String user_id;
    public String referee_id;
    public String score_value;
    public String score_create_time;
    public String score_detail;
    public String score_remark;
    public String score_publish_time;
    public String score_del;
    public String score_unit;
    public String score_integral;

    public String match_title;

    public String getScore_id() {
        return score_id;
    }

    public void setScore_id(String score_id) {
        this.score_id = score_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReferee_id() {
        return referee_id;
    }

    public void setReferee_id(String referee_id) {
        this.referee_id = referee_id;
    }

    public String getScore_value() {
        return score_value;
    }

    public void setScore_value(String score_value) {
        this.score_value = score_value;
    }

    public String getScore_create_time() {
        return score_create_time;
    }

    public void setScore_create_time(String score_create_time) {
        this.score_create_time = score_create_time;
    }

    public String getScore_detail() {
        return score_detail;
    }

    public void setScore_detail(String score_detail) {
        this.score_detail = score_detail;
    }

    public String getScore_remark() {
        return score_remark;
    }

    public void setScore_remark(String score_remark) {
        this.score_remark = score_remark;
    }

    public String getScore_publish_time() {
        return score_publish_time;
    }

    public void setScore_publish_time(String score_publish_time) {
        this.score_publish_time = score_publish_time;
    }

    public String getScore_del() {
        return score_del;
    }

    public void setScore_del(String score_del) {
        this.score_del = score_del;
    }

    public String getScore_unit() {
        return score_unit;
    }

    public void setScore_unit(String score_unit) {
        this.score_unit = score_unit;
    }

    public String getScore_integral() {
        return score_integral;
    }

    public void setScore_integral(String score_integral) {
        this.score_integral = score_integral;
    }

    public String getMatch_title() {
        return match_title;
    }

    public void setMatch_title(String match_title) {
        this.match_title = match_title;
    }

    @Override
    public String toString() {
        return "ScoreModel{" +
                "score_id='" + score_id + '\'' +
                ", match_id='" + match_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", referee_id='" + referee_id + '\'' +
                ", score_value='" + score_value + '\'' +
                ", score_create_time='" + score_create_time + '\'' +
                ", score_detail='" + score_detail + '\'' +
                ", score_remark='" + score_remark + '\'' +
                ", score_publish_time='" + score_publish_time + '\'' +
                ", score_del='" + score_del + '\'' +
                ", score_unit='" + score_unit + '\'' +
                ", score_integral='" + score_integral + '\'' +
                ", match_title='" + match_title + '\'' +
                '}';
    }
}
