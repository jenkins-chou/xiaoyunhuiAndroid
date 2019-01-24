package com.jenking.xiaoyunhui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jenking.xiaoyunhui.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TeamDetailActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private Context context;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        unbinder = ButterKnife.bind(this);
    }
}
