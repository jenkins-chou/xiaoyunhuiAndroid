package com.jenking.xiaoyunhui.api;

import com.jenking.xiaoyunhui.models.base.ClassModel;
import com.jenking.xiaoyunhui.models.base.MatchDetailModel;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.MatchTypeModel;
import com.jenking.xiaoyunhui.models.base.RefereeModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.SchoolModel;
import com.jenking.xiaoyunhui.models.base.ScoreDetailModel;
import com.jenking.xiaoyunhui.models.base.ScoreModel;
import com.jenking.xiaoyunhui.models.base.TeamDetailModel;
import com.jenking.xiaoyunhui.models.base.TeamModel;
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

    //修改个人信息
    @FormUrlEncoded
    @POST("user/updateuser")
    Observable<ResultModel<UserModel>> updateUserInfo(@FieldMap Map<String, String> body);

    //获取个人信息
    @FormUrlEncoded
    @POST("user/getuser")
    Observable<ResultModel<UserModel>> getUserInfo(@FieldMap Map<String, String> body);


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

    //根据refereeid获取比赛
    @FormUrlEncoded
    @POST("match/getmatchByRefereeId")
    Observable<ResultModel<MatchModel>> getmatchByRefereeId(@FieldMap Map<String, String> body);

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

    //批量执行函数
    @FormUrlEncoded
    @POST("match/excute")
    Observable<ResultModel> excuteMatchSql(@FieldMap Map<String, String> body);

    //删除函数
    @FormUrlEncoded
    @POST("match/deletematch")
    Observable<ResultModel> deleteMatch(@FieldMap Map<String, String> body);

    //比赛报名
    @FormUrlEncoded
    @POST("userMatch/addUserMatch")
    Observable<ResultModel> addUserMatch(@FieldMap Map<String, String> body);

    //删除比赛报名信息
    @FormUrlEncoded
    @POST("userMatch/deleteUserMatch2")
    Observable<ResultModel> deleteUserMatch(@FieldMap Map<String, String> body);

    //根据比赛id获取报名的名单
    @FormUrlEncoded
    @POST("userMatch/getUserMatchByMatchId")
    Observable<ResultModel<UserMatchModel>> getUserMatchByMatchId(@FieldMap Map<String, String> body);

    //根据比赛id获取报名的名单
    @FormUrlEncoded
    @POST("userMatch/getUserMatchDetailByMatchId")
    Observable<ResultModel<UserModel>> getUserMatchDetailByMatchId(@FieldMap Map<String, String> body);

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

    @FormUrlEncoded
    @POST("school/getSchool")
    Observable<ResultModel<SchoolModel>> getSchool(@FieldMap Map<String, String> body);


    @FormUrlEncoded
    @POST("score/getScoreListByUserId")
    Observable<ResultModel<MatchModel>> getScoreListByUserId(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("score/getScoreListByMatchId")
    Observable<ResultModel<ScoreDetailModel>> getScoreListByMatchId(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("score/getAllScoreList")
    Observable<ResultModel<ScoreDetailModel>> getAllScoreList(@FieldMap Map<String, String> body);


    @FormUrlEncoded
    @POST("score/getScorePublishListByUserId")
    Observable<ResultModel<ScoreModel>> getScorePublishListByUserId(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("score/addScores")
    Observable<ResultModel<ScoreModel>> addScores(@FieldMap Map<String, String> body);


    @FormUrlEncoded
    @POST("class/getAllClass")
    Observable<ResultModel<ClassModel>> getAllClass(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("class/addclass")
    Observable<ResultModel<ClassModel>> addClass(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("class/getClass")
    Observable<ResultModel<ClassModel>> getClassById(@FieldMap Map<String, String> body);


    @FormUrlEncoded
    @POST("team/addTeam")
    Observable<ResultModel<TeamModel>> addTeam(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("team/getTeamListByCreater")
    Observable<ResultModel<TeamModel>> getTeamListByCreater(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("team/getTeamListByUserId")
    Observable<ResultModel<TeamModel>> getTeamListByUserId(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("team/getTeamDetail")
    Observable<ResultModel<TeamDetailModel>> getTeamDetail(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("team/getTeamListExceptUserId")
    Observable<ResultModel<TeamModel>> getTeamListExceptUserId(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("team/searchTeamListExceptUserId")
    Observable<ResultModel<TeamModel>> searchTeamListExceptUserId(@FieldMap Map<String, String> body);




    @FormUrlEncoded
    @POST("userTeam/getTeamMember")
    Observable<ResultModel<UserModel>> getTeamMember(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("userTeam/addUserTeam")
    Observable<ResultModel<UserModel>> applyJoinTeam(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("userTeam/operateUserTeamApply")
    Observable<ResultModel> operateUserTeamApply(@FieldMap Map<String, String> body);

}
