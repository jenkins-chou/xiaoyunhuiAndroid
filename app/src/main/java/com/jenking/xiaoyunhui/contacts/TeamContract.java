package com.jenking.xiaoyunhui.contacts;

public interface TeamContract extends BaseCallBack{
    void addTeamResult(boolean isSuccess, Object object);//
    void getMineTeamResult(boolean isSuccess, Object object);//
    void getTeamByUserIdResult(boolean isSuccess, Object object);//获取用户参与的团队
    void getOtherTeamResult(boolean isSuccess, Object object);//
    void getTeamDetailResult(boolean isSuccess, Object object);//
    void joinTeamResult(boolean isSuccess, Object object);//
    void getTeamMenber(boolean isSuccess, Object object);//
}
