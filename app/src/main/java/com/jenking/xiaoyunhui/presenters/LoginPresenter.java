package com.jenking.xiaoyunhui.presenters;

import android.content.Context;
import android.util.Log;

import com.jenking.xiaoyunhui.api.ApiService;
import com.jenking.xiaoyunhui.api.ApiUtil;
import com.jenking.xiaoyunhui.contacts.BaseCallBack;
import com.jenking.xiaoyunhui.contacts.LoginContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.UserModel;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter模板
 */
public class LoginPresenter {

    private Context context;

    private BaseCallBack view;

    public LoginPresenter(Context context, BaseCallBack view){
        this.context = context;
        this.view = view;
    }

    //带参基本请求方法
    public void login(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .login(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<UserModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<UserModel> resultModel) {
                        //更新视图
                        LoginContract loginContract = (LoginContract)view;
                        loginContract.loginResult(true,resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        LoginContract loginContract = (LoginContract)view;
                        loginContract.loginResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void register(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .register(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<UserModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<UserModel> resultModel) {
                        //更新视图
                        LoginContract loginContract = (LoginContract)view;
                        loginContract.registerResult(true,resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        LoginContract loginContract = (LoginContract)view;
                        loginContract.registerResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
