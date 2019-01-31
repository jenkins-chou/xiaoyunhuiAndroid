package com.jenking.xiaoyunhui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.SlidingTabLayout;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.fragment.match_manager.MatchManagerFragment;
import com.jenking.xiaoyunhui.fragment.match_manager.MatchProcessManagerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MatchProcessManagerActivity extends BaseActivity {
    private Unbinder unbinder;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @OnClick({R.id.back})
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_process_manager);
        unbinder = ButterKnife.bind(this);
        slidingTabLayout.setViewPager(viewPager,getTitles(),this,getFragments());
    }

    private String[] getTitles(){
        return new String[]{"报名中","比赛中","比赛完毕","公布成绩"};
    }

    private ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i=0;i<4;i++){
            fragments.add(MatchProcessManagerFragment.init((i+1)+""));
        }
        return fragments;
    }


}
