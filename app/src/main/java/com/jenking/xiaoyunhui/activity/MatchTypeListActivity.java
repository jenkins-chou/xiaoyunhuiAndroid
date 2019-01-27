package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchTypeContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.MatchTypeModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchTypePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MatchTypeListActivity extends BaseActivity implements MatchTypeContract {

    public final static int SelectTypeResultCode = 20000;

    private int deletePosition = 0;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private List<MatchTypeModel> datas;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private MatchTypePresenter matchTypePresenter;
    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.add_match_type)
    void add_match_type(){
        Intent intent = new Intent(context,MatchTypeAddActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_type_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (matchTypePresenter!=null){
            matchTypePresenter.getAllMatchType(RequestService.getBaseParams(context));
        }
    }

    @Override
    public void initData() {
        super.initData();
        datas = new ArrayList<>();
        //获取数据
        matchTypePresenter = new MatchTypePresenter(context,this);

        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchTypeModel>(context,datas,R.layout.activity_match_type_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, MatchTypeModel item) {
                helper.setText(R.id.item_name,item.getMatch_type_name());

                TextView item_select = helper.getView(R.id.item_select);
                item_select.setTag(item.getMatch_type_id());
                item_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        select((String)v.getTag());
                    }
                });

                TextView item_delete = helper.getView(R.id.item_delete);
                item_delete.setTag(item.getMatch_type_id());
                item_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete((String)v.getTag());
                    }
                });
            }
        };
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);
    }

    @Override
    public void getAllMatchTypeResult(boolean isSuccess, Object object) {
        if (isSuccess&&object!=null) {
            ResultModel resultModel = (ResultModel) object;
            Log.e("getAllMatchTypeResult", resultModel.toString());
            if (resultModel.getStatus() != null && resultModel.getStatus().equals("200")) {
                datas = resultModel.getData();
                if (datas != null && datas.size() > 0) {
                    baseRecyclerAdapter.setData(datas);
                }
            }
        }
    }

    @Override
    public void addMatchTypeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void deleteMatchTypeResult(boolean isSuccess, Object object) {
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel) object;
            if (resultModel.getStatus().equals("200")){
                if (datas!=null)datas.remove(deletePosition);
                baseRecyclerAdapter.setData(datas);
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void select(String id){
        MatchTypeModel matchTypeModel = getMatchType(id);
        Log.e("select",matchTypeModel!=null?matchTypeModel.toString():"");
        if (matchTypeModel!=null){
            Intent intent = getIntent();
            intent.putExtra("match_type_id",matchTypeModel.getMatch_type_id());
            intent.putExtra("match_type_name",matchTypeModel.getMatch_type_name());
            setResult(SelectTypeResultCode,intent);
            finish();
        }
    }

    private void delete(String id){
        final MatchTypeModel matchTypeModel = getMatchType(id);
        Log.e("delete",matchTypeModel!=null?matchTypeModel.toString():"");
        if (matchTypeModel!=null){
        CommonTipsDialog.create(context,"温馨提示","真的要删除"+matchTypeModel.getMatch_type_name()+"吗？",false).setOnClickListener(new CommonTipsDialog.OnClickListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {
                Map<String,String> params = RequestService.getBaseParams(context);
                params.put("match_type_id",matchTypeModel.getMatch_type_id());
                matchTypePresenter.deleteMatchType(params);
            }
        }).show();
        }
        
    }

    private MatchTypeModel getMatchType(String id){
        MatchTypeModel matchTypeModel = null;
        if (id==null)return null;
        if (datas!=null){
            for (int i =0;i<datas.size();i++){
                if (id.equals(datas.get(i).getMatch_type_id())){
                    matchTypeModel = datas.get(i);
                    deletePosition = i;
                }
            }
        }
            return matchTypeModel;
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
