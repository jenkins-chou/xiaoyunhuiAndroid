package com.jenking.xiaoyunhui.models.base;

public class MatchModel {
    private String match_id;
    private String match_title;
    private String match_time;
    private String match_create_time;
    private String match_referee_id;
    private String match_manager;
    private String match_abstract;
    private String match_detail;
    private String match_athletes_num;
    private String match_status;
    private String match_organizers;
    private String match_del;

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_title() {
        return match_title;
    }

    public void setMatch_title(String match_title) {
        this.match_title = match_title;
    }

    public String getMatch_time() {
        return match_time;
    }

    public void setMatch_time(String match_time) {
        this.match_time = match_time;
    }

    public String getMatch_create_time() {
        return match_create_time;
    }

    public void setMatch_create_time(String match_create_time) {
        this.match_create_time = match_create_time;
    }

    public String getMatch_referee_id() {
        return match_referee_id;
    }

    public void setMatch_referee_id(String match_referee_id) {
        this.match_referee_id = match_referee_id;
    }

    public String getMatch_manager() {
        return match_manager;
    }

    public void setMatch_manager(String match_manager) {
        this.match_manager = match_manager;
    }

    public String getMatch_abstract() {
        return match_abstract;
    }

    public void setMatch_abstract(String match_abstract) {
        this.match_abstract = match_abstract;
    }

    public String getMatch_detail() {
        return match_detail;
    }

    public void setMatch_detail(String match_detail) {
        this.match_detail = match_detail;
    }

    public String getMatch_athletes_num() {
        return match_athletes_num;
    }

    public void setMatch_athletes_num(String match_athletes_num) {
        this.match_athletes_num = match_athletes_num;
    }

    public String getMatch_status() {
        return match_status;
    }

    public void setMatch_status(String match_status) {
        this.match_status = match_status;
    }

    public String getMatch_organizers() {
        return match_organizers;
    }

    public void setMatch_organizers(String match_organizers) {
        this.match_organizers = match_organizers;
    }

    public String getMatch_del() {
        return match_del;
    }

    public void setMatch_del(String match_del) {
        this.match_del = match_del;
    }

    @Override
    public String toString() {
        return "MatchModel{" +
                "match_id='" + match_id + '\'' +
                ", match_title='" + match_title + '\'' +
                ", match_time='" + match_time + '\'' +
                ", match_create_time='" + match_create_time + '\'' +
                ", match_referee_id='" + match_referee_id + '\'' +
                ", match_manager='" + match_manager + '\'' +
                ", match_abstract='" + match_abstract + '\'' +
                ", match_detail='" + match_detail + '\'' +
                ", match_athletes_num='" + match_athletes_num + '\'' +
                ", match_status='" + match_status + '\'' +
                ", match_organizers='" + match_organizers + '\'' +
                ", match_del='" + match_del + '\'' +
                '}';
    }
}
