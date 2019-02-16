package com.jenking.xiaoyunhui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.contacts.ScoreContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.main.scoreForExcel.AllScoreIntegral;
import com.jenking.xiaoyunhui.models.main.scoreForExcel.ScoreExcelModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.presenters.ScorePresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.ExcelTools;
import com.jenking.xiaoyunhui.tools.StringUtil;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ScoreManagerActivity extends BaseActivity implements MatchContract,ScoreContract {

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    private Unbinder unbinder;
    private Context context;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private List<MatchModel> datas;

    private MatchPresenter matchPresenter;
    private ScorePresenter scorePresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_show)
    TextView empty_show;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private int REQUEST_PERMISSION_CODE = 1001;


    @OnClick(R.id.output_score)
    void output_score(){

        if (scorePresenter!=null){
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
                }else{
                    scorePresenter.getAllScoreIntegral(RequestService.getBaseParams(this));
                }
            }else{
                scorePresenter.getAllScoreIntegral(RequestService.getBaseParams(this));
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_manager);
    }

    public void initData(){
        context = this;
        datas = new ArrayList<>();

        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(context,datas,R.layout.fragment_publish_score2_item) {
            @Override
            protected void convert(BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.match_title,item.getMatch_title());
                helper.setText(R.id.match_status,getMatchStatusName(item.getMatch_status()));
                helper.setText(R.id.match_number,item.getMatch_athletes_num());
                helper.setText(R.id.match_time, StringUtil.getStrTime(item.getMatch_time(),"yyyy-MM-dd HH:ss"));

                TextView item_button = helper.getView(R.id.item_button);
                if (item.getMatch_status().equals(Const.Match_type_one)){
                    item_button.setText("报名中");
                    item_button.getBackground().mutate().setAlpha(100);
                }else if (item.getMatch_status().equals(Const.Match_type_two)){
                    item_button.setText("比赛中");
                    item_button.getBackground().mutate().setAlpha(100);
                }else if (item.getMatch_status().equals(Const.Match_type_three)){
                    item_button.setText("已完成");
                    item_button.getBackground().mutate().setAlpha(255);
                }else if (item.getMatch_status().equals(Const.Match_type_four)){
                    item_button.setText("已公布成绩");
                    item_button.getBackground().mutate().setAlpha(255);
                }

            }
        };

        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (datas.get(position)!=null){
                    String status = datas.get(position).getMatch_status();
                    switch (status){
                        case Const.Match_type_one: case Const.Match_type_two:
                            CommonTipsDialog.showTip(context,"温馨提示",datas.get(position).getMatch_title()+"正在报名中/比赛中，请在比赛完成后公布成绩",false);
                            break;
//                        case Const.Match_type_three:
//                            Intent intent = new Intent(context,ScoreOperateActivity.class);
//                            intent.putExtra("match_id",datas.get(position).getMatch_id());
//                            startActivity(intent);
//                            break;
                        case Const.Match_type_four:
                            Intent intent = new Intent(context,ScoreShowActivity.class);
                            intent.putExtra("match_id",datas.get(position).getMatch_id());
                            startActivity(intent);
                            break;
                    }
//                    Intent intent = new Intent(context,ScoreOperateActivity.class);
//                    intent.putExtra("match_id",datas.get(position).getMatch_id());
//                    startActivity(intent);
                }
            }
        });
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        smartRefreshLayout.setRefreshHeader(new TaurusHeader(context));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });

        matchPresenter = new MatchPresenter(context,this);
        scorePresenter = new ScorePresenter(context,this);
        getData();
    }

    void getData(){
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("user_id", AccountTool.getLoginUser(context).getUser_id());
        matchPresenter.getAllMatch(params);
    }

    String getMatchStatusName(String key){
        String result = "";
        switch (key){
            case Const.Match_type_one:
                result = "报名中";
                break;
            case Const.Match_type_two:
                result = "比赛中";
                break;
            case Const.Match_type_three:
                result = "已完成";
                break;
            case Const.Match_type_four:
                result = "已公布成绩";
                break;
        }
        return result;
    }

    @Override
    public void getAllMatchResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getStatus()!=null){
                filter(resultModel.getData());
            }
        }
        refershView();
    }

    private void filter(List<MatchModel> source){
        if (source==null){
            return;
        }
        datas.clear();
        for (int i = 0;i<source.size();i++){
            String status = source.get(i).getMatch_status();
            if (status!=null
                    &&(StringUtil.isEquals(status, Const.Match_type_one)
                    ||StringUtil.isEquals(status, Const.Match_type_two)
                    ||StringUtil.isEquals(status, Const.Match_type_three)
                    ||StringUtil.isEquals(status, Const.Match_type_four))){
                datas.add(source.get(i));
            }
        }
        baseRecyclerAdapter.setData(datas);
    }
    private void refershView(){
        if (datas==null||datas.size()<=0){
            empty_show.setVisibility(View.VISIBLE);
        }else{
            empty_show.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_PERMISSION_CODE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "获取权限成功，请重新保存", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void getMatchByStatusResult(boolean isSuccess, Object object) {

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

    @Override
    public void getScoreListByUserIdResult(boolean isSuccess, Object object) {

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

    @Override
    public void getAllScoreIntegral(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
//            Toast.makeText(this, "获取成功", Toast.LENGTH_SHORT).show();
            Log.e("result",object.toString());

            List<AllScoreIntegral> list = new ArrayList<>();
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null){
                list = resultModel.getData();
                if (list!=null){
                    saveExcel(list);
                }
            }
        }else {
            Toast.makeText(this, "系统无数据", Toast.LENGTH_SHORT).show();
        }
    }

    void saveExcel(List datas){
            String path = Environment.getExternalStorageDirectory().getPath()+"/校运会";
            File file = new File(path);
            //文件夹是否已经存在
            if (!file.exists()) {
                file.mkdirs();
            }
            String[] title = {"比赛名称", "比赛时间", "比赛地点", "团队名称", "参赛者名称","参赛者成绩","成绩单位","得分"};
            String fileName = file.toString() + "/" + "全部成绩.xls";
            ExcelTools.initExcel(fileName, title);
            ExcelTools.writeObjListToExcel(datas, fileName, this);
            CommonTipsDialog.showTip(this,"温馨提示","导出excel成功\n文件位置：文件根目录/校运会/全部成绩.xls",false);
//            Toast.makeText(this, "导出Excel成功", Toast.LENGTH_LONG).show();
    }
}
