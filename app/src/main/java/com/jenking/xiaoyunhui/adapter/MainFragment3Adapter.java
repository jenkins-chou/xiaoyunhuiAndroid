package com.jenking.xiaoyunhui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jenking.xiaoyunhui.models.main.part3.TeamModel;

import java.util.List;

public class MainFragment3Adapter extends BaseQuickAdapter<TeamModel,BaseViewHolder> {
    public MainFragment3Adapter(int layoutResId, @Nullable List<TeamModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamModel item) {

    }
}
