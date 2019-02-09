package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.ClassContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ClassModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.ClassPresenter;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassModifyActivity extends BaseActivity implements ClassContract {

    @BindView(R.id.input_class_name)
    EditText input_class_name;
    @BindView(R.id.select_school)
    TextView select_school;

    private ClassPresenter classPresenter;

    private String selectSchoolId = "";
    private String selectSchoolName = "";

    private String class_id;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.select_school)
    void select_school(){
        Intent intent = new Intent(context,SchoolListActivity.class);
        startActivityForResult(intent,SchoolListActivity.SelectSchoolResutlCode);
    }

    @OnClick(R.id.submit)
    void setSubmit(){
        if (input_class_name.getText().toString()==null||input_class_name.getText().toString().equals("")){
            CommonTipsDialog.showTip(context,"温馨提示","请输入班级名称",false);
        }else if(selectSchoolId==null||selectSchoolId.equals("")){
            CommonTipsDialog.showTip(context,"温馨提示","请选择学校",false);
        }else{
            submitData();
        }

    }

    void submitData(){
        if (class_id!=null&&!class_id.equals("")) {
            Map<String, String> params = RequestService.getBaseParams(context);
            params.put("class_name", input_class_name.getText().toString());
            params.put("class_create_time", StringUtil.getTime());
            params.put("school_id", selectSchoolId);
            params.put("class_id", class_id);
            if (classPresenter != null) {
                classPresenter.modifyClass(params);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_modify);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SchoolListActivity.SelectSchoolResutlCode:
                if (data != null) {
                    selectSchoolId = data.getStringExtra("select_school_id");
                    selectSchoolName = data.getStringExtra("select_school_name");
                    select_school.setText(selectSchoolName);
                    Log.e("selectSchoolId", "" + selectSchoolId);
                    Log.e("selectSchoolName", "" + selectSchoolName);
                }
                break;
        }
    }

    @Override
    public void getClassResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            if (resultModel!=null&&resultModel.getData()!=null&&resultModel.getData().size()>0){
                ClassModel classModel = (ClassModel)resultModel.getData().get(0);
                input_class_name.setText(classModel.getClass_name());
                select_school.setText(classModel.getSchool_name());
            }
        }
    }

    @Override
    public void getAllClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void initData() {
        super.initData();
        classPresenter = new ClassPresenter(context,this);

        Intent intent = getIntent();
        if (intent!=null){
            class_id = intent.getStringExtra("class_id");

            Map<String,String> params = RequestService.getBaseParams(this);
            params.put("class_id",class_id);
            classPresenter.getClassById(params);
        }
    }

    @Override
    public void addClassResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void modifyClassResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void deleteClassResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
