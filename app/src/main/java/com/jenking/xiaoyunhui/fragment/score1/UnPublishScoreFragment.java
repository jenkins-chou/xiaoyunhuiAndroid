package com.jenking.xiaoyunhui.fragment.score1;

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
import android.widget.TextView;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.ScoreShowActivity;
import com.jenking.xiaoyunhui.adapter.score1.PublishAdapter;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.ScoreContract;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.ScoreModel;
import com.jenking.xiaoyunhui.presenters.ScorePresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
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

public class UnPublishScoreFragment extends Fragment implements ScoreContract {
    private Unbinder unbinder;
    private Context context;
    private BaseRecyclerAdapter publishAdapter;
    private List<MatchModel> datas;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_show)
    TextView empty_show;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private ScorePresenter scorePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unpublish_score1,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        context = getActivity();
        datas = new ArrayList<>();
        publishAdapter = new BaseRecyclerAdapter<MatchModel>(context,datas,R.layout.fragment_publish_score1_item2) {
            @Override
            protected void convert(BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.match_title,item.getMatch_title());
                helper.setText(R.id.match_time, StringUtil.getStrTime(item.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));
                helper.setText(R.id.match_number,item.getMatch_athletes_num());
                helper.setText(R.id.match_address,item.getMatch_address());
            }
        };
        publishAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (datas.get(position)!=null){
                    Intent intent = new Intent(context, ScoreShowActivity.class);datas.get(position);
                    intent.putExtra("match_id",datas.get(position).getMatch_id());
                    startActivity(intent);
                }
            }
        });
        publishAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(publishAdapter);

        scorePresenter = new ScorePresenter(context,this);

        smartRefreshLayout.setRefreshHeader(new MaterialHeader(context));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        getData();
    }

    void getData(){
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("user_id", AccountTool.getLoginUser(context).getUser_id());
        if (scorePresenter!=null){
            scorePresenter.getScoreListByUserId(params);
        }
    }

    @Override
    public void getScoreListByUserIdResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getStatus()!=null){
                Log.e("getScoreListByUserId",resultModel.toString());
                switch (resultModel.getStatus()){
                    case "200":
                        datas.clear();
                        if (resultModel.getData()!=null){
                            List<MatchModel> items = (List<MatchModel>)resultModel.getData();
                            for (int i = 0;i<items.size();i++){
                                if (items.get(i)!=null
                                        &&items.get(i).getMatch_status()!=null
                                        &&!items.get(i).getMatch_status().equals(Const.Match_type_four)){
                                    datas.add(items.get(i));
                                }
                            }
                        }
                        publishAdapter.setData(datas);
                        break;
                }
            }
        }
        refershView();
    }

    @Override
    public void getScoreListByMatchIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getScorePublishListByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getAllScoreList(boolean isSuccess, Object object) {

    }

    @Override
    public void addScoresResult(boolean isSuccess, Object object) {

    }

    @Override
    public void updateScoreResult(boolean isSuccess, Object object) {

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
