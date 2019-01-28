package com.jenking.xiaoyunhui.api;

import com.jenking.xiaoyunhui.models.base.MatchDetailModel;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.MatchTypeModel;
import com.jenking.xiaoyunhui.models.base.RefereeModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.SchoolModel;
import com.jenking.xiaoyunhui.models.base.UserMatchModel;
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


    //获取比赛类型
    @FormUrlEncoded
    @POST("matchType/getAllMatchType")
    Observable<ResultModel<MatchTypeModel>> getAllMatchType(@FieldMap Map<String, String> body);

    //删除比赛类型
    @FormUrlEncoded
    @POST("matchType/deleteMatchType")
    Observable<ResultModel> deleteMatchType(@FieldMap Map<String, String> body);

    //添加比赛类型
    @FormUrlEncoded
    @POST("matchType/addMatchType")
    Observable<ResultModel> addMatchType(@FieldMap Map<String, String> body);

    //裁判员认证
    @FormUrlEncoded
    @POST("referee/addReferee")
    Observable<ResultModel<RefereeModel>> addReferee(@FieldMap Map<String, String> body);

    //通过裁判员认证
    @FormUrlEncoded
    @POST("referee/updateReferee")
    Observable<ResultModel<RefereeModel>> updateReferee(@FieldMap Map<String, String> body);

    //根据status获取所有裁判员
    @FormUrlEncoded
    @POST("referee/getRefereeByStatus")
    Observable<ResultModel<RefereeModel>> getRefereeByStatus(@FieldMap Map<String, String> body);


    //获取所有比赛
    @FormUrlEncoded
    @POST("match/getmatchs")
    Observable<ResultModel<MatchModel>> getAllMatch(@FieldMap Map<String, String> body);

    //根据状态获取比赛
    @FormUrlEncoded
    @POST("match/getmatchByStatus")
    Observable<ResultModel<MatchModel>> getmatchByStatus(@FieldMap Map<String, String> body);

    //根据userid获取比赛
    @FormUrlEncoded
    @POST("match/getmatchByUserId")
    Observable<ResultModel<MatchModel>> getmatchByUserId(@FieldMap Map<String, String> body);

    //根据keyword获取比赛
    @FormUrlEncoded
    @POST("match/searchmatch")
    Observable<ResultModel<MatchModel>> searchMatch(@FieldMap Map<String, String> body);

    //添加比赛
    @FormUrlEncoded
    @POST("match/addmatch")
    Observable<ResultModel> addMatch(@FieldMap Map<String, String> body);

    //添加比赛
    @FormUrlEncoded
    @POST("match/getmatchDetail")
    Observable<ResultModel<MatchDetailModel>> getMatchById(@FieldMap Map<String, String> body);

    //比赛报名
    @FormUrlEncoded
    @POST("userMatch/addUserMatch")
    Observable<ResultModel> addUserMatch(@FieldMap Map<String, String> body);

    //根据比赛id获取报名的名单
    @FormUrlEncoded
    @POST("userMatch/getUserMatchByMatchId")
    Observable<ResultModel<UserMatchModel>> getUserMatchByMatchId(@FieldMap Map<String, String> body);

    //根据user_id获取比赛信息
    @FormUrlEncoded
    @POST("userMatch/getUserMatchByUserId")
    Observable<ResultModel<UserMatchModel>> getUserMatchByUserId(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("match/getmatchByType")
    Observable<ResultModel<MatchModel>> getmatchByType(@FieldMap Map<String, String> body);


    @FormUrlEncoded
    @POST("school/getSchools")
    Observable<ResultModel<SchoolModel>> getSchools(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("school/addSchool")
    Observable<ResultModel<SchoolModel>> addSchool(@FieldMap Map<String, String> body);



}
