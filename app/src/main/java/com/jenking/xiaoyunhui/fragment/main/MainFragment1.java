package com.jenking.xiaoyunhui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MatchSearchActivity;
import com.jenking.xiaoyunhui.fragment.part1.IndexFragment;
import com.jenking.xiaoyunhui.fragment.part1.TamplateFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment1 extends Fragment {
    private Unbinder unbinder;

    //https://github.com/H07000223/FlycoTabLayout/blob/master/README_CN.md
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @OnClick(R.id.search_button)
    void search_button(){
        Intent intent = new Intent(getContext(),MatchSearchActivity.class);
        startActivity(intent);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        initView();
        return view;
    }

    private void initData(){

    }

    private void initView(){
        slidingTabLayout.setViewPager(viewPager,getTitles(),getActivity(),getFragments());
    }

    private String[] getTitles(){
        return new String[]{"首页","田径","跳远","单杠","双杠","游泳","跳水","骑行"};
    }

    private ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        for (int i = 0;i<7;i++){
            fragments.add(new TamplateFragment());
        }
        return fragments;
    }


}
