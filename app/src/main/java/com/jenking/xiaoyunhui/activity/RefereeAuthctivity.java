package com.jenking.xiaoyunhui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.RefereeContract;
import com.jenking.xiaoyunhui.dialog.CommonTipsDialog;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.RefereePresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;

import java.util.Map;

import butterknife.OnClick;

public class RefereeAuthctivity extends BaseActivity implements RefereeContract {

    private RefereePresenter refereePresenter;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    //提交申请
    @OnClick({R.id.footer})
    void footer(){
        if (refereePresenter!=null){
            Map<String,String> params = RequestService.getBaseParams(context);
            params.put("user_id",AccountTool.getLoginUser(context).getUser_id());
            params.put("referee_status","1");
            params.put("referee_manager","");
            refereePresenter.addReferee(params);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_authctivity);
    }

    @Override
    public void initData() {
        super.initData();
        refereePresenter = new RefereePresenter(context,this);
    }

    @Override
    public void getAllRefereeResult(boolean isSuccess, Object object) {

    }

    @Override
    public void addRefereeResult(boolean isSuccess, Object object) {
        if (isSuccess){
            if (object!=null){
                ResultModel resultModel = (ResultModel) object;
                Log.e("addRefereeResult",resultModel.toString());
                if (resultModel!=null&&resultModel.getStatus()!=null){
                    switch (resultModel.getStatus()){
                        case "200":
                            CommonTipsDialog.create(context,"温馨提示","申请成功",false)
                                    .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                                        @Override
                                        public void cancel() {

                                        }

                                        @Override
                                        public void confirm() {
                                            finish();
                                        }
                                    });
                            break;
                        case "201":
                            CommonTipsDialog.create(context,"温馨提示","您已经申请过了",false)
                                    .setOnClickListener(new CommonTipsDialog.OnClickListener() {
                                        @Override
                                        public void cancel() {

                                        }

                                        @Override
                                        public void confirm() {
                                            finish();
                                        }
                                    });
                            break;
                    }
                }
            }
        }else{
            CommonTipsDialog.showTip(context,"温馨提示","网络超时",false);
        }
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
