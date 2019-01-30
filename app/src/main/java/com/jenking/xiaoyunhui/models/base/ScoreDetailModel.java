package com.jenking.xiaoyunhui.models.base;

public class ScoreDetailModel extends ScoreModel {
    private String user_name;
    private String user_avatar;
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    @Override
    public String getMatch_title() {
        return match_title;
    }

    @Override
    public void setMatch_title(String match_title) {
        this.match_title = match_title;
    }

    @Override
    public String toString() {
        return "ScoreDetailModel{" +
                "user_name='" + user_name + '\'' +
                ", user_avatar='" + user_avatar + '\'' +
                ", match_title='" + match_title + '\'' +
                '}';
    }
}
