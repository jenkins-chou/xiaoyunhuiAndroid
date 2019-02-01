package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.TeamContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.TeamModel;
import com.jenking.xiaoyunhui.presenters.TeamPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TeamSearchActivity extends BaseActivity implements TeamContract {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private Unbinder unbinder;


    private List<TeamModel> teamModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private TeamPresenter teamPresenter;

    @BindView(R.id.input_team_keyword)
    EditText input_team_keyword;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.search_button)
    void search(){
        String input_keyword = input_team_keyword.getText().toString();
        if (StringUtil.isNotEmpty(input_keyword)){
            getData(input_keyword);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_search);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        teamModels = new ArrayList<>();
        teamPresenter = new TeamPresenter(this,this);
        baseRecyclerAdapter = new BaseRecyclerAdapter<TeamModel>(context,teamModels,R.layout.activity_mine_team_item) {
            @Override
            protected void convert(BaseViewHolder helper, TeamModel item) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.avatar1);
                requestOptions.error(R.mipmap.avatar1);
                Glide.with(TeamSearchActivity.this).load(BaseAPI.base_url+item.getTeam_logo()).apply(requestOptions).into((ImageView)helper.getView(R.id.team_logo));
                helper.setText(R.id.team_name,item.getTeam_name());
                helper.setText(R.id.team_abstract,item.getTeam_abstract());
            }
        };
        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent  = new Intent(TeamSearchActivity.this,TeamDetailActivity.class);
                intent.putExtra("team_id",teamModels.get(position).getTeam_id());
                intent.putExtra("team_type",TeamDetailActivity.TeamTypeNotJoin);
                startActivity(intent);
            }
        });
        baseRecyclerAdapter.openLoadAnimation(false);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

    }

    private void getData(String team_keyword){
        if (AccountTool.isLogin(this)){
            Map<String,String> params = RequestService.getBaseParams(this);
            params.put("user_id", AccountTool.getLoginUser(this).getUser_id());
            params.put("team_keyword", team_keyword);
            teamPresenter.searchAllTeamExceptUserId(params);
        }else{

        }
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void addTeamResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMineTeamResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getTeamByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getAllTeamExceptUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void searchAllTeamExceptUserIdResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            teamModels = resultModel.getData();
            baseRecyclerAdapter.setData(teamModels);
        }
    }

    @Override
    public void getTeamDetailResult(boolean isSuccess, Object object) {

    }

    @Override
    public void joinTeamResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getTeamMenber(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
