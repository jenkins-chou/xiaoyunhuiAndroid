package com.jenking.xiaoyunhui.presenters;

import android.content.Context;
import android.util.Log;

import com.jenking.xiaoyunhui.api.ApiService;
import com.jenking.xiaoyunhui.api.ApiUtil;
import com.jenking.xiaoyunhui.contacts.BaseCallBack;
import com.jenking.xiaoyunhui.contacts.TeamContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.TeamDetailModel;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter模板
 */
public class TeamPresenter {

    private Context context;

    private BaseCallBack view;

    public TeamPresenter(Context context, BaseCallBack view){
        this.context = context;
        this.view = view;
    }

    public void createTeam(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .addTeam(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel resultModel) {
                        //更新视图
                        TeamContract teamContract = (TeamContract)view;
                        teamContract.addTeamResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        TeamContract teamContract = (TeamContract)view;
                        teamContract.addTeamResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getTeamListByCreater(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getTeamListByCreater(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel resultModel) {
                        //更新视图
                        TeamContract teamContract = (TeamContract)view;
                        teamContract.getMineTeamResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        TeamContract teamContract = (TeamContract)view;
                        teamContract.getMineTeamResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //获取用户参与的团队
    public void getTeamListByUserId(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getTeamListByUserId(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel resultModel) {
                        //更新视图
                        TeamContract teamContract = (TeamContract)view;
                        teamContract.getTeamByUserIdResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        TeamContract teamContract = (TeamContract)view;
                        teamContract.getTeamByUserIdResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getTeamDetail(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getTeamDetail(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<TeamDetailModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<TeamDetailModel> resultModel) {
                        //更新视图
                        TeamContract teamContract = (TeamContract)view;
                        teamContract.getTeamDetailResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        TeamContract teamContract = (TeamContract)view;
                        teamContract.getTeamDetailResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
