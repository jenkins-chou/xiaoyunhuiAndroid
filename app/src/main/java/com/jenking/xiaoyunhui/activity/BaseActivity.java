package com.jenking.xiaoyunhui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity {


    //别名
    private String TAG;
    public Context context;

    //类名
    String className ;
    private Unbinder unbinder;

    //下一步class
    public Class<?> nextStep;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBarColor();
        setBinder();
        initData();
        initView();
    }

    private void setStatusBarColor() {
//        StatusBarUtil.setColor(this,getResources().getColor(R.color.baseColor),0);
    }

    private void setBinder() {
        unbinder = ButterKnife.bind(this);
    }

    public void initData(){
        context = this;
        TAG = getClass().getCanonicalName();
        Intent intent = getIntent();
        if (intent!=null){
            nextStep = (Class<?>)intent.getSerializableExtra("nextStep");
        }
    }

    public void initView(){}
}
