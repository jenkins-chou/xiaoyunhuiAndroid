package com.jenking.xiaoyunhui.contacts;

public interface LoginContract extends BaseCallBack{
    void loginResult(boolean isSuccess,Object object);//
    void registerResult(boolean isSuccess,Object object);//
}
