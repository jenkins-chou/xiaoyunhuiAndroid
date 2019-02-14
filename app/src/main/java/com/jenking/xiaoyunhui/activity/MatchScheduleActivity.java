package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//赛程表
public class MatchScheduleActivity extends BaseActivity implements MatchContract {

    private MatchPresenter matchPresenter;
    private List<MatchModel> modelList;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_schedule);
    }

    @Override
    public void initData() {
        super.initData();
        matchPresenter = new MatchPresenter(this,this);
        matchPresenter.getAllMatch(RequestService.getBaseParams(this));

        modelList = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(this,modelList,R.layout.activity_match_schedule_item) {
            @Override
            protected void convert(BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.match_name,item.getMatch_title());
                helper.setText(R.id.match_tag,getMatchStatusTag(item.getMatch_status()));
                helper.setText(R.id.match_time,""+StringUtil.getStrTime(item.getMatch_time(),"yyyy-MM-dd HH:mm"));
                helper.setText(R.id.match_address,item.getMatch_address());
            }
        };

        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context,MatchDetailActivity.class);
                intent.putExtra("match_id",modelList.get(position).getMatch_id());
                intent.putExtra("justShowDetail","true");
                startActivity(intent);
            }
        });

        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);
    }

    private String getMatchStatusTag(String match_status){
        String result = "";
        if (match_status==null)return result;
        switch (match_status){
            case "1":
                result = "报名中";
                break;
            case "2":
                result = "比赛中";
                break;
            case "3":
                result = "比赛完成";
                break;
            case "4":
                result = "已公布成绩";
                break;
        }
        return result;
    }

    @Override
    public void getAllMatchResult(boolean isSuccess, Object object) {
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (StringUtil.isEquals(resultModel.getStatus(),"200")){
                modelList = resultModel.getData();
                if (modelList!=null){
                    baseRecyclerAdapter.setData(modelList);
                }
            }
        }
    }

    @Override
    public void getMatchByStatusResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByTypeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void addMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void addUserMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void deleteUserMatchResult(boolean isSuccess, Object object) {

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
    public void excuteResult(boolean isSuccess, Object object) {

    }

    @Override
    public void deleteMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
