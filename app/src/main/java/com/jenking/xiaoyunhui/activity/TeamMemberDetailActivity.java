package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.BinderThread;
import android.support.annotation.RequiresApi;
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
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.ClassContract;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.contacts.SchoolContract;
import com.jenking.xiaoyunhui.contacts.UserContract;
import com.jenking.xiaoyunhui.models.base.ClassModel;
import com.jenking.xiaoyunhui.models.base.MatchModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.SchoolModel;
import com.jenking.xiaoyunhui.models.base.UserModel;
import com.jenking.xiaoyunhui.presenters.ClassPresenter;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
import com.jenking.xiaoyunhui.presenters.SchoolPresenter;
import com.jenking.xiaoyunhui.presenters.UserPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.StringUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamMemberDetailActivity extends BaseActivity implements UserContract,SchoolContract,ClassContract,MatchContract {

    private UserPresenter userPresenter;
    private SchoolPresenter schoolPresenter;
    private ClassPresenter classPresenter;

    @BindView(R.id.user_avatar)
    ImageView user_avatar;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_realname)
    TextView user_realname;
    @BindView(R.id.user_slogan)
    TextView user_slogan;
    @BindView(R.id.class_name)
    TextView class_name;
    @BindView(R.id.school_name)
    TextView school_name;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<MatchModel> datas;
    private BaseRecyclerAdapter baseRecyclerAdapter;
    private MatchPresenter matchPresenter;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member_detail);
    }

    @Override
    public void initData() {
        super.initData();
        userPresenter = new UserPresenter(this,this);
        schoolPresenter = new SchoolPresenter(this,this);
        classPresenter = new ClassPresenter(this,this);


        datas = new ArrayList<>();
        baseRecyclerAdapter = new BaseRecyclerAdapter<MatchModel>(context,datas,R.layout.activity_mine_match_item) {

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
                intent.putExtra("match_id",datas.get(position).getMatch_id());
                intent.putExtra("justShowDetail","true");
                startActivity(intent);
            }
        });
        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(baseRecyclerAdapter);

        matchPresenter = new MatchPresenter(context,this);

        getData();
    }

    void getData(){

        Intent intent = getIntent();
        if (intent!=null){
            String user_id = intent.getStringExtra("user_id");
            if (user_id!=null){
                Map<String,String> params = RequestService.getBaseParams(this);
                params.put("user_id",user_id);
                if (userPresenter!=null){
                    userPresenter.getUserInfo(params);
                }
                if (matchPresenter!=null){
                    matchPresenter.getMatchByUserId(params);
                }
            }
        }



    }

    @Override
    public void updateUserinfoResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getUserinfoResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null&&resultModel.getData().size()>=1){
                UserModel userModel = (UserModel)resultModel.getData().get(0);
                Log.e("getUserinfoResult",userModel.toString());
                refreshView(userModel);
            }
        }
    }

    public void refreshView(UserModel userModel){
        if (userModel==null)return;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop();
        requestOptions.error(R.mipmap.avatar2);
        requestOptions.placeholder(R.mipmap.avatar2);
        Glide.with(this).load(BaseAPI.base_url+userModel.getUser_avatar()).apply(requestOptions).into(user_avatar);

        user_name.setText(userModel.getUser_name());
        user_realname.setText(userModel.getUser_realname());
        user_slogan.setText(userModel.getUser_slogan());

        getClassDetail(userModel.getUser_class());
        getSchoolDetail(userModel.getUser_school());
    }

    private void getClassDetail(String class_id){
        if (class_id!=null&&!class_id.equals("")){
            Map<String,String> params = RequestService.getBaseParams(this);
            params.put("class_id",class_id);
            if (classPresenter!=null){
                classPresenter.getClassById(params);
            }
        }else{
            //TODO
        }
    }

    private void getSchoolDetail(String school_id){
        if (school_id!=null&&!school_id.equals("")){
            Map<String,String> params = RequestService.getBaseParams(this);
            params.put("school_id",school_id);
            if (schoolPresenter!=null){
                schoolPresenter.getSchoolById(params);
            }
        }else{
            //TODO
        }
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }

    @Override
    public void getClassResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null&&resultModel.getData().size()>=1){
                ClassModel classModel = (ClassModel)resultModel.getData().get(0);
                if (classModel!=null){
                    Log.e("getSchoolByIdResult",classModel.toString());
                    class_name.setText(classModel.getClass_name());
                }
            }
        }
    }

    @Override
    public void getAllClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void addClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void modifyClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void deleteClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getAllSchoolResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getSchoolByIdResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null&&resultModel.getData().size()>=1){
                SchoolModel schoolModel = (SchoolModel)resultModel.getData().get(0);
                if (schoolModel!=null){
                    Log.e("getSchoolByIdResult",schoolModel.toString());
                    school_name.setText(schoolModel.getSchool_name());
                }
            }
        }
    }

    @Override
    public void addSchoolResult(boolean isSuccess, Object object) {

    }

    @Override

    public void getAllMatchResult(boolean isSuccess, Object object) {

    }

    public void updateSchoolResult(boolean isSuccess, Object object) {

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
        if (isSuccess&&object!=null){
            ResultModel resultModel = (ResultModel)object;
            if (StringUtil.isEquals(resultModel.getStatus(),"200")){
                datas = resultModel.getData();
                baseRecyclerAdapter.setData(datas);
            }
        }
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
    public void deleteSchoolResult(boolean isSuccess, Object object) {
    }
}
