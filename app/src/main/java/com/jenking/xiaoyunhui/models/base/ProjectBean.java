package com.jenking.xiaoyunhui.models.base;

public class ProjectBean {
    private String name;
    private String project;
    private String money;
    private String year;
    private String month;
    private String day;
    private String beizhu;

    public ProjectBean(String name, String project, String money, String year, String month, String day, String beizhu) {
        this.name = name;
        this.project = project;
        this.money = money;
        this.year = year;
        this.month = month;
        this.day = day;
        this.beizhu = beizhu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }
}
