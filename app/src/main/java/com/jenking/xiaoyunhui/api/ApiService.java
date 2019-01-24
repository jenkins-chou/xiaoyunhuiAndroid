package com.jenking.xiaoyunhui.api;

import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.UserModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by zhouzhenjian on 2018/3/26.
 */

public interface ApiService {

    //模板接口
    @FormUrlEncoded
    @POST("user/login")
    Observable<ResultModel> template(@FieldMap Map<String, String> body);

    //登录接口
    @FormUrlEncoded
    @POST("user/login")
    Observable<ResultModel<UserModel>> login(@FieldMap Map<String, String> body);

    //注册接口
    @FormUrlEncoded
    @POST("user/adduser")
    Observable<ResultModel<UserModel>> register(@FieldMap Map<String, String> body);



}
