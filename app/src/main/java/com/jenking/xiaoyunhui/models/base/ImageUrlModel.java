package com.jenking.xiaoyunhui.models.base;

public class ImageUrlModel {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ImageUrlModel{" +
                "path='" + path + '\'' +
                '}';
    }
}
