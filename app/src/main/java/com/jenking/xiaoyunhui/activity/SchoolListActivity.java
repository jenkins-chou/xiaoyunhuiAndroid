package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.SchoolContract;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.SchoolModel;
import com.jenking.xiaoyunhui.presenters.SchoolPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SchoolListActivity extends BaseActivity implements SchoolContract {
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
            protected void convert(BaseViewHolder helper, SchoolModel item) {
                helper.setText(R.id.item_name,item.getSchool_name());
                ImageView imageView = helper.getView(R.id.item_img);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.circleCrop();
                requestOptions.timeout(300000);
                requestOptions.error(R.mipmap.avatar2);
                Log.e("imageUrl",BaseAPI.base_url+item.getSchool_logo());
                Glide.with(context).load(BaseAPI.base_url+item.getSchool_logo()).apply(requestOptions).into(imageView);
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

    @Override
    protected void onResume() {
        super.onResume();
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
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
