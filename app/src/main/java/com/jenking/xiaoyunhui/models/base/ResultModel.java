package com.jenking.xiaoyunhui.models.base;

import java.util.List;

/**
 * Created by zhouzhenjian on 2018/4/24.
 */

public class ResultModel<T> {

    private String status;
    private String message;
    private List<T> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
