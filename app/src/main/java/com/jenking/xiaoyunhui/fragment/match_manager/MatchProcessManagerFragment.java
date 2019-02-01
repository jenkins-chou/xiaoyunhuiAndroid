package com.jenking.xiaoyunhui.fragment.match_manager;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.ScoreShowActivity;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.dialog.CommonBottomListDialog;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MatchProcessManagerFragment extends Fragment implements MatchContract {
    private Unbinder unbinder;
    private String match_status;
    private MatchPresenter matchPresenter;
    private Map<String,Boolean> selectList;//match选择列表

    private List<String> statusList;
    private String sqlStr;
    private String selectMatchStatus;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private List<MatchModel> MatchModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    @OnClick(R.id.footer)
    void footer(){
        final List<String> matchIds = new ArrayList<>();
        if (MatchModels!=null){
            for (int i = 0;i<MatchModels.size();i++){
                Log.e("match_id",MatchModels.get(i).getMatch_id());
                Boolean check = selectList.get(MatchModels.get(i).getMatch_id());
                Log.e("check",check+"");
                if (check!=null&&check){
                    matchIds.add(MatchModels.get(i).getMatch_id());
                }
            }
        }

        if (matchIds.size()<=0){
            CommonTipsDialog.showTip(getContext(),"温馨提示","你还没有选择任何比赛呢",false);
        }else{

            CommonBottomListDialog commonBottomListDialog = new CommonBottomListDialog(getContext(),"选择状态",statusList,"",true) {
                @Override
                protected void setOnItemClickListener(String value) {
                    selectMatchStatus = getMatchStatusCode(value);
                    if (selectMatchStatus.equals(match_status)){
                        Toast.makeText(getContext(), "当前已经是"+value+"状态", Toast.LENGTH_SHORT).show();
                    }else{
                        String sql = "update matchs set match_status = '"+selectMatchStatus+"' where match_id in (";
                        for(int i =0;i<matchIds.size();i++){
                            if (i==matchIds.size()-1){
                                sql += matchIds.get(i)+")";
                            }else{
                                sql += matchIds.get(i)+",";
                            }
                        }
                        sqlStr = sql;
                        Log.e("match_sql",sql);

                        confirmAndSubmit();
                    }

                }
            };
            commonBottomListDialog.show();
        }
    }

    private void confirmAndSubmit(){
        CommonTipsDialog.create(getContext(),"温馨提示","确认提交吗",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm() {
                        Map<String,String> params = RequestService.getBaseParams(getContext());
                        params.put("sql",sqlStr);
                        matchPresenter.excute(params);
                    }
                }).show();
    }

    private String getMatchStatusCode(String statusDetail){
        String result = "";
        switch (statusDetail){
            case "报名中":
                result = "1";
                break;
            case "比赛中":
                result = "2";
                break;
            case "比赛完毕":
                result = "3";
                break;
            case "公布成绩":
                result = "4";
                break;
        }
        return result;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_process_manager,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        return view;
    }
    private void initData(){
        MatchModels = new ArrayList<>();
        selectList = new HashMap<>();
        statusList = new ArrayList<>();
        statusList.add("报名中");
        statusList.add("比赛中");
        statusList.add("比赛完毕");
        statusList.add("公布成绩");
        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(getContext(),MatchModels,R.layout.fragment_match_process_manager_item) {
            @Override
            protected void convert(BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.match_name,item.getMatch_title());
                helper.setText(R.id.match_status,getStatusStr(item.getMatch_status()));
                helper.setText(R.id.match_create_time,"创建时间"+ StringUtil.getStrTime(item.getMatch_create_time(),"yyyy-MM-dd"));
                ImageView match_img = helper.getView(R.id.match_img);
                if (getContext()!=null){
                    Glide.with(getContext()).load(BaseAPI.base_url+item.getMatch_img()).into(match_img);
                }

                CheckBox checkbox = helper.getView(R.id.checkbox);
                checkbox.setTag(item.getMatch_id());
                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        compoundButton.getTag();
                        Log.e("match_id",compoundButton.getTag()+"");
                        Log.e("check",b+"");
                        selectList.put(compoundButton.getTag().toString(),b);
                    }
                });
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

    private void setMatch_status(String match_status){
        this.match_status = match_status;
    }

    public static MatchProcessManagerFragment init(String match_status){
        MatchProcessManagerFragment matchProcessManagerFragment = new MatchProcessManagerFragment();
        matchProcessManagerFragment.setMatch_status(match_status);
        return matchProcessManagerFragment;
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
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getStatus()!=null&&resultModel.getStatus().equals("200")){
                Toast.makeText(getContext(), "操作成功,请刷新数据", Toast.LENGTH_SHORT).show();
            }
        }
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
