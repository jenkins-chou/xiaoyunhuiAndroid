package com.jenking.xiaoyunhui.models.base;

public class MatchTypeModel {
    private String match_type_id;
    private String match_type_name;
    private String match_type_manager;
    private String match_type_create_time;
    private String match_type_del;

    public String getMatch_type_id() {
        return match_type_id;
    }

    public void setMatch_type_id(String match_type_id) {
        this.match_type_id = match_type_id;
    }

    public String getMatch_type_name() {
        return match_type_name;
    }

    public void setMatch_type_name(String match_type_name) {
        this.match_type_name = match_type_name;
    }

    public String getMatch_type_manager() {
        return match_type_manager;
    }

    public void setMatch_type_manager(String match_type_manager) {
        this.match_type_manager = match_type_manager;
    }

    public String getMatch_type_create_time() {
        return match_type_create_time;
    }

    public void setMatch_type_create_time(String match_type_create_time) {
        this.match_type_create_time = match_type_create_time;
    }

    public String getMatch_type_del() {
        return match_type_del;
    }

    public void setMatch_type_del(String match_type_del) {
        this.match_type_del = match_type_del;
    }

    @Override
    public String toString() {
        return "MatchTypeModel{" +
                "match_type_id='" + match_type_id + '\'' +
                ", match_type_name='" + match_type_name + '\'' +
                ", match_type_manager='" + match_type_manager + '\'' +
                ", match_type_create_time='" + match_type_create_time + '\'' +
                ", match_type_del='" + match_type_del + '\'' +
                '}';
    }
}
