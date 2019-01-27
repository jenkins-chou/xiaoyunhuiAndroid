package com.jenking.xiaoyunhui.fragment.part1;

import android.content.Context;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.LoginActivity;
import com.jenking.xiaoyunhui.activity.MatchDetailActivity;
import com.jenking.xiaoyunhui.activity.MineMatchActivity;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.contacts.UserMatchContract;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.presenters.UserMatchPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.StringUtil;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IndexFragment extends Fragment implements MatchContract,UserMatchContract {
    private Unbinder unbinder;
    private List<MatchModel> allMatchModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private View headerView;
    private Context context;

    private LinearLayout mineMatch;//我的比赛项目

    private MatchPresenter matchPresenter;
    private UserMatchPresenter userMatchPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1_index,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        initView();
        return view;
    }

    private void initView(){
        smartRefreshLayout.setRefreshHeader(new TaurusHeader(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    private void getData(){
        if (matchPresenter!=null){
            matchPresenter.getAllMatch(RequestService.getBaseParams(context));
        }
        if (userMatchPresenter!=null){
            if (AccountTool.isLogin(context)){
                Map<String,String> params = RequestService.getBaseParams(context);
                params.put("user_id",AccountTool.getLoginUser(context).getUser_id());
                userMatchPresenter.getUserMatchByUserId(params);
            }
        }
    }

    private void initData(){
        context = this.getContext();
        matchPresenter = new MatchPresenter(context,this);
        userMatchPresenter = new UserMatchPresenter(context,this);

        allMatchModels = new ArrayList<>();

        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(context, allMatchModels,R.layout.fragment_part1_index_item) {
            @Override
            protected void convert(BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.item_match_name,item.getMatch_title());
                helper.setText(R.id.item_match_time,"比赛时间："+StringUtil.getStrTime(item.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));
                helper.setText(R.id.item_match_address,"地址："+item.getMatch_address());

                ImageView item_img = helper.getView(R.id.item_img);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(R.mipmap.avatar2);
                Glide.with(context).load(BaseAPI.base_url+item.getMatch_img()).apply(requestOptions).into(item_img);
            }
        };

        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context,MatchDetailActivity.class);
                intent.putExtra("match_id", allMatchModels.get(position).getMatch_id());
                startActivity(intent);
            }
        });

        baseRecyclerAdapter.openLoadAnimation(false);

        //添加头部
        headerView = getLayoutInflater().inflate(R.layout.fragment_part1_index_header,null,false);
        baseRecyclerAdapter.addHeaderView(headerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        mineMatch = headerView.findViewById(R.id.mine_match);
        mineMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MineMatchActivity.class);
                startActivity(intent);
            }
        });
        getData();

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshHeader(headerView);
    }

    private void refreshHeader(View headerView){
        LinearLayout usertype_one;
        LinearLayout usertype_two;
        if (headerView!=null){
            usertype_one = headerView.findViewById(R.id.usertype_one);
            usertype_two = headerView.findViewById(R.id.usertype_two);
            if (AccountTool.isLogin(context)){
                switch (AccountTool.getLoginUser(context).getUser_type()){
                    case "1":
                        usertype_one.setVisibility(View.VISIBLE);
                        usertype_two.setVisibility(View.GONE);
                        break;
                    case "2":
                        usertype_one.setVisibility(View.GONE);
                        usertype_two.setVisibility(View.VISIBLE);
                        break;
                    case "3":
                        usertype_one.setVisibility(View.GONE);
                        usertype_two.setVisibility(View.GONE);
                        break;
                    default:break;//视作未登录
                }
            }else{
                usertype_one.setVisibility(View.GONE);
                usertype_two.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getAllMatchResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (StringUtil.isEquals(resultModel.getStatus(),"200")){
                allMatchModels = resultModel.getData();
                baseRecyclerAdapter.setData(allMatchModels);
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
    public void getUserMatchByMatchIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }

    @Override
    public void getUserMatchByUserIdResult(boolean isSuccess, Object object) {
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                Log.e("getUserMatchByUserId",""+resultModel.toString());
                if (headerView!=null){
                    TextView user_match_account = headerView.findViewById(R.id.user_match_account);
                    if (user_match_account!=null){
                        user_match_account.setText(resultModel.getData().size()+"");
                    }
                }
            }
        }
    }
}
