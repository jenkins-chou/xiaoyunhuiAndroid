package com.jenking.xiaoyunhui.fragment.referee_manager;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.RefereeContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.RefereeModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.RefereePresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
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

public class RefereeApplyListFragment extends Fragment implements RefereeContract {

    private Unbinder unbinder;
    private Context context;
    private List<RefereeModel> refereeModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private RefereePresenter refereePresenter;

    private int passPosition = 0;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_referee_manager_apply_list,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData(){
        context = getContext();
        refereeModels = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<RefereeModel>(context,refereeModels,R.layout.fragment_referee_manager_apply_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, RefereeModel item) {
                helper.setText(R.id.item_name,item.getUser_name());
                helper.setText(R.id.item_create_time, StringUtil.getStrTime(item.getUser_create_time(),"yyyy-MM-dd HH:mm:ss"));
                ImageView item_image = helper.getView(R.id.item_image);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(R.mipmap.avatar1);
                Glide.with(context).load(BaseAPI.base_url+item.getUser_avatar()).apply(requestOptions).into(item_image);

                TextView item_pass = helper.getView(R.id.item_pass);
                item_pass.setTag(item.getReferee_id());
                item_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPassConfirmDialog((String) view.getTag());
                    }
                });

                TextView item_delete = helper.getView(R.id.item_delete);
                item_delete.setTag(item.getReferee_id());
                item_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showUnPassConfirmDialog((String) view.getTag());
                    }
                });
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
        params.put("referee_status","1");
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

    void showPassConfirmDialog(final String referee_id){
        CommonTipsDialog.create(context,"温馨提示","确认要通过该申请吗？",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm() {
                        passRefereeApply(referee_id);
                    }
                }).show();
    }

    void showUnPassConfirmDialog(final String referee_id){
        CommonTipsDialog.create(context,"温馨提示","确认要忽略该申请吗？",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm() {
                        unpassRefereeApply(referee_id);
                    }
                }).show();;
    }

    void passRefereeApply(String referee_id){
        RefereeModel refereeModel = getRefereeItem(referee_id);
        if (refereeModel!=null){
            Map<String,String> params = RequestService.getBaseParams(context);
            params.put("user_id",refereeModel.getUser_id());
            params.put("referee_id",refereeModel.getReferee_id());
            params.put("referee_status","2");
            params.put("referee_manager", AccountTool.getLoginUser(context).getUser_id());
            refereePresenter.updateReferee(params);
        }
    }

    void unpassRefereeApply(String referee_id){
        RefereeModel refereeModel = getRefereeItem(referee_id);
        if (refereeModel!=null){
            Map<String,String> params = RequestService.getBaseParams(context);
            params.put("user_id",refereeModel.getUser_id());
            params.put("referee_id",refereeModel.getReferee_id());
            params.put("referee_status","1");
            params.put("referee_del","delete");
            params.put("referee_manager", AccountTool.getLoginUser(context).getUser_id());
            refereePresenter.updateReferee(params);
        }
    }

    RefereeModel getRefereeItem(String referee_id){
        if (referee_id==null)return null;
        RefereeModel refereeModel = null;
        if (refereeModels!=null){
            for (int i = 0;i<refereeModels.size();i++){
                if (referee_id.equals(refereeModels.get(i).referee_id)){
                    passPosition = i;
                    refereeModel = refereeModels.get(i);
                }
            }
        }
        return refereeModel;
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
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getStatus()!=null){
                switch (resultModel.getStatus()){
                    case "200":
                        refereeModels.remove(passPosition);
                        baseRecyclerAdapter.setData(refereeModels);
                        Toast.makeText(context, "通过成功,请前往裁判列表刷新", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
