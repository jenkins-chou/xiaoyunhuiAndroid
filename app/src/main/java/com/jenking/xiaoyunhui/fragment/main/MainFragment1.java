package com.jenking.xiaoyunhui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.activity.MatchManagerActivity;
import com.jenking.xiaoyunhui.activity.MatchSearchActivity;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchTypeContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.fragment.part1.IndexFragment;
import com.jenking.xiaoyunhui.fragment.part1.TamplateFragment;
import com.jenking.xiaoyunhui.models.base.MatchTypeModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchTypePresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment1 extends Fragment implements MatchTypeContract {
    private Unbinder unbinder;
    private MatchTypePresenter matchTypePresenter;
    //https://github.com/H07000223/FlycoTabLayout/blob/master/README_CN.md
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.get_data_again)
    TextView get_data_again;
    @BindView(R.id.loading)
    CommonLoading loading;

    @BindView(R.id.manager_in)
    LinearLayout manager_in;//管理员入口

    @OnClick(R.id.get_data_again)
    void get_data_again(){
        getDataFromService();
    }

    @OnClick(R.id.search_button)
    void search_button(){
//        ResultModel resultModel = null;
//        Log.e("",resultModel.toString());
        Intent intent = new Intent(getContext(),MatchSearchActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.manager_in,R.id.manager_in_text})
    void manager_in(){
        Intent intent = new Intent(getContext(),MatchManagerActivity.class);
        startActivity(intent);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1,container,false);
        unbinder = ButterKnife.bind(this,view);
        initData();
        initView();
        return view;
    }

    private void initData(){
        //获取数据
        matchTypePresenter = new MatchTypePresenter(getContext(),this);
        getDataFromService();
    }

    private void getDataFromService(){
        if (matchTypePresenter!=null){
            matchTypePresenter.getAllMatchType(RequestService.getBaseParams(getContext()));
            setLoadingEnable(true);
        }
    }

    private void initView(){

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }

    private ArrayList<Fragment> getFragments(List<MatchTypeModel> matchTypeModels){

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        if (matchTypeModels!=null){
            for (int i = 0;i<matchTypeModels.size();i++){
                fragments.add(TamplateFragment.init(matchTypeModels.get(i).getMatch_type_id()));
            }
        }

        return fragments;
    }

    @Override
    public void getAllMatchTypeResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (isSuccess&&object!=null){
            get_data_again.setVisibility(View.GONE);
            ResultModel resultModel = (ResultModel)object;
            Log.e("getAllMatchTypeResult",resultModel.toString());
            String[] titles;
            if (resultModel.getStatus()!=null&&resultModel.getStatus().equals("200")){
                List<MatchTypeModel> matchTypeModels = resultModel.getData();

                if (matchTypeModels!=null&&matchTypeModels.size()>0){
                    titles = new String[matchTypeModels.size()+1];
                    titles[0] = "首页";
                    for (int i = 0;i<matchTypeModels.size();i++){
                        titles[i+1] = matchTypeModels.get(i).getMatch_type_name();
                    }
                    slidingTabLayout.setViewPager(viewPager,titles,getActivity(),getFragments(matchTypeModels));
                }else{
                    titles = new String[]{"首页"};
                    slidingTabLayout.setViewPager(viewPager,titles,getActivity(),getFragments(null));
                }
            }else{
                titles = new String[]{"首页"};
                slidingTabLayout.setViewPager(viewPager,titles,getActivity(),getFragments(null));
            }
        }else{
            get_data_again.setVisibility(View.VISIBLE);
        }
    }

    void refreshView(){
        if (AccountTool.isLogin(getContext())){
            switch (AccountTool.getLoginUser(getContext()).getUser_type()){
                case "3":
                    manager_in.setVisibility(View.VISIBLE);
                    break;
                default:
                    manager_in.setVisibility(View.GONE);
                    break;
            }

        }else{
            manager_in.setVisibility(View.GONE);
        }
    }
    void setLoadingEnable(boolean enable){
        if (loading!=null){
            loading.setVisibility(enable?View.VISIBLE:View.GONE);
        }
    }

    @Override
    public void addMatchTypeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void deleteMatchTypeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
