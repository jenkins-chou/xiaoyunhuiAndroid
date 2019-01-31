package com.jenking.xiaoyunhui.fragment.part3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.TeamDetailActivity;
import com.jenking.xiaoyunhui.adapter.MainFragment3Adapter;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.TeamContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.TeamModel;
import com.jenking.xiaoyunhui.presenters.TeamPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.StringUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OtherTeamFragment extends Fragment implements TeamContract {
    private Unbinder unbinder;
    private List<TeamModel> teamModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    private TeamPresenter teamPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.tips_text)
    TextView tips_text;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_other_team,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!AccountTool.isLogin(getContext())){
            teamModels.clear();
            baseRecyclerAdapter.setData(teamModels);
            tips_text.setText("请登录后查看");
            tips_text.setVisibility(View.VISIBLE);
        }else{
            tips_text.setVisibility(View.GONE);
        }
    }

    private void initData(){
        teamModels = new ArrayList<>();
        teamPresenter = new TeamPresenter(getContext(),this);
        baseRecyclerAdapter = new BaseRecyclerAdapter<com.jenking.xiaoyunhui.models.base.TeamModel>(getContext(),teamModels,R.layout.activity_mine_team_item) {
            @Override
            protected void convert(BaseViewHolder helper, com.jenking.xiaoyunhui.models.base.TeamModel item) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.avatar1);
                requestOptions.error(R.mipmap.avatar1);
                Glide.with(getActivity()).load(BaseAPI.base_url+item.getTeam_logo()).apply(requestOptions).into((ImageView)helper.getView(R.id.team_logo));
                helper.setText(R.id.team_name,item.getTeam_name());
                helper.setText(R.id.team_abstract,item.getTeam_abstract());
            }
        };
        baseRecyclerAdapter.openLoadAnimation(false);
        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent  = new Intent(getContext(),TeamDetailActivity.class);
                intent.putExtra("team_id",teamModels.get(position).getTeam_id());
                intent.putExtra("team_type",TeamDetailActivity.TeamTypeJoined);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        smartRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        getData();
    }

    private void getData(){
        if (AccountTool.isLogin(getContext())){
            Map<String,String> params = RequestService.getBaseParams(getContext());
            params.put("user_id", AccountTool.getLoginUser(getContext()).getUser_id());
            teamPresenter.getTeamListByUserId(params);
        }else{
            smartRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void addTeamResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMineTeamResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getTeamByUserIdResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                teamModels = resultModel.getData();
                baseRecyclerAdapter.setData(teamModels);
            }
        }
        checkDatas();
    }

    @Override
    public void getAllTeamExceptUserIdResult(boolean isSuccess, Object object) {

    }

    private void checkDatas(){
        if (teamModels==null||teamModels.size()<=0){
            tips_text.setVisibility(View.VISIBLE);
            tips_text.setText("空空如也");
        }else{
            tips_text.setVisibility(View.GONE);
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
