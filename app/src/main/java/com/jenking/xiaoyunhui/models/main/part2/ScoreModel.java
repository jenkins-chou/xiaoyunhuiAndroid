package com.jenking.xiaoyunhui.models.main.part2;

public class ScoreModel {
    private String name;

    public ScoreModel(String name) {
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
        return "ScoreModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
