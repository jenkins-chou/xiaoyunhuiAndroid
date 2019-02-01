package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.SegmentTabLayout;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.fragment.part3.MineTeamFragment;
import com.jenking.xiaoyunhui.fragment.part3.OtherTeamFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TeamNormalManagerActivity extends BaseActivity {
    private String[] mTitles = {"我带领的", "我参与的"};
    private ArrayList<Fragment> fragments;
    private Unbinder unbinder;
    @BindView(R.id.segmentTabLayout)
    SegmentTabLayout segmentTabLayout;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_normal_manager);
        unbinder = ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        fragments = new ArrayList<>();
        fragments.add(new MineTeamFragment());
        fragments.add(new OtherTeamFragment());
        segmentTabLayout.setTabData(mTitles,this,R.id.frameLayout,fragments);
    }
}
