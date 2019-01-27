package com.jenking.xiaoyunhui.models.base;

public class RefereeModel extends UserModel{
    public String referee_id;
    public String referee_status;
    public String referee_manager;
    public String referee_del;

    public String getReferee_id() {
        return referee_id;
    }

    public void setReferee_id(String referee_id) {
        this.referee_id = referee_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReferee_status() {
        return referee_status;
    }

    public void setReferee_status(String referee_status) {
        this.referee_status = referee_status;
    }

    public String getReferee_manager() {
        return referee_manager;
    }

    public void setReferee_manager(String referee_manager) {
        this.referee_manager = referee_manager;
    }
    public String getReferee_del() {
        return referee_del;
    }

    public void setReferee_del(String referee_del) {
        this.referee_del = referee_del;
    }

    @Override
    public String toString() {
        return "RefereeModel{" +
                "referee_id='" + referee_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", referee_status='" + referee_status + '\'' +
                ", referee_manager='" + referee_manager + '\'' +
                ", referee_del='" + referee_del + '\'' +
                '}';
    }
}
