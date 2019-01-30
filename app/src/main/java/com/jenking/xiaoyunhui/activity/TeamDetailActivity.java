package com.jenking.xiaoyunhui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.TeamContract;
import com.jenking.xiaoyunhui.contacts.UserTeamContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.TeamDetailModel;
import com.jenking.xiaoyunhui.models.base.TeamModel;
import com.jenking.xiaoyunhui.models.base.UserModel;
import com.jenking.xiaoyunhui.presenters.TeamPresenter;
import com.jenking.xiaoyunhui.presenters.UserTeamPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TeamDetailActivity extends BaseActivity implements TeamContract,UserTeamContract {

    public final static String TeamTypeLeader = "TeamTypeLeader";
    public final static String TeamTypeJoined = "TeamTypeJoined";
    public final static String TeamTypeNotJoin = "TeamTypeNotJoin";

    private Unbinder unbinder;
    private Context context;

    private TeamPresenter teamPresenter;
    private UserTeamPresenter userTeamPresenter;

    private TeamDetailModel teamModel;

    private List<UserModel> members;//团队成员

    @BindView(R.id.empty_show)
    TextView empty_show;

    @BindView(R.id.team_logo)
    ImageView team_logo;

    @BindView(R.id.team_name)
    TextView team_name;

    @BindView(R.id.team_detail)
    TextView team_detail;

    @BindView(R.id.team_leader_avatar)
    ImageView team_leader_avatar;

    @BindView(R.id.team_leader_name)
    TextView team_leader_name;

    @BindView(R.id.loading)
    CommonLoading loading;

    @BindView(R.id.apply_join)
    TextView apply_join;
    @BindView(R.id.modify_info)
    TextView modify_info;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        members = new ArrayList<>();
        teamPresenter = new TeamPresenter(context,this);
        userTeamPresenter = new UserTeamPresenter(context,this);
        Intent intent = getIntent();
        if (intent!=null){
            String team_id = intent.getStringExtra("team_id");
            Map<String,String> params = RequestService.getBaseParams(context);
            params.put("team_id",team_id);
            teamPresenter.getTeamDetail(params);
            userTeamPresenter.getTeamMember(params);
            setLoadingEnable(true);

            String team_type = intent.getStringExtra("team_type");
            if (team_type!=null){
                switch (team_type){
                    case TeamTypeLeader:
                        apply_join.setVisibility(View.GONE);
                        modify_info.setVisibility(View.VISIBLE);
                        break;
                    case TeamTypeJoined:
                        apply_join.setVisibility(View.GONE);
                        modify_info.setVisibility(View.GONE);
                        break;
                    case TeamTypeNotJoin:
                        apply_join.setVisibility(View.VISIBLE);
                        modify_info.setVisibility(View.GONE);
                        break;
                }
            }
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

    }

    @Override
    public void getOtherTeamResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getTeamDetailResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null&&resultModel.getData().size()>=1){
                teamModel = (TeamDetailModel) resultModel.getData().get(0);
            }
        }
        refreshModel();
        setLoadingEnable(false);
    }

    private void refreshModel(){
        if (teamModel!=null){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.avatar2);
            requestOptions.error(R.mipmap.avatar2);
            requestOptions.circleCrop();
            Glide.with(this).load(BaseAPI.base_url+teamModel.getTeam_logo()).apply(requestOptions).into(team_logo);
            Glide.with(this).load(BaseAPI.base_url+teamModel.getUser_avatar()).apply(requestOptions).into(team_leader_avatar);

            team_name.setText(teamModel.getTeam_name());
            team_detail.setText(teamModel.getTeam_detail());

            team_leader_name.setText(teamModel.getUser_name());
        }
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
    void setLoadingEnable(boolean enable){
        if (loading!=null){
            loading.setVisibility(enable?View.VISIBLE:View.GONE);
        }
    }

    @Override
    public void getTeamMemberResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            members = resultModel.getData();
        }
        refreshTeamMembers();
    }

    private void refreshTeamMembers(){
        if (members==null||members.size()<=0){
            empty_show.setVisibility(View.VISIBLE);
        }else{
            empty_show.setVisibility(View.GONE);
        }
    }
}
