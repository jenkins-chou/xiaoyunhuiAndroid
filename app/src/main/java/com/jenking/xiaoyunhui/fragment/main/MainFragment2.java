package com.jenking.xiaoyunhui.fragment.main;

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

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MatchSearchActivity;
import com.jenking.xiaoyunhui.activity.ScoreRankingListActivity;
import com.jenking.xiaoyunhui.activity.ScoreRefereeActivity;
import com.jenking.xiaoyunhui.activity.ScoreSearchActivity;
import com.jenking.xiaoyunhui.activity.ScoreStudentActivity;
import com.jenking.xiaoyunhui.adapter.MainFragment2Adapter;
import com.jenking.xiaoyunhui.models.main.part2.ScoreModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment2 extends Fragment {
    private Unbinder unbinder;
    private List<ScoreModel> scoreModels;
    private MainFragment2Adapter mainFragment2Adapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.score_student)
    void scoreStudent(){
        Intent intent = new Intent(getContext(),ScoreStudentActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.score_referee)
    void scoreReferee(){
        Intent intent = new Intent(getContext(),ScoreRefereeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.search_button)
    void search_button(){
        Intent intent = new Intent(getContext(),ScoreSearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.score_ranking_list)
    void score_ranking_list(){
        Intent intent = new Intent(getContext(),ScoreRankingListActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part2,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        scoreModels = new ArrayList<>();
        scoreModels.add(new ScoreModel(""));
        scoreModels.add(new ScoreModel(""));
        scoreModels.add(new ScoreModel(""));
        scoreModels.add(new ScoreModel(""));
        mainFragment2Adapter = new MainFragment2Adapter(R.layout.fragment_part2_item,scoreModels);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(mainFragment2Adapter);
    }
}
