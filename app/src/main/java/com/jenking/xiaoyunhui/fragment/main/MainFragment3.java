package com.jenking.xiaoyunhui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MineTeamActivity;
import com.jenking.xiaoyunhui.activity.ScoreSearchActivity;
import com.jenking.xiaoyunhui.activity.TeamAllActivity;
import com.jenking.xiaoyunhui.activity.TeamSearchActivity;
import com.jenking.xiaoyunhui.adapter.MainFragment3Adapter;
import com.jenking.xiaoyunhui.fragment.part3.MineTeamFragment;
import com.jenking.xiaoyunhui.fragment.part3.OtherTeamFragment;
import com.jenking.xiaoyunhui.models.main.part3.TeamModel;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment3 extends Fragment {
    private String[] mTitles = {"我带领的", "我参与的"};
    private ArrayList<Fragment> fragments;
    private Unbinder unbinder;
    @BindView(R.id.segmentTabLayout)
    SegmentTabLayout segmentTabLayout;

    @OnClick(R.id.search_button)
    void search_button(){
        Intent intent = new Intent(getContext(),TeamSearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.allteam)
    void allteam(){
        Intent intent = new Intent(getContext(),TeamAllActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part3,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        fragments = new ArrayList<>();
        fragments.add(new MineTeamFragment());
        fragments.add(new OtherTeamFragment());
        segmentTabLayout.setTabData(mTitles,getActivity(),R.id.frameLayout,fragments);
    }
}
