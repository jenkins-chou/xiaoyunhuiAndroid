package com.jenking.xiaoyunhui.models.main.part3;

public class TeamModel {
    private String title;

    public TeamModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TeamModel{" +
                "title='" + title + '\'' +
                '}';
    }
}
