package com.jenking.xiaoyunhui.contacts;

//比赛类型
public interface MatchTypeContract extends BaseCallBack{
    void getAllMatchTypeResult(boolean isSuccess, Object object);//
    void addMatchTypeResult(boolean isSuccess, Object object);//
    void deleteMatchTypeResult(boolean isSuccess, Object object);//
}
