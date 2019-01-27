package com.jenking.xiaoyunhui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.tools.AccountTool;

import butterknife.OnClick;


public class SettingActivity extends BaseActivity {

    @OnClick(R.id.logout)
    void logout(){
        AccountTool.logout(context);
        CommonTipsDialog.showTip(context,"温馨提示","退出登录成功",false);
        finish();
    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
