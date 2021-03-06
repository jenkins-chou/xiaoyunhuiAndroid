package com.jenking.xiaoyunhui.contacts;

//比赛类型
public interface MatchContract extends BaseCallBack{
    void getAllMatchResult(boolean isSuccess, Object object);//
    void getMatchByStatusResult(boolean isSuccess, Object object);//
    void getMatchByTypeResult(boolean isSuccess, Object object);//
    void getMatchByIdResult(boolean isSuccess, Object object);//
    void addMatchResult(boolean isSuccess, Object object);//
    void addUserMatchResult(boolean isSuccess, Object object);//
    void deleteUserMatchResult(boolean isSuccess, Object object);//
    void getUserMatchByMatchIdResult(boolean isSuccess, Object object);//
    void getMatchByUserIdResult(boolean isSuccess, Object object);//
    void getMatchByRefereeIdResult(boolean isSuccess, Object object);//
    void excuteResult(boolean isSuccess, Object object);//
    void deleteMatchResult(boolean isSuccess, Object object);//
}
