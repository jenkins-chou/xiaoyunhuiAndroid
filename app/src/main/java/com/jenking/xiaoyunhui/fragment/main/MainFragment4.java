package com.jenking.xiaoyunhui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MessageActivity;
import com.jenking.xiaoyunhui.activity.SettingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment4 extends Fragment {

    private Unbinder unbinder;

    @OnClick(R.id.message)
    void message(){
        Intent intent = new Intent(getContext(),MessageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.setting)
    void setting(){
        Intent intent = new Intent(getContext(),SettingActivity.class);
        startActivity(intent);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part4,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }
}
