package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.SlidingTabLayout;
import com.jenking.xiaoyunhui.R;
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
        return new String[]{"全部","审核通过","审核不通过","未审核"};
    }

    private ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(new MatchManagerFragment());
        for (int i=0;i<4;i++){
            fragments.add(MatchManagerFragment.init());
        }
        return fragments;
    }
}
