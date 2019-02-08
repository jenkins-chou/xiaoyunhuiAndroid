package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.UserMatchContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.UserMatchModel;
import com.jenking.xiaoyunhui.presenters.UserMatchPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;
import com.jenking.xiaoyunhui.tools.StringUtil;

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

    @BindView(R.id.footer)
    LinearLayout footer;

    private List<UserMatchModel> datas;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private UserMatchPresenter userMatchPresenter;



    private String match_id;

    String Letters[] = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.genarate)
    void genarate(){
        if (datas!=null&&datas.size()>0){
            CommonTipsDialog.create(this,"温馨提示","一键生成会覆盖原来的数据，确定要执行吗？",false)
                    .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                        @Override
                        public void cancel() {
                        }
                        @Override
                        public void confirm() {
                            for (int i = 0;i<datas.size();i++){
                                datas.get(i).setUser_group("A");
                                datas.get(i).setUser_order(""+(i+1));
                            }
                            if (baseRecyclerAdapter!=null){
                                baseRecyclerAdapter.setData(datas);
                            }

                            Log.e("con",datas.toString());
                        }
                    }).show();
        }else{
            Toast.makeText(context, "报名人数为0", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.save)
    void save(){
        if (datas!=null&&datas.size()>0){
            String sql = "replace into user_match (user_match_id,user_id,match_id,user_match_del,user_match_status,score_id,user_group,user_order) values";
            for (int i = 0;i<datas.size();i++){
                if (i==datas.size()-1){
                    sql += "("+datas.get(i).getUser_match_id()+",'"
                            +datas.get(i).getUser_id()+"','"
                            +datas.get(i).getMatch_id()+"','"
                            +datas.get(i).getUser_match_del()+"','"
                            +datas.get(i).getUser_match_status()+"','"
                            +datas.get(i).getScore_id()+"','"
                            +datas.get(i).getUser_group()+"','"
                            +datas.get(i).getUser_order()+"');";
                }else{
                    sql += "("+datas.get(i).getUser_match_id()+",'"
                            +datas.get(i).getUser_id()+"','"
                            +datas.get(i).getMatch_id()+"','"
                            +datas.get(i).getUser_match_del()+"','"
                            +datas.get(i).getUser_match_status()+"','"
                            +datas.get(i).getScore_id()+"','"
                            +datas.get(i).getUser_group()+"','"
                            +datas.get(i).getUser_order()+"'),";
                }
            }
            Log.e("sql",sql);
            if (userMatchPresenter!=null){
                Map<String,String> params = RequestService.getBaseParams(this);
                params.put("sql",sql);
                userMatchPresenter.updateUserMatchs(params);
            }
        }
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

                helper.setText(R.id.order,item.getUser_order());


                Spinner groupSpinner = helper.getView(R.id.group_type_spinner);

                if (item.getUser_group()!=null&&!item.getUser_group().equals("")){
                    int position = getLetterPosition(item.getUser_group());
                    if (position!=-1){
                        groupSpinner.setSelection(position);
                    }
                }

                groupSpinner.setTag(item.getUser_match_id());
                groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("modelId",parent.getTag()+"");
                        Log.e("groupValue",getLetterValue(position)+"");
                        setGroup(parent.getTag()+"",getLetterValue(position)+"");
                        Log.e("onItemSelected",datas.toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                if (AccountTool.isLogin(MatchNameListActivity.this)) {
                    if (AccountTool.getUserType(MatchNameListActivity.this).equals(Const.User_type_manager)) {
                        groupSpinner.setEnabled(true);
                    }else{
                        groupSpinner.setEnabled(false);
                    }
                }else{
                    groupSpinner.setEnabled(false);
                }


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
    public void initView() {
        super.initView();

        if (AccountTool.isLogin(this)){
            if (AccountTool.getUserType(this).equals(Const.User_type_manager)){
                footer.setVisibility(View.VISIBLE);
            }else{
                footer.setVisibility(View.GONE);
            }
        }else {
            footer.setVisibility(View.GONE);
        }
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

    private void setGroup(String modelId,String groupValue){
        int position = getSelectUserMatchPosition(modelId+"");
        if (position!=-1){
            if (datas!=null&&datas.get(position)!=null){
                datas.get(position).setUser_group(groupValue);
            }
        }
    }

    private void setOrder(int modelId,String orderValue){
        int position = getSelectUserMatchPosition(modelId+"");
        if (position!=-1){
            if (datas!=null&&datas.get(position)!=null){
                datas.get(position).setUser_order(orderValue);
            }
        }
    }

    private int getSelectUserMatchPosition(String id){
        int positon = -1;
        if (id==null||id.equals(""))return positon;
        if (datas!=null){
            for (int i = 0;i<datas.size();i++){
                if (id.equals(datas.get(i).getUser_match_id())){
                    positon = i;
                }
            }
        }
        return positon;
    }

    //获取分组名称对应的位置
    private int getLetterPosition(String value){
        int result = -1;
        for (int i=0;i<Letters.length;i++){
            if (Letters[i].equals(value)){
                result = i;
            }
        }
        return result;
    }
    //获取分组名称
    private String getLetterValue(int i){
        String result = "";
        if (i>=0&&i<26){
            result = Letters[i];
        }
        return result;
    }
    @Override
    public void getRefereeMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void updateUserMatchsResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
