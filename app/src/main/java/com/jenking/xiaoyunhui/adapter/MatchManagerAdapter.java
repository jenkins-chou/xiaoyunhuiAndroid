package com.jenking.xiaoyunhui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jenking.xiaoyunhui.models.base.MatchModel;

import java.util.List;

public class MatchManagerAdapter extends BaseQuickAdapter<MatchModel,BaseViewHolder> {
    public MatchManagerAdapter(int layoutResId, @Nullable List<MatchModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchModel item) {

    }
}
