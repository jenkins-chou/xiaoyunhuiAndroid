package com.jenking.xiaoyunhui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.TeamContract;
import com.jenking.xiaoyunhui.contacts.UserTeamContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.TeamDetailModel;
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

    private List<UserModel> joinedMembers;//团队成员
    private List<UserModel> applyMembers;//申请中团队成员


    private BaseRecyclerAdapter joinedAdapter;
    private BaseRecyclerAdapter applyAdapter;

    private String teamId;

    @BindView(R.id.empty_show)
    TextView empty_show;

    @BindView(R.id.empty_show2)
    TextView empty_show2;

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

    @BindView(R.id.member_recyclerView)
    RecyclerView member_recyclerView;
    @BindView(R.id.apply_recyclerView)
    RecyclerView apply_recyclerView;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.apply_join)
    void setApply_join(){
        if (AccountTool.isLogin(this)){
            if (userTeamPresenter!=null){
                if (teamModel!=null){
                    Map<String,String> params = RequestService.getBaseParams(this);
                    params.put("team_id",teamModel.getTeam_id());
                    params.put("user_id",AccountTool.getLoginUser(this).getUser_id());
                    params.put("user_team_status","1");
                    userTeamPresenter.applyJoinTeam(params);
                    setLoadingEnable(true);
                }
            }

        }else {
            CommonTipsDialog.showTip(this,"温馨提示","请登录后重试",false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        unbinder = ButterKnife.bind(this);
    }

    private void getData(){
        Intent intent = getIntent();
        if (intent!=null){
            teamId = intent.getStringExtra("team_id");
            Map<String,String> params = RequestService.getBaseParams(context);//获取已经是团队成员的列表
            params.put("team_id",teamId);
            params.put("user_team_status","2");
            teamPresenter.getTeamDetail(params);
            userTeamPresenter.getTeamJoinedMember(params);

            Map<String,String> params2 = RequestService.getBaseParams(context);//获取申请中的成员列表
            params2.put("team_id",teamId);
            params2.put("user_team_status","1");
            userTeamPresenter.getTeamApplyMember(params2);

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
    public void initData() {
        super.initData();
        joinedMembers = new ArrayList<>();
        applyMembers = new ArrayList<>();
        teamPresenter = new TeamPresenter(context,this);
        userTeamPresenter = new UserTeamPresenter(context,this);
        getData();

        joinedAdapter = new BaseRecyclerAdapter<UserModel>(this, joinedMembers,R.layout.activity_team_detail_member_item) {
            @Override
            protected void convert(BaseViewHolder helper, UserModel item) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.avatar1);
                requestOptions.error(R.mipmap.avatar1);
                Glide.with(TeamDetailActivity.this).load(BaseAPI.base_url+item.getUser_avatar()).apply(requestOptions).into((ImageView)helper.getView(R.id.user_avatar));
                helper.setText(R.id.user_name,item.getUser_name());
                helper.setText(R.id.user_slogan,item.getUser_slogan());

                TextView showMemberTv = helper.getView(R.id.show_member);
                showMemberTv.setTag(item.getUser_id());
                showMemberTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showMemberInfo(view.getTag().toString());
                    }
                });
            }
        };
        joinedAdapter.openLoadAnimation(false);
        member_recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        member_recyclerView.setAdapter(joinedAdapter);



        applyAdapter = new BaseRecyclerAdapter<UserModel>(this, applyMembers,R.layout.activity_team_detail_apply_item) {
            @Override
            protected void convert(BaseViewHolder helper, UserModel item) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.avatar1);
                requestOptions.error(R.mipmap.avatar1);
                Glide.with(TeamDetailActivity.this).load(BaseAPI.base_url+item.getUser_avatar()).apply(requestOptions).into((ImageView)helper.getView(R.id.user_avatar));
                helper.setText(R.id.user_name,item.getUser_name());
                helper.setText(R.id.user_slogan,item.getUser_slogan());

                TextView passTv = helper.getView(R.id.pass);
                passTv.setTag(item.getUser_id());
                passTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        passApply(view.getTag().toString());
                    }
                });

                TextView unpassTv = helper.getView(R.id.unpass);
                unpassTv.setTag(item.getUser_id());
                unpassTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ignoreApply(view.getTag().toString());
                    }
                });

                TextView showMemberTv = helper.getView(R.id.show_member);
                showMemberTv.setTag(item.getUser_id());
                showMemberTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showMemberInfo(view.getTag().toString());
                    }
                });
            }
        };
        applyAdapter.openLoadAnimation(false);
        apply_recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        apply_recyclerView.setAdapter(applyAdapter);
    }

    private void showMemberInfo(String user_id){
        Intent intent = new Intent(this,TeamMemberDetailActivity.class);
        intent.putExtra("user_id",user_id);
        startActivity(intent);
    }

    private void passApply(final String user_id){
        Log.e("passApply",user_id);
        CommonTipsDialog.create(this,"温馨提示","确认要通过该申请吗",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm() {
                        Map<String,String> params = RequestService.getBaseParams(TeamDetailActivity.this);
                        params.put("user_id",user_id);
                        params.put("team_id",teamId);
                        params.put("user_team_status","2");
                        Log.e("params",params.toString());
                        updateUserTeam(params);
                    }
                }).show();
    }

    private void ignoreApply(final String user_id){
        Log.e("ignoreApply",user_id);
        CommonTipsDialog.create(this,"温馨提示","确认要忽略该申请吗",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm() {
                        Map<String,String> params = RequestService.getBaseParams(TeamDetailActivity.this);
                        params.put("user_id",user_id);
                        params.put("team_id",teamId);
                        params.put("user_team_del","delete");
                        Log.e("params",params.toString());
                        updateUserTeam(params);
                    }
                }).show();
    }

    private void updateUserTeam(Map<String,String> params){
        if (params!=null&&userTeamPresenter!=null){
            userTeamPresenter.operateUserTeamApply(params);
            setLoadingEnable(true);
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
    public void getAllTeamExceptUserIdResult(boolean isSuccess, Object object) {

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
    public void getTeamJoinedMemberResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            joinedMembers = resultModel.getData();
        }
        refreshTeamJoinedMembers();
    }

    private void refreshTeamJoinedMembers(){
        if (joinedMembers ==null|| joinedMembers.size()<=0){
            empty_show.setVisibility(View.VISIBLE);
        }else{
            Log.e("joinedMembers",joinedMembers.toString());
            joinedAdapter.setData(joinedMembers);
            empty_show.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTeamApplyMemberResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            applyMembers = resultModel.getData();
        }
        refreshTeamApplyMembers();
    }

    private void refreshTeamApplyMembers(){
        if (applyMembers ==null|| applyMembers.size()<=0){
            empty_show2.setVisibility(View.VISIBLE);
        }else{
            Log.e("applyMembers",applyMembers.toString());
            applyAdapter.setData(applyMembers);
            empty_show2.setVisibility(View.GONE);
        }
    }

    //加入团队申请回调
    @Override
    public void applyJoinTeamResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getStatus()!=null){
                switch (resultModel.getStatus()){
                    case "200":
                        Toast.makeText(this, "申请成功", Toast.LENGTH_SHORT).show();
                        break;
                    case "201":
                        CommonTipsDialog.showTip(this,"温馨提示","不能重复申请",false);
                        break;
                }
            }
        }
    }

    @Override
    public void updateUserTeamResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        }
        setLoadingEnable(false);
    }
}
