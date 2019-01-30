package com.jenking.xiaoyunhui.models.base;

public class TeamModel {
    public String team_id;
    public String team_leader;
    public String team_create_time;
    public String team_create_user;
    public String team_name;
    public String team_detail;
    public String team_abstract;
    public String team_status;
    public String team_logo;
    public String team_del;

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_leader() {
        return team_leader;
    }

    public void setTeam_leader(String team_leader) {
        this.team_leader = team_leader;
    }

    public String getTeam_create_time() {
        return team_create_time;
    }

    public void setTeam_create_time(String team_create_time) {
        this.team_create_time = team_create_time;
    }

    public String getTeam_create_user() {
        return team_create_user;
    }

    public void setTeam_create_user(String team_create_user) {
        this.team_create_user = team_create_user;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_detail() {
        return team_detail;
    }

    public void setTeam_detail(String team_detail) {
        this.team_detail = team_detail;
    }

    public String getTeam_abstract() {
        return team_abstract;
    }

    public void setTeam_abstract(String team_abstract) {
        this.team_abstract = team_abstract;
    }

    public String getTeam_status() {
        return team_status;
    }

    public void setTeam_status(String team_status) {
        this.team_status = team_status;
    }

    public String getTeam_logo() {
        return team_logo;
    }

    public void setTeam_logo(String team_logo) {
        this.team_logo = team_logo;
    }

    public String getTeam_del() {
        return team_del;
    }

    public void setTeam_del(String team_del) {
        this.team_del = team_del;
    }

    @Override
    public String toString() {
        return "TeamModel{" +
                "team_id='" + team_id + '\'' +
                ", team_leader='" + team_leader + '\'' +
                ", team_create_time='" + team_create_time + '\'' +
                ", team_create_user='" + team_create_user + '\'' +
                ", team_name='" + team_name + '\'' +
                ", team_detail='" + team_detail + '\'' +
                ", team_abstract='" + team_abstract + '\'' +
                ", team_status='" + team_status + '\'' +
                ", team_logo='" + team_logo + '\'' +
                ", team_del='" + team_del + '\'' +
                '}';
    }
}
