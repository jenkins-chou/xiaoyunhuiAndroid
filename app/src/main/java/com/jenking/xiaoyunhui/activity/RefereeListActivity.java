package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.RefereeContract;
import com.jenking.xiaoyunhui.models.base.RefereeModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.RefereePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RefereeListActivity extends BaseActivity implements RefereeContract {
    public final static int SelectRefereeResultId = 3090;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private List<RefereeModel> datas;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private RefereePresenter refereePresenter;
    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_list);
    }

    @Override
    public void initData() {
        super.initData();
        datas = new ArrayList<>();
        refereePresenter = new RefereePresenter(context,this);
        baseRecyclerAdapter = new BaseRecyclerAdapter<RefereeModel>(context,datas,R.layout.activity_referee_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, RefereeModel item) {
                helper.setText(R.id.referee_name,item.getUser_name());
            }
        };
        baseRecyclerAdapter.openLoadAnimation(false);
        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select(position);
            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);
    }

    private void select(int i){
        if (datas!=null&&datas.get(i)!=null){
            RefereeModel refereeModel = datas.get(i);
            Intent intent = getIntent();
            intent.putExtra("select_referee_id",refereeModel.getUser_id());
            intent.putExtra("select_referee_name",refereeModel.getUser_name());
            Log.e("select_referee_id",refereeModel.getUser_id());
            Log.e("select_referee_name",refereeModel.getUser_name());
            setResult(SelectRefereeResultId,intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refereePresenter!=null){
            Map<String,String> params = RequestService.getBaseParams(context);
            params.put("referee_status","2");
            refereePresenter.getAllRefereeByStatus(params);
        }
    }

    @Override
    public void getAllRefereeResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            datas = resultModel.getData();
            baseRecyclerAdapter.setData(datas);
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
