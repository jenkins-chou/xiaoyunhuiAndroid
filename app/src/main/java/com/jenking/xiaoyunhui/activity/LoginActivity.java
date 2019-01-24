package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.jenking.xiaoyunhui.MainActivity;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.LoginContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.UserModel;
import com.jenking.xiaoyunhui.presenters.LoginPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.SPUtils;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity implements LoginContract {

    private Unbinder unbinder;
    private VideoView videoView;

    private LoginPresenter loginPresenter;

    @BindView(R.id.loading)
    CommonLoading loading;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.back)
    void back(){
        Intent intent = new Intent(context,SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.button_login)
    void button_login(){
        String username_str = username.getText().toString();
        String password_str = password.getText().toString();
        if (StringUtil.isNotEmpty(username_str)&&StringUtil.isNotEmpty(password_str)){
            gotoLogin(username_str,password_str);
            setLoadingEnable(true);
        }else{
            CommonTipsDialog.showTip(context,"温馨提示","请完善登录信息",false);
        }
    }

    void gotoLogin(String username,String password){
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("user_name",username);
        params.put("user_pass",password);
        if (loginPresenter!=null){
            loginPresenter.login(params);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
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
        setLoadingEnable(false);
        if (object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null&&resultModel.getData().size()==1){
                AccountTool.saveUser(context,resultModel.getData().get(0));
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else{
                CommonTipsDialog.create(context,"温馨提示","用户不存在请前往注册",false)
                        .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void confirm() {
                                Intent intent = new Intent(context,RegisterActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
            Log.e("loginResult",""+object.toString());
        }

    }

    @Override
    public void registerResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
