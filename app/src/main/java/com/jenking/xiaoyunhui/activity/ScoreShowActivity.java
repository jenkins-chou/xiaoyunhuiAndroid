package com.jenking.xiaoyunhui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.google.gson.Gson;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.contacts.ScoreContract;
import com.jenking.xiaoyunhui.contacts.UserMatchContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.dialog.CommonBottomListDialog;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.MatchDetailModel;
import com.jenking.xiaoyunhui.models.base.ProjectBean;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.ScoreDetailModel;
import com.jenking.xiaoyunhui.models.base.UserModel;
import com.jenking.xiaoyunhui.models.main.scoreForExcel.ScoreExcelModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.presenters.ScorePresenter;
import com.jenking.xiaoyunhui.presenters.UserMatchPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.ExcelTools;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ScoreShowActivity extends BaseActivity implements MatchContract,UserMatchContract,ScoreContract {
    private String match_id;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    private List<ScoreDetailModel> scoreDetailModels;
    private MatchPresenter matchPresenter;//用于获取比赛详情
    private ScorePresenter scorePresenter;

    private MatchDetailModel matchDetailModel;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.loading)
    CommonLoading loading;

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

    @BindView(R.id.empty_show)
    TextView empty_show;

    @BindView(R.id.match_status)
    TextView match_status;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @BindView(R.id.modify_score)
    TextView modify_score;


    @OnClick(R.id.modify_score)
    void modify_score(){
        Intent intent = new Intent(this,ScoreOperateActivity.class);
        intent.putExtra("match_id",match_id);
        startActivity(intent);
    }


    @OnClick(R.id.footer)
    void footer(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }else{
                saveExcel();
            }
        }else{
            saveExcel();
        }
    }

    void saveExcel(){
        List<ScoreExcelModel> list = new ArrayList<>();
        if (matchDetailModel!=null&&scoreDetailModels!=null){
            for (int i = 0;i<scoreDetailModels.size();i++){
                ScoreExcelModel scoreExcelModel = new ScoreExcelModel();
                scoreExcelModel.setMatch_title(matchDetailModel.getMatch_title());
                scoreExcelModel.setMatch_time(StringUtil.getStrTime(matchDetailModel.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));
                scoreExcelModel.setMatch_address(matchDetailModel.getMatch_address());
                scoreExcelModel.setMatch_abstract(matchDetailModel.getMatch_abstract());
                scoreExcelModel.setMatch_detail(matchDetailModel.getMatch_detail());
                scoreExcelModel.setUser_name(scoreDetailModels.get(i).getUser_name());
                scoreExcelModel.setScore_value(scoreDetailModels.get(i).getScore_value());
                scoreExcelModel.setScore_unit(scoreDetailModels.get(i).getScore_unit());
                scoreExcelModel.setScore_integral(scoreDetailModels.get(i).getScore_integral());
                list.add(scoreExcelModel);
            }

            String path = Environment.getExternalStorageDirectory().getPath()+"/校运会";
            File file = new File(path);
            //文件夹是否已经存在
            if (!file.exists()) {
                file.mkdirs();
            }

            String[] title = {"比赛名称", "比赛时间", "比赛地点", "比赛简介", "比赛说明","参赛者","参赛者成绩","成绩单位","得分"};
            String fileName = file.toString() + "/" + matchDetailModel.getMatch_title()+".xls";
            ExcelTools.initExcel(fileName, title);
            ExcelTools.writeObjListToExcel(list, fileName, this);
            CommonTipsDialog.showTip(this,"温馨提示","导出excel成功\n文件位置：文件根目录/校运会/比赛名称.xls",false);
//            Toast.makeText(this, "导出Excel成功", Toast.LENGTH_LONG).show();
        }else{
            Log.e("matchDetailModel","null");
        }
    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private int REQUEST_PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_show);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_PERMISSION_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                saveExcel();
            }
        }
    }

    @Override
    public void initData(){
        scoreDetailModels = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<ScoreDetailModel>(this,scoreDetailModels,R.layout.activity_score_show_item) {
            @Override
            protected void convert(com.github.library.BaseViewHolder helper, final ScoreDetailModel item) {
                ImageView imageView = helper.getView(R.id.item_avatar);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.avatar2);
                requestOptions.error(R.mipmap.avatar2);
                Glide.with(ScoreShowActivity.this).load(BaseAPI.base_url+item.getUser_avatar()).apply(requestOptions).into(imageView);
                helper.setText(R.id.item_name,item.getUser_name());

                helper.setText(R.id.score_integral,"得分:"+item.getScore_integral());
                helper.setText(R.id.score_value,"成绩:"+item.getScore_value());
                helper.setText(R.id.score_unit,item.getScore_unit());

                TextView score_update = helper.getView(R.id.score_update);
                if (AccountTool.isLogin(ScoreShowActivity.this)) {
                    if (AccountTool.getUserType(ScoreShowActivity.this).equals(Const.User_type_referee)) {
                        score_update.setVisibility(View.VISIBLE);
                    }else{
                        score_update.setVisibility(View.GONE);
                    }
                }else{
                    score_update.setVisibility(View.GONE);
                }

                score_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String json = new Gson().toJson(item);
                        Intent intent = new Intent(ScoreShowActivity.this,ScoreUpdateActivity.class);
                        intent.putExtra("data",json);
                        startActivity(intent);
                    }
                });
            }
        };
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);
        matchPresenter = new MatchPresenter(context,this);
        scorePresenter = new ScorePresenter(context,this);
    }

    @Override
    public void initView() {
        super.initView();
        if (AccountTool.isLogin(this)) {
            if (AccountTool.getUserType(this).equals(Const.User_type_referee)) {
                modify_score.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        Intent intent = getIntent();
        if (intent!=null){
            match_id = intent.getStringExtra("match_id");
            Map<String,String> params = RequestService.getBaseParams(context);
            params.put("match_id",match_id);
            matchPresenter.getMatchById(params);
            scorePresenter.getScoreListByMatchId(params);
            setLoadingEnable(true);
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
        setLoadingEnable(false);
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel.getData()!=null&&resultModel.getData().size()>0){
                matchDetailModel = (MatchDetailModel) resultModel.getData().get(0);
                Log.e("matchDetailModel",matchDetailModel.toString());
            }
        }
        refreshVeiw();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void refreshVeiw(){
        if (matchDetailModel!=null){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.item_default_img);
            requestOptions.placeholder(R.mipmap.item_default_img);
            if (!isDestroyed()){
                Glide.with(this).load(BaseAPI.base_url+matchDetailModel.getMatch_img()).into(match_image);
            }
            match_create_time.setText(StringUtil.getStrTime(matchDetailModel.getMatch_create_time(),"yyyy-MM-dd HH:mm:ss"));
            match_title.setText(matchDetailModel.getMatch_title());
            match_type.setText(matchDetailModel.getMatch_type_name());
            match_number.setText(matchDetailModel.getMatch_athletes_num());
            match_time.setText(StringUtil.getStrTime(matchDetailModel.getMatch_time(),"yyyy-MM-dd HH:mm:ss"));

            switch (matchDetailModel.getMatch_status()){
                case Const.Match_type_one:
                    match_status.setText("报名中");
                    break;
                case Const.Match_type_two:
                    match_status.setText("比赛中");
                    break;
                case Const.Match_type_three:
                    match_status.setText("已完成");
                    break;
                case Const.Match_type_four:
                    match_status.setText("已公布成绩");
                    break;
            }
        }else {
            //TODO 显示错误信息
        }
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

    void setLoadingEnable(boolean enable){
        if (loading!=null){
            loading.setVisibility(enable? View.VISIBLE:View.GONE);
        }
    }

    @Override
    public void getUserMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getUserMatchDetailByMatchIdResult(boolean isSuccess, Object object) {

    }

    private void refreshUser(){
        if (scoreDetailModels==null||scoreDetailModels.size()<=0){
            empty_show.setVisibility(View.VISIBLE);
        }else{
            empty_show.setVisibility(View.GONE);
        }
    }


    @Override
    public void getRefereeMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void updateUserMatchsResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getScoreListByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getScoreListByMatchIdResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                scoreDetailModels = resultModel.getData();
                Log.e("getUserMatchDetail",""+scoreDetailModels.toString());
                baseRecyclerAdapter.setData(scoreDetailModels);
            }
        }
        refreshUser();
    }

    @Override
    public void getScorePublishListByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getAllScoreList(boolean isSuccess, Object object) {

    }

    @Override
    public void addScoresResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void updateScoreResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getAllScoreIntegral(boolean isSuccess, Object object) {

    }
}
