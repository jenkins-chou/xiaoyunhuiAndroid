package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.SlidingTabLayout;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.fragment.match_manager.MatchManagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MatchManagerActivity extends BaseActivity {

    private Unbinder unbinder;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @OnClick({R.id.back})
    void back(){
        finish();
    }

    @OnClick(R.id.match_process_manager)
    void match_process_manager(){
        Intent intent = new Intent(this,MatchProcessManagerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.footer)
    void footer(){
        Intent intent = new Intent(this,CreateMatchActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_manager);
        unbinder = ButterKnife.bind(this);
        slidingTabLayout.setViewPager(viewPager,getTitles(),this,getFragments());
    }

    private String[] getTitles(){
        return new String[]{"全部","报名中","比赛中","比赛完毕","公布成绩"};
    }

    private ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i=0;i<5;i++){
            fragments.add(MatchManagerFragment.init(i+""));
        }
        return fragments;
    }
}
