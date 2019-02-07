package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.UserMatchContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.UserMatchModel;
import com.jenking.xiaoyunhui.presenters.UserMatchPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 比赛报名名单
 */
public class MatchNameListActivity extends BaseActivity implements UserMatchContract {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private List<UserMatchModel> datas;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private UserMatchPresenter userMatchPresenter;

    private String match_id;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_name_list);
    }

    @Override
    public void initData() {
        super.initData();
        datas = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<UserMatchModel>(context,datas,R.layout.activity_match_name_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, UserMatchModel item) {
                helper.setText(R.id.realname,item.getUser_realname());
                helper.setText(R.id.school_name,item.getSchool_name());
                helper.setText(R.id.class_name,item.getClass_name());
                helper.setText(R.id.group,item.getUser_group());
                helper.setText(R.id.order,item.getUser_order());
            }
        };

        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setAdapter(baseRecyclerAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));

        userMatchPresenter = new UserMatchPresenter(this,this);

        Map<String,String> params = RequestService.getBaseParams(this);
        Intent intent = getIntent();
        if (intent!=null){
            match_id = intent.getStringExtra("match_id");
            params.put("match_id",match_id);
        }
        userMatchPresenter.getUserMatchDetailByMatchId(params);
    }

    @Override
    public void getUserMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getUserMatchDetailByMatchIdResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null){
                datas = resultModel.getData();
                if (datas!=null){
                    baseRecyclerAdapter.setData(datas);
                }
                Log.e("UserMatchDetail",""+resultModel.toString());
            }
        }
    }

    @Override
    public void getRefereeMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
