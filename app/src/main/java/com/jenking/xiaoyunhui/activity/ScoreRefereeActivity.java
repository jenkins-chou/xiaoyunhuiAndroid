package com.jenking.xiaoyunhui.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.SegmentTabLayout;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.fragment.score1.PublishScoreFragment;
import com.jenking.xiaoyunhui.fragment.score1.UnPublishScoreFragment;
import com.jenking.xiaoyunhui.fragment.score2.PublishScoreRefereeFragment;
import com.jenking.xiaoyunhui.fragment.score2.UnPublishScoreRefereeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ScoreRefereeActivity extends AppCompatActivity {

    private String[] mTitles = { "未公布","已公布"};
    private Unbinder unbinder;
    private Context context;

    @BindView(R.id.segmentTabLayout)
    SegmentTabLayout segmentTabLayout;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_referee);
        initData();
    }


    protected void initData() {
        context = this;
        unbinder = ButterKnife.bind(this);
        segmentTabLayout.setTabData(mTitles);
        segmentTabLayout.setTabData(mTitles,this,R.id.frameLayout,getFragments());
    }

    public ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new UnPublishScoreRefereeFragment());
        list.add(new PublishScoreRefereeFragment());
        return list;
    }
}
