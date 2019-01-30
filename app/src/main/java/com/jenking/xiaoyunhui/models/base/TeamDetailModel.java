package com.jenking.xiaoyunhui.models.base;

public class TeamDetailModel extends TeamModel {
    private String user_name;//领队名
    private String user_avatar;//领队头像

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
    public String toString() {
        return "TeamDetailModel{" +
                "user_name='" + user_name + '\'' +
                ", user_avatar='" + user_avatar + '\'' +
                '}';
    }
}
