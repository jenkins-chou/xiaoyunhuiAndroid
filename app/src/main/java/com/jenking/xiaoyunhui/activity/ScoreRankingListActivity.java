package com.jenking.xiaoyunhui.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.ScoreContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.ScoreDetailModel;
import com.jenking.xiaoyunhui.presenters.ScorePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ScoreRankingListActivity extends BaseActivity implements ScoreContract{

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty_show)
    TextView empty_show;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    private List<ScoreDetailModel> scoreDetailModels;
    private ScorePresenter scorePresenter;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_ranking_list);
    }

    @Override
    public void initData() {
        super.initData();
        scoreDetailModels = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<ScoreDetailModel>(context,scoreDetailModels,R.layout.activity_score_ranking_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, ScoreDetailModel item) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(R.mipmap.avatar2);
                requestOptions.placeholder(R.mipmap.avatar2);
                ImageView user_avatar = helper.getView(R.id.user_avatar);
                Glide.with(context).load(BaseAPI.base_url+item.getUser_avatar()).apply(requestOptions).into(user_avatar);

                helper.setText(R.id.user_name,item.getUser_name());
                helper.setText(R.id.match_title,item.getMatch_title());
                helper.setText(R.id.score_value,item.getScore_value());
                helper.setText(R.id.score_unit,item.getScore_unit());
            }
        };
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        scorePresenter = new ScorePresenter(this,this);
        scorePresenter.getAllScoreList(RequestService.getBaseParams(this));
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
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                scoreDetailModels = resultModel.getData();
                Log.e("getUserMatchDetail",""+scoreDetailModels.toString());
                baseRecyclerAdapter.setData(scoreDetailModels);
            }
        }
        refreshUser();
    }

    private void refreshUser(){
        if (scoreDetailModels==null||scoreDetailModels.size()<=0){
            empty_show.setVisibility(View.VISIBLE);
        }else{
            empty_show.setVisibility(View.GONE);
        }
    }

    @Override
    public void addScoresResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
