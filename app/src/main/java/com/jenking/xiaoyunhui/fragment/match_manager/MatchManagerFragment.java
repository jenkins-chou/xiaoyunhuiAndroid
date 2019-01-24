package com.jenking.xiaoyunhui.fragment.match_manager;

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
import com.jenking.xiaoyunhui.adapter.MatchManagerAdapter;
import com.jenking.xiaoyunhui.models.main.match_manager.MatchModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MatchManagerFragment extends Fragment {
    private Unbinder unbinder;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<MatchModel> matchModels;
    private MatchManagerAdapter matchManagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_manager,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        matchModels = new ArrayList<>();
        matchModels.add(new MatchModel(""));
        matchModels.add(new MatchModel(""));
        matchModels.add(new MatchModel(""));
        matchManagerAdapter = new MatchManagerAdapter(R.layout.fragment_match_manager_item,matchModels);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(matchManagerAdapter);
    }

    public static MatchManagerFragment init(){
        MatchManagerFragment matchManagerFragment = new MatchManagerFragment();
        return matchManagerFragment;
    }
}
