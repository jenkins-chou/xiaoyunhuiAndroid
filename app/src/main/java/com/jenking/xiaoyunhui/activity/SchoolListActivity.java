package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.google.gson.Gson;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.SchoolContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ClassModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.SchoolModel;
import com.jenking.xiaoyunhui.presenters.SchoolPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SchoolListActivity extends BaseActivity implements SchoolContract {

    private String isFromManager;
    public final static int SelectSchoolResutlCode = 23000;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<SchoolModel> datas;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    private SchoolPresenter schoolPresenter;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.add_school)
    void add_school(){
        Intent intent = new Intent(context,SchoolAddActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
    }

    @Override
    public void initData() {
        super.initData();
        datas = new ArrayList<>();
        schoolPresenter = new SchoolPresenter(context,this);

        baseRecyclerAdapter = new BaseRecyclerAdapter<SchoolModel>(context,datas,R.layout.activity_school_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, final SchoolModel item) {
                helper.setText(R.id.item_name,item.getSchool_name());
                ImageView imageView = helper.getView(R.id.item_img);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.circleCrop();
                requestOptions.timeout(300000);
                requestOptions.error(R.mipmap.avatar2);
                Log.e("imageUrl",BaseAPI.base_url+item.getSchool_logo());
                Glide.with(context).load(BaseAPI.base_url+item.getSchool_logo()).apply(requestOptions).into(imageView);

                TextView modify = helper.getView(R.id.modify);
                TextView delete = helper.getView(R.id.delete);

                if (AccountTool.isLogin(SchoolListActivity.this)) {
                    if (AccountTool.getUserType(SchoolListActivity.this).equals(Const.User_type_manager)) {
                        modify.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);
                    }
                }

                modify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        modify(item);
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(item);
                    }
                });
            }
        };

        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = getIntent();
                intent.putExtra("select_school_id",datas.get(position).getSchool_id());
                intent.putExtra("select_school_name",datas.get(position).getSchool_name());
                setResult(SelectSchoolResutlCode,intent);
                finish();
            }
        });
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);
    }

    private void modify(final SchoolModel schoolModel){
        if (schoolModel==null)return;
        Intent intent = new Intent(this,SchoolUpdateActivity.class);
        intent.putExtra("data",new Gson().toJson(schoolModel));
        startActivity(intent);
    }

    private void delete(final SchoolModel schoolModel){
        if (schoolModel==null)return;
        CommonTipsDialog.create(this,"温馨提示","确定要删除吗?",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm() {
                        if (schoolPresenter!=null){
                            Map<String,String> params = RequestService.getBaseParams(SchoolListActivity.this);
                            params.put("school_id",schoolModel.getSchool_id());
                            schoolPresenter.deleteSchool(params);
                        }
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        if (schoolPresenter!=null){
            schoolPresenter.getSchools(RequestService.getBaseParams(context));
        }
    }

    @Override
    public void getAllSchoolResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            datas = resultModel.getData();
            baseRecyclerAdapter.setData(datas);
        }
    }

    @Override
    public void getSchoolByIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void addSchoolResult(boolean isSuccess, Object object) {

    }

    @Override
    public void updateSchoolResult(boolean isSuccess, Object object) {

    }

    @Override
    public void deleteSchoolResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            getData();
        }
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
