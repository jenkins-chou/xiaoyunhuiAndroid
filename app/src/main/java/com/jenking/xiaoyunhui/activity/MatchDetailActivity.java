package com.jenking.xiaoyunhui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jenking.xiaoyunhui.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MatchDetailActivity extends BaseActivity {

    private Unbinder unbinder;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        initData();
    }

    @Override
    public void initView() {
        super.initView();
    }

    public void initData(){
        unbinder = ButterKnife.bind(this);
    }
}
