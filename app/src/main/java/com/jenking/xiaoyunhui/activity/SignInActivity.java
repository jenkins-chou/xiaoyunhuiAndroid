package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.jenking.xiaoyunhui.MainActivity;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.tools.AccountTool;

import butterknife.BindView;

public class SignInActivity extends BaseActivity {

    private VideoView videoView;
    private LinearLayout button_login;
    private LinearLayout button_register;
    @BindView(R.id.root_view)
    RelativeLayout root_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        checkLogin();
    }

    //检查是否已经登录
    private void checkLogin(){
        if (AccountTool.isLogin(context)){
            Intent intent = new Intent(context,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            root_view.setVisibility(View.VISIBLE);
            initVideoBg();
            initView();
        }
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

    public void initView(){
        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_register = findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
