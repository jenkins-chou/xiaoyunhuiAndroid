package com.jenking.xiaoyunhui.models.main.scoreForExcel;

public class AllScoreIntegral {
    private String score_value;
    private String score_unit;
    private String score_integral;
    private String user_name;
    private String team_name;
    private String match_title;
    private String match_time;
    private String match_address;

    public String getScore_value() {
        return score_value;
    }

    public void setScore_value(String score_value) {
        this.score_value = score_value;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
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

    public String getMatch_address() {
        return match_address;
    }

    public void setMatch_address(String match_address) {
        this.match_address = match_address;
    }

    @Override
    public String toString() {
        return "AllScoreIntegral{" +
                "score_value='" + score_value + '\'' +
                ", score_unit='" + score_unit + '\'' +
                ", score_integral='" + score_integral + '\'' +
                ", user_name='" + user_name + '\'' +
                ", team_name='" + team_name + '\'' +
                ", match_title='" + match_title + '\'' +
                ", match_time='" + match_time + '\'' +
                ", match_address='" + match_address + '\'' +
                '}';
    }
}
