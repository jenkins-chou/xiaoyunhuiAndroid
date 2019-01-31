package com.jenking.xiaoyunhui.contacts;

public interface ClassContract extends BaseCallBack{
    void getClassResult(boolean isSuccess, Object object);//获取单个班级的信息
    void getAllClassResult(boolean isSuccess, Object object);//
    void addClassResult(boolean isSuccess, Object object);//
}
