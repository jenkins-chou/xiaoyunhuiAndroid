package com.jenking.xiaoyunhui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.customui.CommonScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MatchDetailActivity extends BaseActivity {

    private Unbinder unbinder;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.scrollView)
    CommonScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        initData();
    }

    @Override
    public void initView() {
        super.initView();
        header.getBackground().mutate().setAlpha(0);
        scrollView.setScrollViewListener(new CommonScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(CommonScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y<=0){
                    header.getBackground().mutate().setAlpha(0);
                }else if (0<y&&y<255){
                    header.getBackground().mutate().setAlpha(y);
                }else{
                    header.getBackground().mutate().setAlpha(255);
                }
            }
        });
    }

    public void initData(){
        unbinder = ButterKnife.bind(this);
    }
}
