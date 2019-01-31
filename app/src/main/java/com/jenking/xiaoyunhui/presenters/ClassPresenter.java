package com.jenking.xiaoyunhui.presenters;

import android.content.Context;
import android.util.Log;

import com.jenking.xiaoyunhui.api.ApiService;
import com.jenking.xiaoyunhui.api.ApiUtil;
import com.jenking.xiaoyunhui.contacts.BaseCallBack;
import com.jenking.xiaoyunhui.contacts.ClassContract;
import com.jenking.xiaoyunhui.models.base.ClassModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter模板
 */
public class ClassPresenter {

    private Context context;

    private BaseCallBack view;

    public ClassPresenter(Context context, BaseCallBack view){
        this.context = context;
        this.view = view;
    }

    //带参基本请求方法
    public void getAllClass(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getAllClass(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<ClassModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<ClassModel> resultModel) {
                        //更新视图
                        ClassContract classContract = (ClassContract)view;
                        classContract.getAllClassResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        ClassContract classContract = (ClassContract)view;
                        classContract.getAllClassResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void addClass(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .addClass(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<ClassModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<ClassModel> resultModel) {
                        //更新视图
                        ClassContract classContract = (ClassContract)view;
                        classContract.addClassResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        ClassContract classContract = (ClassContract)view;
                        classContract.addClassResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getClassById(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getClassById(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<ClassModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel<ClassModel> resultModel) {
                        //更新视图
                        ClassContract classContract = (ClassContract)view;
                        classContract.getClassResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        ClassContract classContract = (ClassContract)view;
                        classContract.getClassResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
