package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.VideoView;

import com.jenking.xiaoyunhui.MainActivity;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.LoginContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.LoginPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements LoginContract {

    private VideoView videoView;

    @BindView(R.id.loading)
    CommonLoading loading;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirm_password;

    private LoginPresenter loginPresenter;

    @OnClick(R.id.back)
    void back(){
        Intent intent = new Intent(context,SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.button_register)
    void button_register(){
        String username_str = username.getText().toString();
        String password_str = password.getText().toString();
        String confirm_password_str = confirm_password.getText().toString();
        if (StringUtil.isNotEmpty(username_str)
                &&StringUtil.isNotEmpty(password_str)
                &&StringUtil.isNotEmpty(confirm_password_str)){
            if (StringUtil.isEquals(password_str,confirm_password_str)){
                gotoRegister(username_str,password_str);
                setLoadingEnable(true);
            }else{
                CommonTipsDialog.showTip(context,"温馨提示","前后密码不一致",false);
            }
        }else{
            CommonTipsDialog.showTip(context,"温馨提示","请完善登录信息",false);
        }
    }

    void gotoRegister(String username, String password){
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("user_name",username);
        params.put("user_pass",password);
        if (loginPresenter!=null){
            loginPresenter.register(params);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initVideoBg();
    }

    @Override
    public void initData() {
        super.initData();
        loginPresenter = new LoginPresenter(context,this);
    }
    //初始化视频桌面
    private void initVideoBg(){
        videoView = findViewById(R.id.videoView);
        final String videoPath = Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.sign_in_video).toString();
        videoView.setVideoPath(videoPath);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.start();
                mp.setLooping(true);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoPath(videoPath);
                videoView.start();
            }
        });
    }

    void setLoadingEnable(boolean enable){
        if (loading!=null){
            loading.setVisibility(enable?View.VISIBLE:View.GONE);
        }
    }

    @Override
    public void loginResult(boolean isSuccess, Object object) {

    }

    @Override
    public void registerResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (isSuccess){
            if (object!=null){
                ResultModel resultModel = (ResultModel)object;
                if (resultModel!=null&&resultModel.getData()!=null&&resultModel.getData().size()==1){
                    AccountTool.saveUser(context,resultModel.getData().get(0));
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    CommonTipsDialog.showTip(context,"温馨提示",resultModel.getMessage()+"",false);
                }
            }
        }else {
            CommonTipsDialog.showTip(context,"温馨提示","服务器繁忙,请稍后重试",false);
        }

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
