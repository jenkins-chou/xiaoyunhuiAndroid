package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.google.gson.Gson;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.contacts.ScoreContract;
import com.jenking.xiaoyunhui.contacts.UserMatchContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.dialog.CommonBottomListDialog;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.MatchDetailModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.ScoreDetailModel;
import com.jenking.xiaoyunhui.models.base.UserMatchModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.presenters.ScorePresenter;
import com.jenking.xiaoyunhui.presenters.UserMatchPresenter;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ScoreUpdateActivity extends BaseActivity implements ScoreContract{

    private ScorePresenter scorePresenter;
    private ScoreDetailModel scoreDetailModel;

    @BindView(R.id.input_score)
    EditText input_score;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.submit_score)
    void submit_score(){
        String input_score_str = input_score.getText().toString();
        if (StringUtil.isNotEmpty(input_score_str)){
            if (scoreDetailModel!=null&&StringUtil.isNotEmpty(scoreDetailModel.getScore_id())&&scorePresenter!=null){
                Map<String,String> params = RequestService.getBaseParams(this);
                params.put("score_id",scoreDetailModel.getScore_id());
                params.put("score_value",input_score_str);
                Log.e("params",params.toString());
                scorePresenter.updateScore(params);
            }else{
                Toast.makeText(this, "请返回重试", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "请输入成绩", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_update);
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent!=null){
            String json = intent.getStringExtra("data");
            if (json!=null&&!json.equals("")){
                scoreDetailModel = new Gson().fromJson(json,ScoreDetailModel.class);
            }
        }
        scorePresenter = new ScorePresenter(this,this);
    }

    @Override
    public void getScoreListByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getScoreListByMatchIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getScorePublishListByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getAllScoreList(boolean isSuccess, Object object) {

    }

    @Override
    public void addScoresResult(boolean isSuccess, Object object) {

    }

    @Override
    public void updateScoreResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
