package com.jenking.xiaoyunhui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jenking.xiaoyunhui.models.main.part1.IndexItem;

import java.util.List;

public class CommonRecyclerAdapter extends BaseQuickAdapter<IndexItem,BaseViewHolder> {
    public CommonRecyclerAdapter(int layoutResId, @Nullable List<IndexItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexItem item) {

    }

}
