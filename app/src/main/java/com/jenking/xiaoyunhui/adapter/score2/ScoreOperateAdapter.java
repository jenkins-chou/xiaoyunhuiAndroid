package com.jenking.xiaoyunhui.adapter.score2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.ScoreOperateActivity;
import com.jenking.xiaoyunhui.api.BaseAPI;

import java.util.List;

public class ScoreOperateAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ScoreOperateAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        ImageView imageView = helper.getView(R.id.item_avatar);
//
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.mipmap.avatar2);
//        requestOptions.error(R.mipmap.avatar2);
//        Glide.with(ScoreOperateActivity.this).load(BaseAPI.base_url+item.getUser_avatar()).into(imageView);
//        helper.setText(R.id.item_name,item.getUser_name());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        EditText editText = holder.getView(R.id.item_edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("position : "+position,editable.toString());
            }
        });
    }
}
