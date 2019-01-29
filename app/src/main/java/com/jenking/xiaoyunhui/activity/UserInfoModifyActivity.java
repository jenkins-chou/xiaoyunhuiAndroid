package com.jenking.xiaoyunhui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.BaseAPI;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.SchoolContract;
import com.jenking.xiaoyunhui.contacts.UserContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.dialog.CommonBottomListDialog;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.models.base.SchoolModel;
import com.jenking.xiaoyunhui.models.base.UserModel;
import com.jenking.xiaoyunhui.presenters.SchoolPresenter;
import com.jenking.xiaoyunhui.presenters.UserPresenter;
import com.jenking.xiaoyunhui.tools.AccountTool;
import com.jenking.xiaoyunhui.tools.StringUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jenking.xiaoyunhui.activity.ClassListActivity.SelectClassResutlCode;

public class UserInfoModifyActivity extends BaseActivity implements UserContract ,SchoolContract{

    private String imagePath;//本地图像url
    private String imageUrl;//服务器图像url
    private UserPresenter userPresenter;
    private SchoolPresenter schoolPresenter;
    private List<String> sexList;

    private String selectHealth = "";

    private String selectSchoolId = "";
    private String selectSchoolName = "";

    private String selectClassId = "";
    private String selectClassName = "";

    @BindView(R.id.loading)
    CommonLoading loading;
    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.input_realname)
    EditText input_realname;
    @BindView(R.id.input_sex)
    TextView input_sex;
    @BindView(R.id.input_slogan)
    EditText input_slogan;
    @BindView(R.id.input_phone)
    EditText input_phone;
    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_health)
    TextView input_health;
    @BindView(R.id.input_address)
    EditText input_address;
    @BindView(R.id.input_school)
    TextView input_school;
    @BindView(R.id.input_class)
    TextView input_class;

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    //选择头像
    @OnClick({R.id.avatar_bar,R.id.avatar,R.id.avatar_text})
    void select_avatar(){
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(UserInfoModifyActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia(false)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    //选择性别
    @OnClick({R.id.input_sex})
    void select_sex(){
        CommonBottomListDialog commonBottomListDialog = new CommonBottomListDialog(context,"请选择性别",sexList,"",false) {
            @Override
            protected void setOnItemClickListener(String value) {
                input_sex.setText(value);
            }
        };
        commonBottomListDialog.show();
    }

    //选择生日日期
    @OnClick({R.id.input_health})
    void select_health(){
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.e("date",date.getTime()/1000+"");
                selectHealth = date.getTime()/1000+"";
                input_health.setText(StringUtil.getStrTime(date.getTime()/1000+"","yyyy年MM月dd日"));
            }
        }).setTitleText("选择比赛时间")
                .setType(new boolean[]{true,true,true,false,false,false})
                .build();
        pvTime.show();
    }

    //选择school
    @OnClick({R.id.input_school})
    void select_school(){
        Intent intent = new Intent(context,SchoolListActivity.class);
        startActivityForResult(intent,SchoolListActivity.SelectSchoolResutlCode);
    }

    //选择class
    @OnClick({R.id.input_class})
    void select_class(){
        Intent intent = new Intent(context,ClassListActivity.class);
        startActivityForResult(intent, SelectClassResutlCode);
    }

    @OnClick(R.id.footer)
    void submit(){
        setLoadingEnable(true);
        if (imagePath==null||imagePath.equals("")){
            submitData();
        }else{
            uploadFile(imagePath);
        }
    }

    void uploadFile(String path){
        /**
         * 自定义实体参数类请参考:
         * 请求注解 {@link org.xutils.http.annotation.HttpRequest}
         * 请求注解处理模板接口 {@link org.xutils.http.app.ParamsBuilder}
         *
         * 需要自定义类型作为callback的泛型时, 参考:
         * 响应注解 {@link org.xutils.http.annotation.HttpResponse}
         * 响应注解处理模板接口 {@link org.xutils.http.app.ResponseParser}
         *
         * 示例: 查看 org.xutils.sample.http 包里的代码
         */
        RequestParams params = new RequestParams("http://104.194.85.83:8888/upload/uploadImg");
        //        params.setSslSocketFactory(...); // 设置ssl
        //        params.addQueryStringParameter("wd", "xUtils");
        params.setMultipart(true);
        params.addBodyParameter("uploadFile", new File(path));
        //        BaiduParams params = new BaiduParams();
        //        params.wd = "xUtils";
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        // params.setMultipart(true);
        // 上传文件方式 1
        // params.uploadFile = new File("/sdcard/test.txt");
        // 上传文件方式 2
        // params.addBodyParameter("uploadFile", new File("/sdcard/test.txt"));
        Callback.Cancelable cancelable
                = x.http().post(params,
                /**
                 * 1. callback的泛型:
                 * callback参数默认支持的泛型类型参见{@link org.xutils.http.loader.LoaderFactory},
                 * 例如: 指定泛型为File则可实现文件下载, 使用params.setSaveFilePath(path)指定文件保存的全路径.
                 * 默认支持断点续传(采用了文件锁和尾端校验续传文件的一致性).
                 * 其他常用类型可以自己在LoaderFactory中注册,
                 * 也可以使用{@link org.xutils.http.annotation.HttpResponse}
                 * 将注解HttpResponse加到自定义返回值类型上, 实现自定义ResponseParser接口来统一转换.
                 * 如果返回值是json形式, 那么利用第三方的json工具将十分容易定义自己的ResponseParser.
                 * 如示例代码{@link org.xutils.sample.http.BaiduResponse}, 可直接使用BaiduResponse作为
                 * callback的泛型.
                 *
                 * 2. callback的组合:
                 * 可以用基类或接口组合个种类的Callback, 见{@link org.xutils.common.Callback}.
                 * 例如:
                 * a. 组合使用CacheCallback将使请求检测缓存或将结果存入缓存(仅GET请求生效).
                 * b. 组合使用PrepareCallback的prepare方法将为callback提供一次后台执行耗时任务的机会,
                 * 然后将结果给onCache或onSuccess.
                 * c. 组合使用ProgressCallback将提供进度回调.
                 * ...(可参考{@link org.xutils.image.ImageLoader}
                 * 或 示例代码中的 {@link org.xutils.sample.download.DownloadCallback})
                 *
                 * 3. 请求过程拦截或记录日志: 参考 {@link org.xutils.http.app.RequestTracker}
                 *
                 * 4. 请求Header获取: 参考 {@link org.xutils.http.app.RequestInterceptListener}
                 *
                 * 5. 其他(线程池, 超时, 重定向, 重试, 代理等): 参考 {@link org.xutils.http.RequestParams}
                 *
                 **/
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("onSuccess",""+result);
                        imageUrl = result;
                        submitData();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        setLoadingEnable(false);
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        if (ex instanceof HttpException) { // 网络错误
                            HttpException httpEx = (HttpException) ex;
                            int responseCode = httpEx.getCode();
                            String responseMsg = httpEx.getMessage();
                            String errorResult = httpEx.getResult();
                            // ...
                        } else { // 其他错误
                            // ...
                        }
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                        setLoadingEnable(false);
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFinished() {
                        setLoadingEnable(false);
                    }
                });
    }

    void submitData(){
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("user_id", AccountTool.getLoginUser(context).getUser_id()+"");
        if(imageUrl==null||imageUrl.equals("")){
            params.put("user_avatar",AccountTool.getLoginUser(context).getUser_avatar()+"");
        }else{
            params.put("user_avatar",imageUrl+"");
        }

        params.put("user_realname",input_realname.getText().toString()+"");
        params.put("user_sex",input_sex.getText().toString()+"");
        params.put("user_slogan",input_slogan.getText().toString()+"");
        params.put("user_phone",input_phone.getText().toString()+"");
        params.put("user_email",input_email.getText().toString()+"");
        params.put("user_health",selectHealth+"");
        params.put("user_address",input_address.getText().toString()+"");
        params.put("user_school",selectSchoolId+"");
        params.put("user_class",selectClassId+"");

        userPresenter.updateUserInfo(params);
        Log.e("params",""+params.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_modify);
    }

    @Override
    public void initData() {
        super.initData();
        userPresenter = new UserPresenter(context,this);
        schoolPresenter = new SchoolPresenter(context,this);

        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("school_id",AccountTool.getLoginUser(context).getUser_school()+"");
        schoolPresenter.getSchoolById(params);
        sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
    }

    @Override
    public void initView() {
        super.initView();

        UserModel userModel = AccountTool.getLoginUser(context);
        if (userModel!=null){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.circleCrop();
            requestOptions.placeholder(R.mipmap.avatar2);
            requestOptions.error(R.mipmap.avatar2);
            Glide.with(context).load(BaseAPI.base_url+userModel.getUser_avatar()).apply(requestOptions).into(avatar);

            input_realname.setText(userModel.getUser_realname());
            input_sex.setText(userModel.getUser_sex());
            input_slogan.setText(userModel.getUser_slogan());
            input_phone.setText(userModel.getUser_phone());
            input_email.setText(userModel.getUser_email());
            String user_health = userModel.getUser_health();
            if (user_health!=null&&StringUtil.isNumber(user_health)){
                input_health.setText(StringUtil.getStrTime(user_health,"yyyy-MM-dd"));
            }
            input_address.setText(userModel.getUser_sex());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case SchoolListActivity.SelectSchoolResutlCode:
                if (data!=null){
                    selectSchoolId = data.getStringExtra("select_school_id");
                    selectSchoolName = data.getStringExtra("select_school_name");
                    input_school.setText(selectSchoolName);
                    Log.e("selectSchoolId",""+selectSchoolId);
                    Log.e("selectSchoolName",""+selectSchoolName);
                }
                break;
            case ClassListActivity.SelectClassResutlCode:
                if (data!=null){
                    selectClassId = data.getStringExtra("select_class_id");
                    selectClassName = data.getStringExtra("select_class_name");
                    input_class.setText(selectClassName);
                    Log.e("selectSchoolId",""+selectClassId);
                    Log.e("selectSchoolName",""+selectClassName);
                }
                break;
        }
        switch (requestCode){
            case PictureConfig.CHOOSE_REQUEST:
                // 图片、视频、音频选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size()>0){
                    imagePath = selectList.get(0).getCutPath();
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.circleCrop();
                    Glide.with(context).load(imagePath).apply(requestOptions).into(avatar);
                }else {
                    imagePath = "";
//                    RequestOptions requestOptions = new RequestOptions();
//                    requestOptions.circleCrop();
//                    requestOptions.placeholder(R.mipmap.avatar2);
//                    requestOptions.error(R.mipmap.avatar2);
//                    Glide.with(context).load(BaseAPI.base_url+userModel.getUser_avatar()).apply(requestOptions).into(avatar);
                }
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                break;
        }
    }

    void setLoadingEnable(boolean enable){
        if (loading!=null){
            loading.setVisibility(enable? View.VISIBLE:View.GONE);
        }
    }

    @Override
    public void updateUserinfoResult(boolean isSuccess, Object object) {
        setLoadingEnable(false);
        if (checkResultModel(isSuccess,object)){
            ResultModel resultModel = (ResultModel)object;
            Log.e("updateUserinfoResult",resultModel.toString());
            if (resultModel!=null&&resultModel.getData()!=null&&resultModel.getData().size()>=1){
                AccountTool.saveUser(context,resultModel.getData().get(0));
                Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void getUserinfoResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

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
                    input_school.setText(schoolModel.getSchool_name());
                }
            }
        }
    }

    @Override
    public void addSchoolResult(boolean isSuccess, Object object) {

    }
}
