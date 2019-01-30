package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.library.BaseRecyclerAdapter;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.adapter.score2.ScoreOperateAdapter;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.contacts.UserMatchContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.MatchDetailModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.UserModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.presenters.UserMatchPresenter;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ScoreOperateActivity extends BaseActivity implements MatchContract,UserMatchContract {


    private String match_id;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    private List<UserModel> userModels;
    private MatchPresenter matchPresenter;//用于获取比赛详情
    private UserMatchPresenter userMatchPresenter;

    private MatchDetailModel matchDetailModel;

    private Map<String,String> scoresMap;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.loading)
    CommonLoading loading;

    @BindView(R.id.match_image)
    ImageView match_image;
    @BindView(R.id.match_create_time)
    TextView match_create_time;
    @BindView(R.id.match_title)
    TextView match_title;
    @BindView(R.id.match_type)
    TextView match_type;
    @BindView(R.id.match_number)
    TextView match_number;
    @BindView(R.id.match_time)
    TextView match_time;

    @BindView(R.id.empty_show)
    TextView empty_show;

    @BindView(R.id.match_status)
    TextView match_status;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.submit)
    void submit(){
        if (matchDetailModel==null||
                matchDetailModel.getMatch_status()==null||
                !matchDetailModel.getMatch_status().equals(Const.Match_type_three)){
            CommonTipsDialog.showTip(this,"温馨提示","当前比赛不能提交成绩",false);
        }else if (match_id==null||match_id.equals("")){
            CommonTipsDialog.showTip(this,"温馨提示","页面初始化失败",false);
//            return;
        }else if (userModels==null||userModels.size()<=0){
            CommonTipsDialog.showTip(this,"温馨提示","暂时无人员报名",false);
        }else{
            for (int i = 0;i<userModels.size();i++){
                Log.e("user_id",userModels.get(i).getUser_id());
                Log.e("match_id",match_id);
                Log.e("score_value",scoresMap.get(userModels.get(i).getUser_id())+"");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_operate);
    }

    @Override
    public void initData(){
        userModels = new ArrayList<>();
        scoresMap = new HashMap<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<UserModel>(this,userModels,R.layout.activity_score_operate_item) {
            @Override
            protected void convert(com.github.library.BaseViewHolder helper, final UserModel item) {
                ImageView imageView = helper.getView(R.id.item_avatar);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.avatar2);
                requestOptions.error(R.mipmap.avatar2);
                Glide.with(ScoreOperateActivity.this).load(BaseAPI.base_url+item.getUser_avatar()).apply(requestOptions).into(imageView);
                helper.setText(R.id.item_name,item.getUser_name());

                final EditText editText = helper.getView(R.id.item_edittext);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        scoresMap.put(item.user_id,editable.toString());
//                        Log.d("user_id : "+item.user_id, "afterTextChanged: "+editable.toString());
                    }
                });
            }
        };
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);
        matchPresenter = new MatchPresenter(context,this);
        userMatchPresenter = new UserMatchPresenter(context,this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        Intent intent = getIntent();
        if (intent!=null){
            match_id = intent.getStringExtra("match_id");

            Map<String,String> params = RequestService.getBaseParams(context);
            params.put("match_id",match_id);
            matchPresenter.getMatchById(params);
            userMatchPresenter.getUserMatchDetailByMatchId(params);
            setLoadingEnable(true);
        }
    }

    @Override
    public void getAllMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByStatusResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByTypeResult(boolean isSuccess, Object object) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void getMatchByIdResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel.getData()!=null&&resultModel.getData().size()>0){
                matchDetailModel = (MatchDetailModel) resultModel.getData().get(0);
                Log.e("matchDetailModel",matchDetailModel.toString());
            }
        }
        refreshVeiw();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void refreshVeiw(){
        if (matchDetailModel!=null){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.item_default_img);
            requestOptions.placeholder(R.mipmap.item_default_img);
            if (!isDestroyed()){
                Glide.with(this).load(BaseAPI.base_url+matchDetailModel.getMatch_img()).into(match_image);
            }
            match_create_time.setText(StringUtil.getStrTime(matchDetailModel.getMatch_create_time(),"yyyy-MM-dd HH:mm:ss"));
            match_title.setText(matchDetailModel.getMatch_title());
            match_type.setText(matchDetailModel.getMatch_type_name());
            match_number.setText(matchDetailModel.getMatch_athletes_num());
            match_time.setText(StringUtil.getStrTime(matchDetailModel.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));

            switch (matchDetailModel.getMatch_status()){
                case Const.Match_type_one:
                    match_status.setText("报名中");
                    break;
                case Const.Match_type_two:
                    match_status.setText("比赛中");
                    break;
                case Const.Match_type_three:
                    match_status.setText("已完成");
                    break;
                case Const.Match_type_four:
                    match_status.setText("已公布成绩");
                    break;
            }


        }else {
            //TODO 显示错误信息
        }
    }

    @Override
    public void addMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void addUserMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getUserMatchByMatchIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByRefereeIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }

    void setLoadingEnable(boolean enable){
        if (loading!=null){
            loading.setVisibility(enable?View.VISIBLE:View.GONE);
        }
    }

    @Override
    public void getUserMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getUserMatchDetailByMatchIdResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                userModels = resultModel.getData();
                Log.e("getUserMatchDetail",""+userModels.toString());
                baseRecyclerAdapter.setData(userModels);
            }
        }
        refreshUser();
    }

    private void refreshUser(){
        if (userModels==null||userModels.size()<=0){
            empty_show.setVisibility(View.VISIBLE);
        }else{
            empty_show.setVisibility(View.GONE);
        }
    }


    @Override
    public void getRefereeMatchByUserIdResult(boolean isSuccess, Object object) {

    }
}
