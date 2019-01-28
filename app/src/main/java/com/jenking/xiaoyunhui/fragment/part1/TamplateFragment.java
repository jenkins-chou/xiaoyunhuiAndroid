package com.jenking.xiaoyunhui.fragment.part1;

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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MatchDetailActivity;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
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

public class TamplateFragment extends Fragment implements MatchContract {

    private Unbinder unbinder;
    private String matchType;
    private List<MatchModel> datas;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private MatchPresenter matchPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1_template,container,false);
        unbinder = ButterKnife.bind(this,view);
        initView();
        return view;
    }

    public void setMatchType(String matchType){
        this.matchType = matchType;
    }

    public static TamplateFragment init(String matchType){
        Log.e("TamplateFragment init",matchType);
        TamplateFragment tamplateFragment = new TamplateFragment();
        tamplateFragment.setMatchType(matchType);
        return tamplateFragment;
    }

    private void initView(){
        datas = new ArrayList<>();
        matchPresenter = new MatchPresenter(getContext(),this);
        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(getContext(),datas,R.layout.fragment_part1_template_item) {
            @Override
            protected void convert(com.github.library.BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.item_title,item.getMatch_title());
                helper.setText(R.id.item_address,"比赛地点:"+item.getMatch_address());
                helper.setText(R.id.item_time,"比赛时间:"+StringUtil.getStrTime(item.getMatch_time(),"yyyy年MM月dd日 HH时mm分ss秒"));

                ImageView item_image = helper.getView(R.id.item_img);
                Glide.with(getContext()).load(BaseAPI.base_url+item.getMatch_img()).into(item_image);
            }
        };

        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(),MatchDetailActivity.class);
                intent.putExtra("match_id",datas.get(position).getMatch_id());
                startActivity(intent);
            }
        });
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        smartRefreshLayout.setRefreshHeader(new TaurusHeader(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        //获取网络数据
        getData();
    }

    void getData(){
        if (matchPresenter!=null){
            Map<String,String> params = RequestService.getBaseParams(getContext());
            params.put("match_type",matchType);
            matchPresenter.getMatchByType(params);
        }
    }

    @Override
    public void getAllMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByStatusResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByTypeResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (StringUtil.isEquals(resultModel.getStatus(),"200")){
                datas = resultModel.getData();
                baseRecyclerAdapter.setData(datas);
            }
        }
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
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
