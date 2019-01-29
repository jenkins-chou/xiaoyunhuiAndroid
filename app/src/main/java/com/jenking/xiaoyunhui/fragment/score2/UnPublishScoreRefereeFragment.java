package com.jenking.xiaoyunhui.fragment.score2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.ScoreOperateActivity;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.StringUtil;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UnPublishScoreRefereeFragment extends Fragment implements MatchContract{
    private Unbinder unbinder;
    private Context context;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private List<MatchModel> datas;

    private MatchPresenter matchPresenter;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_show)
    TextView empty_show;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unpublish_score2,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        context = getActivity();
        datas = new ArrayList<>();

        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(context,datas,R.layout.fragment_publish_score2_item) {
            @Override
            protected void convert(BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.match_title,item.getMatch_title());
                helper.setText(R.id.match_status,getMatchStatusName(item.getMatch_status()));
                helper.setText(R.id.match_number,item.getMatch_athletes_num());
                helper.setText(R.id.match_time, StringUtil.getStrTime(item.getMatch_time(),"yyyy-MM-dd HH:ss"));

                TextView item_button = helper.getView(R.id.item_button);
                if (item.getMatch_status().equals(Const.Match_type_one)){
                    item_button.setText("报名中");
                    item_button.getBackground().mutate().setAlpha(100);
                }else if (item.getMatch_status().equals(Const.Match_type_two)){
                    item_button.setText("比赛中");
                    item_button.getBackground().mutate().setAlpha(100);
                }else if (item.getMatch_status().equals(Const.Match_type_three)){
                    item_button.setText("已完成，请公布成绩");
                    item_button.getBackground().mutate().setAlpha(255);
                }
            }
        };

        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (datas.get(position)!=null){
                    String status = datas.get(position).getMatch_status();
                    switch (status){
                        case Const.Match_type_one: case Const.Match_type_two:
                            CommonTipsDialog.showTip(context,"温馨提示",datas.get(position).getMatch_title()+"正在报名中/比赛中，请在比赛完成后公布成绩",false);
                            break;
                        case Const.Match_type_three:
                            Intent intent = new Intent(context,ScoreOperateActivity.class);
                            startActivity(intent);
                            break;
                    }
                }

            }
        });
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        smartRefreshLayout.setRefreshHeader(new TaurusHeader(context));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });

        matchPresenter = new MatchPresenter(context,this);
        getData();
    }

    void getData(){
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("user_id", AccountTool.getLoginUser(context).getUser_id());
        matchPresenter.getMatchByRefereeId(params);
    }

    String getMatchStatusName(String key){
        String result = "";
        switch (key){
            case Const.Match_type_one:
                result = "报名中";
                break;
            case Const.Match_type_two:
                result = "比赛中";
                break;
            case Const.Match_type_three:
                result = "已完成";
                break;
        }
        return result;
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
    public void getUserMatchByMatchIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByRefereeIdResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getStatus()!=null){
                filter(resultModel.getData());

            }
        }
        refershView();
    }

    private void filter(List<MatchModel> source){
        if (source==null){
            return;
        }
        datas.clear();
        for (int i = 0;i<source.size();i++){
            String status = source.get(i).getMatch_status();
            if (status!=null
                    &&(StringUtil.isEquals(status, Const.Match_type_one)
                    ||StringUtil.isEquals(status, Const.Match_type_two)
                    ||StringUtil.isEquals(status, Const.Match_type_three))){
                datas.add(source.get(i));
            }
        }
        baseRecyclerAdapter.setData(datas);
    }

    private void refershView(){
        if (datas==null||datas.size()<=0){
            empty_show.setVisibility(View.VISIBLE);
        }else{
            empty_show.setVisibility(View.GONE);
        }
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
