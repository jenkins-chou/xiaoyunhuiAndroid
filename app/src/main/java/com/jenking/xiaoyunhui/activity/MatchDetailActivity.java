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
import android.widget.LinearLayout;
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
import com.jenking.xiaoyunhui.dialog.CommonBottomListDialog;
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

import static com.jenking.xiaoyunhui.tools.MatchTypeTool.getMatchType;

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
    TextView footer;//立即报名按钮
    @BindView(R.id.unapply_match)
    TextView unapply_match;//撤销报名按钮
    @BindView(R.id.footer2)
    LinearLayout footer2;
    @BindView(R.id.footer3)
    TextView footer3;//裁判员查看报名名单

    @BindView(R.id.loading)
    CommonLoading loading;

    @BindView(R.id.header)
    RelativeLayout header;


    @BindView(R.id.scrollView)
    CommonScrollView scrollView;

    private boolean loadFinishOne = false;
    private boolean loadFinishTwo = false;

    private List<String> statusList;

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
                if (AccountTool.getLoginUser(context).getUser_type().equals("3")||AccountTool.getLoginUser(context).getUser_type().equals("2")){
                    CommonTipsDialog.showTip(context,"温馨提示","管理员/裁判员不能报名",false);
                }else{
                    if (!AccountTool.isCompleteUserInfo(context)){
                        CommonTipsDialog.create(context,"温馨提示","请到【我的】-【右上方设置】-【修改个人资料】中完善个人信息后重新报名",false)
                                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                                    @Override
                                    public void cancel() {

                                    }

                                    @Override
                                    public void confirm() {
                                        finish();
                                    }
                                }).show();
                    }else{
                        if (matchDetailModel.getMatch_status().equals("1")){
                            CommonTipsDialog.create(context,"温馨提示","确定要报名该比赛吗",false)
                                    .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                                        @Override
                                        public void cancel() {

                                        }

                                        @Override
                                        public void confirm() {
                                            Map<String,String> params = RequestService.getBaseParams(context);
                                            params.put("match_id",match_id);
                                            params.put("user_id",AccountTool.getLoginUser(context).getUser_id());
                                            params.put("user_match_status","1");//报名中,跟随match_status
                                            matchPresenter.addUserMatch(params);
                                            setLoadingEnable(true);
                                        }
                                    }).show();
                        }else{
                            CommonTipsDialog.showTip(this,"温馨提示","当前比赛已经过了报名时间",false);
                        }
                    }
                }
            }else{
                CommonTipsDialog.showTip(context,"温馨提示","请返回登录后重试",false);
            }
        }else {
            CommonTipsDialog.showTip(context,"温馨提示","未加载完成,请稍候",false);
        }

    }

    @OnClick(R.id.footer3)
    void footer3(){
        Intent intent = new Intent(this,MatchNameListActivity.class);
        intent.putExtra("match_id",match_id);
        if (matchDetailModel!=null){
            intent.putExtra("match_status",matchDetailModel.getMatch_status());
        }
        startActivity(intent);
    }


    //撤销报名
    @OnClick(R.id.unapply_match)
    void unapply_match(){
        if (loadFinishTwo&&loadFinishOne&&matchDetailModel!=null){
                if (matchDetailModel.getMatch_status()!=null&&matchDetailModel.getMatch_status().equals("1")){
                    CommonTipsDialog.create(this,"温馨提示","确定要撤销该比赛的报名吗?",false)
                            .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                                @Override
                                public void cancel() {

                                }

                                @Override
                                public void confirm() {
                                    Map<String,String> params = RequestService.getBaseParams(context);
                                    params.put("match_id",match_id);
                                    params.put("user_id",AccountTool.getLoginUser(context).getUser_id());
                                    matchPresenter.deleteUserMatch(params);
                                    setLoadingEnable(true);
                                }
                            }).show();
                }else{
                    Toast.makeText(this, "当前比赛不能撤销报名", Toast.LENGTH_SHORT).show();
                }
        }else {
            Toast.makeText(this, "未加载完成", Toast.LENGTH_SHORT).show();
        }
    }

    //删除比赛
    @OnClick(R.id.delete_match)
    void delete_match(){
        CommonTipsDialog.create(this,"温馨提示","确认要删除该比赛吗",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm() {
                        Map<String,String> params = RequestService.getBaseParams(MatchDetailActivity.this);
                        params.put("match_id",match_id);
                        matchPresenter.deleteMatch(params);
                        setLoadingEnable(true);
                    }
                }).show();
    }

    //名单管理
    @OnClick(R.id.name_list)
    void name_list(){
        Intent intent = new Intent(this,MatchNameListActivity.class);
        intent.putExtra("match_id",match_id);
        startActivity(intent);
    }

    //更换状态
    @OnClick(R.id.modify_match_status)
    void modify_match_status(){
        final Map<String,String> params = RequestService.getBaseParams(this);
        CommonBottomListDialog commonBottomListDialog = new CommonBottomListDialog(this,"选择状态",statusList,"",true) {
            @Override
            protected void setOnItemClickListener(String value) {
                String match_status = getMatchStatusCode(value);
                String sql = "update matchs set match_status = '"+match_status+"' where match_id = "+match_id;
                params.put("sql",sql);
                matchPresenter.excute(params);
                setLoadingEnable(true);
            }
        };
        commonBottomListDialog.show();


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


        Intent intent = getIntent();
        if (intent!=null&&StringUtil.isNotEmpty(intent.getStringExtra("justShowDetail"))){
            footer.setVisibility(View.GONE);
            footer2.setVisibility(View.GONE);
            footer3.setVisibility(View.GONE);
        }else{
            if (AccountTool.isLogin(context)){
                if (!AccountTool.getLoginUser(context).getUser_type().equals("1")){
                    footer.setVisibility(View.GONE);
                }else{
                    //我参加的比赛
                    if (getIntent()!=null&&getIntent().getStringExtra("mineJoinMatch")!=null&&getIntent().getStringExtra("mineJoinMatch").equals("true")){
                        footer.setVisibility(View.GONE);
                        unapply_match.setVisibility(View.VISIBLE);
                    }
                }
                if (AccountTool.getLoginUser(context).getUser_type().equals("3")){
                    footer2.setVisibility(View.VISIBLE);
                }else{
                    footer2.setVisibility(View.GONE);
                }
            }else{
                footer2.setVisibility(View.GONE);
            }
            if (AccountTool.getLoginUser(context).getUser_type().equals("2")){
                footer3.setVisibility(View.VISIBLE);
            }else{
                footer3.setVisibility(View.GONE);
            }
        }

    }

    public void initData(){
        context = this;
        unbinder = ButterKnife.bind(this);
        match_id = getIntent()!=null?getIntent().getStringExtra("match_id"):"";
        matchPresenter = new MatchPresenter(context,this);
        statusList = new ArrayList<>();
        statusList.add("报名中");
        statusList.add("比赛中");
        statusList.add("比赛完毕");
        statusList.add("公布成绩");
        getData();
    }

    private void getData(){
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
            match_type.setText(matchDetailModel.getMatch_type_name());
            match_number.setText(matchDetailModel.getMatch_athletes_num());
            match_time.setText(StringUtil.getStrTime(matchDetailModel.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));
            match_status.setText(getMatchType(matchDetailModel.getMatch_status()));
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
                        getData();
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
    public void deleteUserMatchResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(context, "撤销成功，请返回刷新数据", Toast.LENGTH_SHORT).show();
            finish();
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

    @Override
    public void getMatchByRefereeIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void excuteResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(this, "操作成功，正在刷新数据", Toast.LENGTH_SHORT).show();
            getData();
        }
    }

    @Override
    public void deleteMatchResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(this, "删除成功,请返回刷新数据", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void refreshUserMatch(){
        if (userMatchModelList!=null&&userMatchModelList.size()>0){
            match_register_number.setText(userMatchModelList.size()+"人");
//            match_status.setText();
        }else{
            match_register_number.setText("0人");
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
