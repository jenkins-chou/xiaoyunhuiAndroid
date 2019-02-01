package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.TeamContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.TeamPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamNormalCountActivity extends BaseActivity implements TeamContract {

    private TeamPresenter teamPresenter;

    @BindView(R.id.lead_team_num)
    TextView lead_team_num;
    @BindView(R.id.join_team_num)
    TextView join_team_num;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.team_detail)
    void team_detail(){
        Intent intent = new Intent(this,TeamNormalManagerActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_normal_count);
    }

    @Override
    public void initData() {
        super.initData();
        teamPresenter = new TeamPresenter(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AccountTool.isLogin(this)){
            Map<String,String> params = RequestService.getBaseParams(this);
            params.put("user_id", AccountTool.getLoginUser(this).getUser_id());
            params.put("team_create_user", AccountTool.getLoginUser(this).getUser_id());
            teamPresenter.getTeamListByCreater(params);
            teamPresenter.getTeamListByUserId(params);
        }
    }

    @Override
    public void addTeamResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMineTeamResult(boolean isSuccess, Object object) {
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                List datas = resultModel.getData();
                if (datas!=null){
                    lead_team_num.setText(datas.size()+"");
                }
            }
        }
    }

    @Override
    public void getTeamByUserIdResult(boolean isSuccess, Object object) {
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                List datas = resultModel.getData();
                if (datas!=null){
                    join_team_num.setText(datas.size()+"");
                }
            }
        }
    }

    @Override
    public void getAllTeamExceptUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void searchAllTeamExceptUserIdResult(boolean isSuccess, Object object) {

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
