package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.ClassContract;
import com.jenking.xiaoyunhui.contacts.SchoolContract;
import com.jenking.xiaoyunhui.contacts.UserContract;
import com.jenking.xiaoyunhui.models.base.ClassModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.SchoolModel;
import com.jenking.xiaoyunhui.models.base.UserModel;
import com.jenking.xiaoyunhui.presenters.ClassPresenter;
import com.jenking.xiaoyunhui.presenters.SchoolPresenter;
import com.jenking.xiaoyunhui.presenters.UserPresenter;

import org.w3c.dom.Text;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamMemberDetailActivity extends BaseActivity implements UserContract,SchoolContract,ClassContract{

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
        Intent intent = getIntent();
        if (intent!=null){
            String user_id = intent.getStringExtra("user_id");
            if (user_id!=null){
                Map<String,String> params = RequestService.getBaseParams(this);
                params.put("user_id",user_id);
                userPresenter.getUserInfo(params);
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
}
