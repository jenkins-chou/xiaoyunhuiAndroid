package com.jenking.xiaoyunhui.models.main.part1;

public class IndexItem {
    private String status;
    private String time;
    private String img;
    private String title;

    public IndexItem(String status, String time, String img, String title) {
        this.status = status;
        this.time = time;
        this.img = img;
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "IndexItem{" +
                "status='" + status + '\'' +
                ", time='" + time + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
