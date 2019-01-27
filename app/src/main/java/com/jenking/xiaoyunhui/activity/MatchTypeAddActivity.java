package com.jenking.xiaoyunhui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchTypeContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchTypePresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.StringUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MatchTypeAddActivity extends BaseActivity implements MatchTypeContract {

    private MatchTypePresenter presenter;
    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @BindView(R.id.input_match_type)
    EditText input_match_type;

    @OnClick(R.id.submit_add)
    void submit_add(){
        String input_match_type_str = input_match_type.getText().toString();
        if (StringUtil.isNotEmpty(input_match_type_str)){
            if (presenter!=null){
                Map<String,String> params = RequestService.getBaseParams(context);
                params.put("match_type_name",input_match_type_str);
                params.put("match_type_create_time",StringUtil.getTime());
                params.put("match_type_manager",AccountTool.getLoginUser(context).getUser_id());
                presenter.addMatchType(params);
            }
        }else{
            CommonTipsDialog.showTip(context,"温馨提示","请输入比赛类型",false);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_type_add);
    }

    @Override
    public void initData() {
        super.initData();
        presenter = new MatchTypePresenter(context,this);
    }

    @Override
    public void getAllMatchTypeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void addMatchTypeResult(boolean isSuccess, Object object) {
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void deleteMatchTypeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
