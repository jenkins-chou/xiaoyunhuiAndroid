package com.jenking.xiaoyunhui.fragment.score2;

import android.content.Context;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.ScoreOperateActivity;
import com.jenking.xiaoyunhui.adapter.score1.PublishAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UnPublishScoreRefereeFragment extends Fragment {
    private Unbinder unbinder;
    private Context context;
    private PublishAdapter publishAdapter;
    private List<String> datas;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unpublish_score2,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        context = getActivity();
        datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        publishAdapter = new PublishAdapter(R.layout.fragment_publish_score2_item,datas);
        publishAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context,ScoreOperateActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(publishAdapter);
    }
}
