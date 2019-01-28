package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.customui.CommonScrollView;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.MatchDetailModel;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.UserMatchModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MatchDetailActivity extends BaseActivity implements MatchContract {

    private Unbinder unbinder;
    private String match_id;

    private MatchPresenter matchPresenter;

    private MatchDetailModel matchDetailModel;

    private List<UserMatchModel> userMatchModelList;//报名者名单

    //view stack
    @BindView(R.id.match_image)
    ImageView match_image;
    @BindView(R.id.match_create_time)
    TextView match_create_time;
    @BindView(R.id.match_title)
    TextView match_title;
    @BindView(R.id.match_type)
    TextView match_type;
    @BindView(R.id.match_number)
    TextView match_number;
    @BindView(R.id.match_time)
    TextView match_time;
    @BindView(R.id.match_status)
    TextView match_status;
    @BindView(R.id.match_register_number)
    TextView match_register_number;
    @BindView(R.id.match_address)
    TextView match_address;
    @BindView(R.id.match_referee)
    TextView match_referee;
    @BindView(R.id.school_icon)
    ImageView school_icon;
    @BindView(R.id.school_name)
    TextView school_name;
    @BindView(R.id.school_abstract)
    TextView school_abstract;
    @BindView(R.id.match_detail)
    TextView match_detail;

    @BindView(R.id.footer)
    TextView footer;
    @BindView(R.id.footer2)
    TextView footer2;

    @BindView(R.id.loading)
    CommonLoading loading;

    private boolean loadFinishOne = false;
    private boolean loadFinishTwo = false;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    //立即报名
    @OnClick(R.id.footer)
    void footer(){
        if (loadFinishOne&&loadFinishTwo){
            if (matchDetailModel==null)return;
            if (StringUtil.isNumber(matchDetailModel.getMatch_athletes_num())){
                if (Integer.parseInt(matchDetailModel.getMatch_athletes_num())<=userMatchModelList.size()){
                    CommonTipsDialog.showTip(context,"温馨提示","当前报名人数已满",false);
                    return;
                }
            }else{
                return;
            }
            if (AccountTool.isLogin(context)){
                if (AccountTool.getLoginUser(context).getUser_type().equals("3")){
                    CommonTipsDialog.showTip(context,"温馨提示","管理员不能报名",false);
                }else{
                    Map<String,String> params = RequestService.getBaseParams(context);
                    params.put("match_id",match_id);
                    params.put("user_id",AccountTool.getLoginUser(context).getUser_id());
                    matchPresenter.addUserMatch(params);
                    setLoadingEnable(true);
                }
            }else{
                CommonTipsDialog.showTip(context,"温馨提示","请返回登录后重试",false);
            }
        }else {
            CommonTipsDialog.showTip(context,"温馨提示","未加载完成,请稍候",false);
        }

    }

    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.scrollView)
    CommonScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        initData();
    }


    @Override
    public void initView() {
        super.initView();
        header.getBackground().mutate().setAlpha(0);
        scrollView.setScrollViewListener(new CommonScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(CommonScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y<=0){
                    header.getBackground().mutate().setAlpha(0);
                }else if (0<y&&y<255){
                    header.getBackground().mutate().setAlpha(y);
                }else{
                    header.getBackground().mutate().setAlpha(255);
                }
            }
        });

        if (AccountTool.isLogin(context)){
            if (!AccountTool.getLoginUser(context).getUser_type().equals("1")){
                footer.setVisibility(View.GONE);
            }
            if (AccountTool.getLoginUser(context).getUser_type().equals("3")){
                footer2.setVisibility(View.VISIBLE);
            }else{
                footer2.setVisibility(View.GONE);
            }
        }else{
            footer2.setVisibility(View.GONE);
        }
    }

    public void initData(){
        context = this;
        unbinder = ButterKnife.bind(this);
        match_id = getIntent()!=null?getIntent().getStringExtra("match_id"):"";
        matchPresenter = new MatchPresenter(context,this);

        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("match_id",match_id);
        matchPresenter.getMatchById(params);
        matchPresenter.getUserMatchByMatchId(params);
        setLoadingEnable(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void refreshVeiw(){
        if (matchDetailModel!=null){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.item_default_img);
            requestOptions.placeholder(R.mipmap.item_default_img);
            if (!isDestroyed()){
                Glide.with(context).load(BaseAPI.base_url+matchDetailModel.getMatch_img()).into(match_image);
            }

            match_create_time.setText(StringUtil.getStrTime(matchDetailModel.getMatch_create_time(),"yyyy-MM-dd HH:mm:ss"));
            match_title.setText(matchDetailModel.getMatch_title());
            match_type.setText(matchDetailModel.getMatch_type());
            match_number.setText(matchDetailModel.getMatch_athletes_num());
            match_time.setText(StringUtil.getStrTime(matchDetailModel.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));
            match_status.setText(matchDetailModel.getMatch_status());
//            match_register_number.setText(matchDetailModel.getMatch_athletes_num());//已报名人数
            match_address.setText(matchDetailModel.getMatch_address());
            match_referee.setText(matchDetailModel.getUser_name());

            if (!isDestroyed()){
                Glide.with(context).load(BaseAPI.base_url+matchDetailModel.getSchool_logo()).into(school_icon);
            }

            school_name.setText(matchDetailModel.getSchool_name());
            school_abstract.setText(matchDetailModel.getSchool_abstract());

            match_detail.setText(matchDetailModel.getMatch_detail());
        }else {
            //TODO 显示错误信息
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

    }

    @Override
    public void getMatchByIdResult(boolean isSuccess, Object object) {
        loadFinishOne = true;
        setLoadingEnable(false);
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel.getData()!=null&&resultModel.getData().size()>0){
                matchDetailModel = (MatchDetailModel) resultModel.getData().get(0);
            }
        }
        refreshVeiw();
    }

    @Override
    public void addMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void addUserMatchResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getStatus()!=null){
                switch (resultModel.getStatus()){
                    case "200":
                        Toast.makeText(context, "报名成功", Toast.LENGTH_SHORT).show();
                        break;
                    case "201":
                        Toast.makeText(context, "不能重复报名", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    @Override
    public void getUserMatchByMatchIdResult(boolean isSuccess, Object object) {
        loadFinishTwo = true;
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            userMatchModelList = resultModel.getData()!=null?resultModel.getData():new ArrayList<UserMatchModel>();
        }else{
            userMatchModelList = new ArrayList<>();
        }
        refreshUserMatch();
    }

    @Override
    public void getMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    private void refreshUserMatch(){
        if (userMatchModelList!=null&&userMatchModelList.size()>0){
            match_register_number.setText(userMatchModelList.size()+"");
//            match_status.setText();
        }
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }

    void setLoadingEnable(boolean enable){
        if (loading!=null){
            loading.setVisibility(enable?View.VISIBLE:View.GONE);
        }
    }

}
