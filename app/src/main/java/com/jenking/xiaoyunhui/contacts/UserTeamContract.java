package com.jenking.xiaoyunhui.contacts;

public interface UserTeamContract extends BaseCallBack{
    void getTeamJoinedMemberResult(boolean isSuccess, Object object);//
    void getTeamApplyMemberResult(boolean isSuccess, Object object);//
    void applyJoinTeamResult(boolean isSuccess, Object object);//申请加入团队回调函数
    void updateUserTeamResult(boolean isSuccess, Object object);//同意或忽略申请
}
