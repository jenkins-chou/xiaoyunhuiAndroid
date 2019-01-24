package com.jenking.xiaoyunhui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jenking.xiaoyunhui.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CreateMatchActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @OnClick({R.id.back})
    void back(){
        finish();
    }

    @OnClick(R.id.footer)
    void onClick(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        unbinder = ButterKnife.bind(this);
    }
}
