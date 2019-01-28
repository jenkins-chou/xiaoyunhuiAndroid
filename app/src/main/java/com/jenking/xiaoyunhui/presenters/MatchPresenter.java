package com.jenking.xiaoyunhui.presenters;

import android.content.Context;
import android.util.Log;

import com.jenking.xiaoyunhui.api.ApiService;
import com.jenking.xiaoyunhui.api.ApiUtil;
import com.jenking.xiaoyunhui.contacts.BaseCallBack;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.contacts.MatchSearchContract;
import com.jenking.xiaoyunhui.models.base.MatchDetailModel;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.UserMatchModel;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter模板
 */
public class MatchPresenter {

    private Context context;

    private BaseCallBack view;

    public MatchPresenter(Context context, BaseCallBack view){
        this.context = context;
        this.view = view;
    }

    //带参基本请求方法
    public void addMatch(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .addMatch(params)
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
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.addMatchResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.addMatchResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMatchById(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getMatchById(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<MatchDetailModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<MatchDetailModel> resultModel) {
                        //更新视图
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getMatchByIdResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getMatchByIdResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMatchByType(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getmatchByType(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<MatchModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<MatchModel> resultModel) {
                        //更新视图
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getMatchByTypeResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getMatchByTypeResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getAllMatch(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getAllMatch(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<MatchModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<MatchModel> resultModel) {
                        //更新视图
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getAllMatchResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getAllMatchResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMatchByStatus(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getmatchByStatus(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<MatchModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<MatchModel> resultModel) {
                        //更新视图
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getMatchByStatusResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getMatchByStatusResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMatchByUserId(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getmatchByUserId(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<MatchModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<MatchModel> resultModel) {
                        //更新视图
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getMatchByUserIdResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getMatchByUserIdResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //模糊查询比赛
    public void searchMatch(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .searchMatch(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<MatchModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<MatchModel> resultModel) {
                        //更新视图
                        MatchSearchContract matchContract = (MatchSearchContract)view;
                        matchContract.searchMatchResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchSearchContract matchContract = (MatchSearchContract)view;
                        matchContract.searchMatchResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //比赛报名
    public void addUserMatch(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .addUserMatch(params)
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
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.addUserMatchResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.addUserMatchResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //获取所有报名者信息
    public void getUserMatchByMatchId(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getUserMatchByMatchId(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<UserMatchModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<UserMatchModel> resultModel) {
                        //更新视图
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getUserMatchByMatchIdResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        MatchContract matchContract = (MatchContract)view;
                        matchContract.getUserMatchByMatchIdResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
