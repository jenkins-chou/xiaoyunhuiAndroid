package com.jenking.xiaoyunhui.fragment.part3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.adapter.MainFragment3Adapter;
import com.jenking.xiaoyunhui.models.main.part3.TeamModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OtherClassFragment extends Fragment {
    private Unbinder unbinder;
    private List<TeamModel> teamModels;
    private MainFragment3Adapter mainFragment3Adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mine_class,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        teamModels = new ArrayList<>();
        teamModels.add(new TeamModel(""));
        teamModels.add(new TeamModel(""));
        teamModels.add(new TeamModel(""));
        teamModels.add(new TeamModel(""));
        teamModels.add(new TeamModel(""));
        teamModels.add(new TeamModel(""));
        teamModels.add(new TeamModel(""));
        mainFragment3Adapter = new MainFragment3Adapter(R.layout.fragment_part3_layout_item,teamModels);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        recyclerView.setAdapter(mainFragment3Adapter);
    }
}
