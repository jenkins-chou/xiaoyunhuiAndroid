package com.jenking.xiaoyunhui.contacts;

public interface ScoreContract extends BaseCallBack{
    void getScoreListByUserIdResult(boolean isSuccess, Object object);//
    void getScoreListByMatchIdResult(boolean isSuccess, Object object);//根据比赛id获取成绩列表
    void getScorePublishListByUserIdResult(boolean isSuccess, Object object);//
    void getAllScoreList(boolean isSuccess, Object object);//
    void addScoresResult(boolean isSuccess, Object object);//批量添加成绩
    void updateScoreResult(boolean isSuccess, Object object);//修改单项成绩
}
