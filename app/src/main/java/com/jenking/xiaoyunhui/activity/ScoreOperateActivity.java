package com.jenking.xiaoyunhui.activity;

import android.os.RecoverySystem;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jenking.xiaoyunhui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ScoreOperateActivity extends AppCompatActivity {

    private Unbinder unbinder;

    private BaseQuickAdapter baseQuickAdapter;
    private List<String> datas;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_operate);
        initData();
    }

    private void initData(){
        unbinder = ButterKnife.bind(this);
        datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        baseQuickAdapter = new BaseQuickAdapter<String,BaseViewHolder>(R.layout.activity_score_operate_item,datas) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        };
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseQuickAdapter);
    }
}
