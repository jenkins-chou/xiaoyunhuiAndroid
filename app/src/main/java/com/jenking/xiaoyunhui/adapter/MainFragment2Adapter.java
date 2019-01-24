package com.jenking.xiaoyunhui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jenking.xiaoyunhui.models.main.part2.ScoreModel;
import com.jenking.xiaoyunhui.models.main.part3.TeamModel;

import java.util.List;

public class MainFragment2Adapter extends BaseQuickAdapter<ScoreModel,BaseViewHolder> {
    public MainFragment2Adapter(int layoutResId, @Nullable List<ScoreModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreModel item) {

    }
}
