package com.jenking.xiaoyunhui.contacts;

public interface SchoolContract extends BaseCallBack{
    void getAllSchoolResult(boolean isSuccess, Object object);
    void getSchoolByIdResult(boolean isSuccess, Object object);
    void addSchoolResult(boolean isSuccess, Object object);
    void updateSchoolResult(boolean isSuccess, Object object);
    void deleteSchoolResult(boolean isSuccess, Object object);
}
