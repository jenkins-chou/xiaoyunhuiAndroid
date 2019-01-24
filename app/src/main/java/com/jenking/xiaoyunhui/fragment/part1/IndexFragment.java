package com.jenking.xiaoyunhui.fragment.part1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MatchDetailActivity;
import com.jenking.xiaoyunhui.activity.MatchManagerActivity;
import com.jenking.xiaoyunhui.activity.MineMatchActivity;
import com.jenking.xiaoyunhui.adapter.CommonRecyclerAdapter;
import com.jenking.xiaoyunhui.adapter.MatchManagerAdapter;
import com.jenking.xiaoyunhui.models.main.part1.IndexItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IndexFragment extends Fragment {
    private Unbinder unbinder;
    private List<IndexItem> fragmentPart1Models;
    private CommonRecyclerAdapter commonRecyclerAdapter;
    private View headerView;
    private Context context;

    private LinearLayout mineMatch;//我的比赛项目
    private TextView tv1;//教练入口

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1_index,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        initView();
        return view;
    }

    private void initView(){

    }

    private void initData(){
        context = this.getContext();
        fragmentPart1Models = new ArrayList<>();
        fragmentPart1Models.add(new IndexItem("即将开始","200000","阿斯达三大","大三大四的"));
        fragmentPart1Models.add(new IndexItem("即将开始","200000","阿斯达三大","大三大四的"));
        fragmentPart1Models.add(new IndexItem("即将开始","200000","阿斯达三大","大三大四的"));
        fragmentPart1Models.add(new IndexItem("即将开始","200000","阿斯达三大","大三大四的"));
        commonRecyclerAdapter = new CommonRecyclerAdapter(R.layout.fragment_part1_index_item,fragmentPart1Models);
        //添加头部
        headerView = getLayoutInflater().inflate(R.layout.fragment_part1_index_header,null,false);
        commonRecyclerAdapter.addHeaderView(headerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(commonRecyclerAdapter);

        commonRecyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context,MatchDetailActivity.class);
                startActivity(intent);
            }
        });

        tv1 = headerView.findViewById(R.id.referee_in);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MatchManagerActivity.class);
                startActivity(intent);
            }
        });

        mineMatch = headerView.findViewById(R.id.mine_match);
        mineMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MineMatchActivity.class);
                startActivity(intent);
            }
        });
    }
}
