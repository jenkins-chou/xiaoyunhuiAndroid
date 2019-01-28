package com.jenking.xiaoyunhui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.SegmentTabLayout;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.fragment.referee_manager.RefereeAddListFragment;
import com.jenking.xiaoyunhui.fragment.referee_manager.RefereeApplyListFragment;
import com.jenking.xiaoyunhui.fragment.referee_manager.RefereeListFragment;
import com.jenking.xiaoyunhui.fragment.score1.PublishScoreFragment;
import com.jenking.xiaoyunhui.fragment.score1.UnPublishScoreFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RefereeManagerActivity extends BaseActivity {

//    private String[] mTitles = { "裁判列表","裁判申请","新增裁判"};
private String[] mTitles = { "裁判列表","申请列表"};
    @BindView(R.id.segmentTabLayout)
    SegmentTabLayout segmentTabLayout;
    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_manager);
    }

    @Override
    public void initView() {
        super.initView();
        segmentTabLayout.setTabData(mTitles);
        segmentTabLayout.setTabData(mTitles,this,R.id.frameLayout,getFragments());
    }

    public ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new RefereeListFragment());
        list.add(new RefereeApplyListFragment());
//        list.add(new RefereeAddListFragment());
        return list;
    }
}
