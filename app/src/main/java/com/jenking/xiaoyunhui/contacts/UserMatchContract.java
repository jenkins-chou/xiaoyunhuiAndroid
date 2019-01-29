package com.jenking.xiaoyunhui.contacts;

public interface UserMatchContract extends BaseCallBack{
    void getUserMatchByUserIdResult(boolean isSuccess, Object object);//
    void getUserMatchDetailByMatchIdResult(boolean isSuccess, Object object);//
    void getRefereeMatchByUserIdResult(boolean isSuccess, Object object);//
}
