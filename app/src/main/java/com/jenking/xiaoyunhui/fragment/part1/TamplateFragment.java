package com.jenking.xiaoyunhui.fragment.part1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MatchDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TamplateFragment extends Fragment {

    private Unbinder unbinder;
    private String classiFy;
    private List<String> datas;
    private BaseQuickAdapter baseQuickAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1_template,container,false);
        unbinder = ButterKnife.bind(this,view);
        initView();
        return view;
    }

    public void setClassiFy(String classiFy){
        this.classiFy = classiFy;
    }

    public static TamplateFragment init(String classiFy){
        TamplateFragment tamplateFragment = new TamplateFragment();
        tamplateFragment.setClassiFy(classiFy);
        return tamplateFragment;
    }

    private void initView(){
        datas = new ArrayList<>();
        datas.add(":");
        datas.add(":");
        datas.add(":");
        datas.add(":");
        baseQuickAdapter = new BaseQuickAdapter<String,BaseViewHolder>(R.layout.fragment_part1_template_item,datas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(),MatchDetailActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseQuickAdapter);
    }
}
