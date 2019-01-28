package com.jenking.xiaoyunhui.fragment.referee_manager;

import android.app.DownloadManager;
import android.content.Context;
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
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.RefereeContract;
import com.jenking.xiaoyunhui.models.base.RefereeModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.RefereePresenter;
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

public class RefereeListFragment extends Fragment implements RefereeContract {

    private Unbinder unbinder;
    private Context context;
    private List<RefereeModel> refereeModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private RefereePresenter refereePresenter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_referee_manager_referee_list,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        context = getContext();
        refereeModels = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<RefereeModel>(context,refereeModels,R.layout.fragment_referee_manager_referee_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, RefereeModel item) {
                helper.setText(R.id.item_name,item.getUser_name());
                helper.setText(R.id.item_create_time, StringUtil.getStrTime(item.getUser_create_time(),"yyyy-MM-dd HH:mm:ss"));
                ImageView item_image = helper.getView(R.id.item_image);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(R.mipmap.avatar1);
                Glide.with(context).load(BaseAPI.base_url+item.getUser_avatar()).apply(requestOptions).into(item_image);
            }
        };
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        refereePresenter = new RefereePresenter(context,this);
        getData();
        initSmartRefreshLayout();
    }

    void getData(){
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("referee_status","2");
        refereePresenter.getAllRefereeByStatus(params);
    }

    void initSmartRefreshLayout(){
        smartRefreshLayout.setRefreshHeader(new TaurusHeader(context));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    @Override
    public void getAllRefereeResult(boolean isSuccess, Object object) {
        if (smartRefreshLayout!=null){
            smartRefreshLayout.finishRefresh();
        }
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            refereeModels = resultModel.getData();
            if (refereeModels!=null&&refereeModels.size()>0){
                baseRecyclerAdapter.setData(refereeModels);
            }
        }
    }

    @Override
    public void addRefereeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void updateRefereeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
