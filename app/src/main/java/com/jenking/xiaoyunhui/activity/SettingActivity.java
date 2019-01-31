package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.dialog.CommonBottomListDialog;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.tools.AccountTool;

import butterknife.BindView;
import butterknife.OnClick;


public class SettingActivity extends BaseActivity {

    @BindView(R.id.logout)
    TextView logout;
    @OnClick(R.id.logout)
    void logout(){
        CommonTipsDialog.create(this,"温馨提示","确定要退出登录吗？",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {
                        finish();
                    }
                    @Override
                    public void confirm() {
                        AccountTool.logout(context);
                        finish();
                    }
                }).show();
    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick({R.id.modify_userinfo})
    void modify_userinfo(){
        if (AccountTool.isLogin(this)){
            Intent intent = new Intent(this,UserInfoModifyActivity.class);
            startActivity(intent);
        }else{
            CommonTipsDialog.showTip(this,"温馨提示","请登录后重试",false);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        super.initView();

        if (AccountTool.isLogin(context)){
            logout.setVisibility(View.VISIBLE);
        }else{
            logout.setVisibility(View.GONE);
        }
    }
}
