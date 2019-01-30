package com.jenking.xiaoyunhui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.ScoreRankingListActivity;
import com.jenking.xiaoyunhui.activity.ScoreRefereeActivity;
import com.jenking.xiaoyunhui.activity.ScoreSearchActivity;
import com.jenking.xiaoyunhui.activity.ScoreStudentActivity;
import com.jenking.xiaoyunhui.adapter.MainFragment2Adapter;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.ScoreContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.ScoreDetailModel;
import com.jenking.xiaoyunhui.presenters.ScorePresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment2 extends Fragment implements ScoreContract{
    private Unbinder unbinder;
    private List<ScoreDetailModel> scoreDetailModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private ScorePresenter scorePresenter;

    @BindView(R.id.empty_show)
    TextView empty_show;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.score_manager_bar)
    LinearLayout score_manager_bar;
    @BindView(R.id.score_student)
    RelativeLayout score_student;//队员 or 领队成绩栏
    @BindView(R.id.score_referee)
    RelativeLayout score_referee;//裁判员成绩栏
    @BindView(R.id.score_manager)
    RelativeLayout score_manager;//管理员成绩栏

    @OnClick(R.id.score_student)
    void scoreStudent(){
        Intent intent = new Intent(getContext(),ScoreStudentActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.score_referee)
    void scoreReferee(){
        Intent intent = new Intent(getContext(),ScoreRefereeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.search_button)
    void search_button(){
        Intent intent = new Intent(getContext(),ScoreSearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.score_ranking_list)
    void score_ranking_list(){
        Intent intent = new Intent(getContext(),ScoreRankingListActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part2,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        scoreDetailModels = new ArrayList<>();
        scorePresenter = new ScorePresenter(getContext(),this);
        scorePresenter.getAllScoreList(RequestService.getBaseParams(getContext()));
        baseRecyclerAdapter = new BaseRecyclerAdapter<ScoreDetailModel>(getContext(),scoreDetailModels,R.layout.fragment_part2_item) {
            @Override
            protected void convert(BaseViewHolder helper, ScoreDetailModel item) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.avatar2);
                requestOptions.error(R.mipmap.avatar2);
                ImageView imageView = helper.getView(R.id.item_img);
                Glide.with(getActivity()).load(BaseAPI.base_url+item.getUser_avatar()).apply(requestOptions).into(imageView);

                helper.setText(R.id.user_name,item.getUser_name());
                helper.setText(R.id.match_name,item.getMatch_title());
                helper.setText(R.id.score_value,item.getScore_value());
                helper.setText(R.id.score_unit,item.getScore_unit());
            }
        };

        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView(){
        if (AccountTool.isLogin(getContext())) {
            score_student.setVisibility(View.GONE);
            score_referee.setVisibility(View.GONE);
            score_manager.setVisibility(View.GONE);
            switch (AccountTool.getLoginUser(getContext()).getUser_type()) {
                case Const.User_type_normal:
                    score_student.setVisibility(View.VISIBLE);
                    break;
                case Const.User_type_referee:
                    score_referee.setVisibility(View.VISIBLE);
                    break;
                case Const.User_type_manager:
                    score_manager.setVisibility(View.VISIBLE);
                    break;
            }
        }else{
            score_manager_bar.setVisibility(View.GONE);
        }
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
                Log.e("getUserMatchDetail",""+scoreDetailModels.toString());
                if (resultModel.getData()!=null){
                    if (resultModel.getData().size()>3){
                        scoreDetailModels.clear();
                        for (int i=0;i<resultModel.getData().size();i++){
                            scoreDetailModels.add((ScoreDetailModel) resultModel.getData().get(i));
                        }
                        baseRecyclerAdapter.setData(scoreDetailModels);
                    }else{
                        scoreDetailModels = resultModel.getData();
                        baseRecyclerAdapter.setData(scoreDetailModels);
                    }
                }
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
