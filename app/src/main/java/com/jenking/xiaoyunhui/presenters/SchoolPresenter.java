package com.jenking.xiaoyunhui.presenters;

import android.content.Context;
import android.util.Log;

import com.jenking.xiaoyunhui.api.ApiService;
import com.jenking.xiaoyunhui.api.ApiUtil;
import com.jenking.xiaoyunhui.contacts.BaseCallBack;
import com.jenking.xiaoyunhui.contacts.SchoolContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.SchoolModel;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter模板
 */
public class SchoolPresenter {

    private Context context;

    private BaseCallBack view;

    public SchoolPresenter(Context context, BaseCallBack view){
        this.context = context;
        this.view = view;
    }

    //带参基本请求方法
    public void getSchools(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getSchools(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<SchoolModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel resultModel) {
                        //更新视图
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.getAllSchoolResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.getAllSchoolResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //带参基本请求方法
    public void addSchool(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .addSchool(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<SchoolModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel resultModel) {
                        //更新视图
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.addSchoolResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.addSchoolResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //带参基本请求方法
    public void getSchoolById(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .getSchool(params)
                .subscribeOn(Schedulers.io())//后台处理线程
                .observeOn(AndroidSchedulers.mainThread())//指定回调发生的线程
                .subscribe(new Observer<ResultModel<SchoolModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.print(d);
                    }

                    @Override
                    public void onNext(ResultModel resultModel) {
                        //更新视图
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.getSchoolByIdResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.getSchoolByIdResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //更新学校
    public void updateSchool(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .updateSchool(params)
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
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.updateSchoolResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.updateSchoolResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    //删除学校
    public void deleteSchool(Map<String,String> params){
        if (params==null)return;
        Log.e("开始请求","p-->"+params.toString());
        new ApiUtil(context)
                .getServer(ApiService.class)
                //记得更改请求接口数据
                .deleteSchool(params)
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
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.deleteSchoolResult(true,resultModel);
                        //view.success(resultModel);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.print("----error");
                        e.printStackTrace();
                        SchoolContract schoolContract = (SchoolContract)view;
                        schoolContract.deleteSchoolResult(false,e);
                        //view.failed(e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
