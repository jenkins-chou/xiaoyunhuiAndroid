package com.jenking.xiaoyunhui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.ClassListActivity;
import com.jenking.xiaoyunhui.activity.LoginActivity;
import com.jenking.xiaoyunhui.activity.MatchManagerActivity;
import com.jenking.xiaoyunhui.activity.MessageActivity;
import com.jenking.xiaoyunhui.activity.RefereeAuthctivity;
import com.jenking.xiaoyunhui.activity.RefereeManagerActivity;
import com.jenking.xiaoyunhui.activity.RefereeMatchActivity;
import com.jenking.xiaoyunhui.activity.SchoolListActivity;
import com.jenking.xiaoyunhui.activity.ScoreNormalCountActivity;
import com.jenking.xiaoyunhui.activity.SettingActivity;
import com.jenking.xiaoyunhui.activity.TeamNormalCountActivity;
import com.jenking.xiaoyunhui.activity.UserInfoModifyActivity;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.UserModel;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment4 extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.user_bar)
    RelativeLayout user_bar;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_slogan)
    TextView user_slogan;

    @BindView(R.id.common_part)
    LinearLayout common_part;
    @BindView(R.id.normal_part)
    LinearLayout normal_part;
    @BindView(R.id.referee_part)
    LinearLayout referee_part;
    @BindView(R.id.manager_part)
    LinearLayout manager_part;



    @OnClick(R.id.user_bar)
    void user_bar(){
        if (!AccountTool.isLogin(getContext())){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

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

    @OnClick(R.id.common_userinfo)
    void common_userinfo(){
        if (AccountTool.isLogin(getContext())){
            if (AccountTool.isLogin(getContext())){
                Intent intent = new Intent(getContext(),UserInfoModifyActivity.class);
                startActivity(intent);
            }else{
                CommonTipsDialog.showTip(getContext(),"温馨提示","请登录后重试",false);
            }
        }
    }

    @OnClick(R.id.normal_score)
    void normal_score(){
        Intent intent = new Intent(getContext(),ScoreNormalCountActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.normal_team)
    void normal_team(){
        Intent intent = new Intent(getContext(),TeamNormalCountActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.normal_referee_auth)
    void normal_referee_auth(){
        if (AccountTool.isLogin(getContext())){
            if (AccountTool.getUserType(getContext()).equals("1")){
                Intent intent = new Intent(getContext(),RefereeAuthctivity.class);
                startActivity(intent);
            }else if (AccountTool.getUserType(getContext()).equals("2")){
                CommonTipsDialog.showTip(getContext(),"温馨提示","您当前已经是裁判了",false);
            }else if (AccountTool.getUserType(getContext()).equals("3")){
                CommonTipsDialog.showTip(getContext(),"温馨提示","您是管理员，无需进行裁判认证",false);
            }
        }else{
            CommonTipsDialog.showTip(getContext(),"温馨提示","请登录后重试",false);
        }

    }

    @OnClick(R.id.referee_match)
    void referee_match(){
        Intent intent = new Intent(getContext(),RefereeMatchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.manager_match)
    void manager_match(){
        Intent intent = new Intent(getContext(),MatchManagerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.manager_referee)
    void referee_manager(){
        Intent intent = new Intent(getContext(),RefereeManagerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.manager_school)
    void manager_school(){
        Intent intent = new Intent(getContext(),SchoolListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.manager_class)
    void manager_class(){
        Intent intent = new Intent(getContext(),ClassListActivity.class);
        startActivity(intent);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part4,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserInfo();
    }

    //加载用户信息
    void loadUserInfo(){
        if (AccountTool.isLogin(getContext())){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.circleCrop();
            requestOptions.placeholder(R.mipmap.avatar1);
            requestOptions.error(R.mipmap.avatar1);
            UserModel userModel = AccountTool.getLoginUser(getContext());
            if (userModel!=null){
                Glide.with(getContext()).load(BaseAPI.base_url+userModel.getUser_avatar()).apply(requestOptions).into(avatar);
                user_name.setText(userModel.getUser_name());
                if (StringUtil.isNotEmpty(userModel.getUser_slogan())){
                    user_slogan.setText(userModel.getUser_slogan());
                }
            }
        }else{
            Glide.with(getContext()).load(R.mipmap.avatar1).into(avatar);
            user_name.setText("请登录");
            user_slogan.setText("登录后查看更多");
        }

        if (AccountTool.isLogin(getContext())){
            switch (AccountTool.getUserType(getContext())){
                case Const
                        .User_type_normal:
                    normal_part.setVisibility(View.VISIBLE);
                    break;
                case Const
                        .User_type_referee:
                    referee_part.setVisibility(View.VISIBLE);
                    break;
                case Const
                        .User_type_manager:
                    manager_part.setVisibility(View.VISIBLE);
                    break;
            }
        }
        else{
            normal_part.setVisibility(View.GONE);
            referee_part.setVisibility(View.GONE);
            manager_part.setVisibility(View.GONE);
        }
    }
}
