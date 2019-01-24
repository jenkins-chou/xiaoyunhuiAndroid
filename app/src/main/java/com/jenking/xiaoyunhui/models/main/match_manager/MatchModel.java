package com.jenking.xiaoyunhui.models.main.match_manager;

public class MatchModel {
    private String name;

    public MatchModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MatchModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
