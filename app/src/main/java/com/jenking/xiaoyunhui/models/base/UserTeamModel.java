package com.jenking.xiaoyunhui.models.base;

public class UserTeamModel {
    private String user_team_id;
    private String user_id;
    private String team_id;
    private String user_team_del;

    public String getUser_team_id() {
        return user_team_id;
    }

    public void setUser_team_id(String user_team_id) {
        this.user_team_id = user_team_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getUser_team_del() {
        return user_team_del;
    }

    public void setUser_team_del(String user_team_del) {
        this.user_team_del = user_team_del;
    }

    @Override
    public String toString() {
        return "UserTeamModel{" +
                "user_team_id='" + user_team_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", team_id='" + team_id + '\'' +
                ", user_team_del='" + user_team_del + '\'' +
                '}';
    }
}
