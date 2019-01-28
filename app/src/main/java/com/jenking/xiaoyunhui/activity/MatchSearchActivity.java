package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchSearchContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MatchSearchActivity extends BaseActivity implements MatchSearchContract {

    private Unbinder unbinder;
    private List<MatchModel> matchModels;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    private MatchPresenter matchPresenter;


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.input_keyword)
    EditText input_keyword;
    @BindView(R.id.loading)
    CommonLoading loading;
    @BindView(R.id.empty_show)
    TextView empty_show;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.search_button)
    void searchButton(){
        String value = input_keyword.getText().toString();
        if (value==null||value.equals(""))return;
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("keyword",value);
        matchPresenter.searchMatch(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_search);
    }

    @Override
    public void initData() {
        super.initData();
        matchModels = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(context,matchModels,R.layout.activity_match_search_item) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            protected void convert(BaseViewHolder helper, MatchModel item) {
                helper.setText(R.id.item_title,item.getMatch_title());
                helper.setText(R.id.item_address,"比赛地点:"+item.getMatch_address());
                helper.setText(R.id.item_time,"比赛时间:"+ StringUtil.getStrTime(item.getMatch_time(),"yyyy年MM月dd日 HH时mm分ss秒"));

                ImageView item_image = helper.getView(R.id.item_img);
                if (!isDestroyed()){
                    Glide.with(context).load(BaseAPI.base_url+item.getMatch_img()).into(item_image);
                }
            }
        };

        baseRecyclerAdapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context,MatchDetailActivity.class);
                intent.putExtra("match_id",matchModels.get(position).getMatch_id());
                startActivity(intent);
            }
        });
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerview.setAdapter(baseRecyclerAdapter);

        matchPresenter= new MatchPresenter(context,this);
    }

    @Override
    public void searchMatchResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (StringUtil.isEquals(resultModel.getStatus(),"200")){
                matchModels = resultModel.getData();
                baseRecyclerAdapter.setData(matchModels);
            }
        }
        if (matchModels!=null&&matchModels.size()>0){
            empty_show.setVisibility(View.GONE);
        }else{
            empty_show.setVisibility(View.VISIBLE);
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
            loading.setVisibility(enable? View.VISIBLE:View.GONE);
        }
    }
}
