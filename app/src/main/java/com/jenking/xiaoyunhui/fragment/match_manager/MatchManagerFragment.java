package com.jenking.xiaoyunhui.fragment.match_manager;

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

import com.bumptech.glide.Glide;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MatchDetailActivity;
import com.jenking.xiaoyunhui.activity.ScoreShowActivity;
import com.jenking.xiaoyunhui.adapter.MatchManagerAdapter;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.StringUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
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

public class MatchManagerFragment extends Fragment implements MatchContract {
    private Unbinder unbinder;

    private String match_status;

    private MatchPresenter matchPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private List<MatchModel> MatchModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_manager,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void setMatch_status(String match_status){
        this.match_status = match_status;
    }

    private void initData(){
        MatchModels = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(getContext(),MatchModels,R.layout.fragment_match_manager_item) {
            @Override
            protected void convert(BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.match_title,item.getMatch_title());
                helper.setText(R.id.match_abstract,item.getMatch_abstract());
                helper.setText(R.id.match_status,getStatusStr(item.getMatch_status()));
                helper.setText(R.id.match_create_time,"创建时间"+StringUtil.getStrTime(item.getMatch_create_time(),"yyyy-MM-dd HH:mm:ss"));
                helper.setText(R.id.match_time,"比赛时间:"+StringUtil.getStrTime(item.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));
                ImageView match_img = helper.getView(R.id.match_img);
                if (getContext()!=null){
                    Glide.with(getContext()).load(BaseAPI.base_url+item.getMatch_img()).into(match_img);
                }
            }
        };
        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (MatchModels.get(position)!=null){
                    String match_id = MatchModels.get(position).getMatch_id();
                    switch (match_status){
                        case Const.Match_type_four:
                            Intent intent = new Intent(getContext(), ScoreShowActivity.class);
                            intent.putExtra("match_id",match_id);
                            startActivity(intent);
                            break;
                            default:
                                Intent intent2 = new Intent(getContext(), MatchDetailActivity.class);
                                intent2.putExtra("match_id",match_id);
                                startActivity(intent2);
                                break;
                    }
                }
            }
        });
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        matchPresenter = new MatchPresenter(getContext(),this);
        getData();

        smartRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    private void getData(){
        if (match_status!=null){
            if (match_status.equals("0")){
                matchPresenter.getAllMatch(RequestService.getBaseParams(getContext()));
            }else{
                Map<String,String> params = RequestService.getBaseParams(getContext());
                params.put("match_status",match_status);
                matchPresenter.getMatchByStatus(params);
            }
        }
    }

    public static MatchManagerFragment init(String match_status){
        MatchManagerFragment matchManagerFragment = new MatchManagerFragment();
        matchManagerFragment.setMatch_status(match_status);
        return matchManagerFragment;
    }

    private String getStatusStr(String code){
        String result = "未知状态";
        if (code!=null){
            switch (code){
                case "1":
                    result = "报名中";
                    break;
                case "2":
                    result = "比赛中";
                    break;
                case "3":
                    result = "比赛完毕";
                    break;
                case "4":
                    result = "已公布成绩";
                    break;
            }
        }
            return result;
    }

    @Override
    public void getAllMatchResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                MatchModels = resultModel.getData();
                baseRecyclerAdapter.setData(MatchModels);
            }
        }
    }

    @Override
    public void getMatchByStatusResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                MatchModels = resultModel.getData();
                baseRecyclerAdapter.setData(MatchModels);
            }
        }
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
