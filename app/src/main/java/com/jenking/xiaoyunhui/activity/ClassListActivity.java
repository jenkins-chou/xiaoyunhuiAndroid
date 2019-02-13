package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.ClassContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ClassModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.ClassPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.Const;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassListActivity extends BaseActivity implements ClassContract {
    public final static int SelectClassResutlCode = 1268;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.empty_show)
    TextView empty_show;
    @BindView(R.id.add_class)
    TextView add_class;

    private List<ClassModel> classModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private ClassPresenter classPresenter;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @OnClick(R.id.add_class)
    void add_class(){
        Intent intent = new Intent(context,ClassAddActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
    }

    @Override
    public void initData() {
        super.initData();
        classModels = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<ClassModel>(context,classModels,R.layout.activity_class_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, final ClassModel item) {
                helper.setText(R.id.class_name,item.getClass_name());

                TextView modify = helper.getView(R.id.modify);
                TextView delete = helper.getView(R.id.delete);

                if (AccountTool.isLogin(ClassListActivity.this)) {
                    if (AccountTool.getUserType(ClassListActivity.this).equals(Const.User_type_manager)) {
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
                if (classModels.get(position)!=null){
                    Intent intent = getIntent();
                    intent.putExtra("select_class_id",classModels.get(position).getClass_id());
                    intent.putExtra("select_class_name",classModels.get(position).getClass_name());
                    setResult(SelectClassResutlCode,intent);
                    finish();
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
        classPresenter = new ClassPresenter(context,this);
    }

    private void getData(){
        if (classPresenter!=null){
            classPresenter.getAllClass(RequestService.getBaseParams(context));
        }
    }

    private void modify(final ClassModel classModel){
        if (classModel==null)return;
            Intent intent = new Intent(this,ClassModifyActivity.class);
            intent.putExtra("class_id",classModel.getClass_id());
            startActivity(intent);
    }

    private void delete(final ClassModel classModel){
        if (classModel==null)return;
        CommonTipsDialog.create(this,"温馨提示","确定要删除吗?",false)
                .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void confirm() {
                        if (classPresenter!=null){
                            Map<String,String> params = RequestService.getBaseParams(ClassListActivity.this);
                            params.put("class_id",classModel.getClass_id());
                            classPresenter.deleteClass(params);
                        }
                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void initView() {
        super.initView();
        if (AccountTool.isLogin(context)
                &&AccountTool.getUserType(context).equals(Const.User_type_manager)){
            add_class.setVisibility(View.VISIBLE);
        }else {
            add_class.setVisibility(View.GONE);
        }
    }

    @Override
    public void getClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getAllClassResult(boolean isSuccess, Object object) {
        smartRefreshLayout.finishRefresh();
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null){
                classModels = resultModel.getData();
                baseRecyclerAdapter.setData(classModels);
            }
        }
        refreshView();
    }

    private void refreshView(){
        if (classModels==null||classModels.size()<=0){
            empty_show.setVisibility(View.VISIBLE);
        }else{
            empty_show.setVisibility(View.GONE);
        }
    }

    @Override
    public void addClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void modifyClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void deleteClassResult(boolean isSuccess, Object object) {
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
