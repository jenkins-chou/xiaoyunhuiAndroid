package com.jenking.xiaoyunhui.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.github.library.BaseRecyclerAdapter;
import com.google.gson.Gson;
import com.jenking.xiaoyunhui.R;
import com.jenking.xiaoyunhui.api.RequestService;
import com.jenking.xiaoyunhui.contacts.MatchContract;
import com.jenking.xiaoyunhui.customui.CommonLoading;
import com.jenking.xiaoyunhui.models.base.ImageUrlModel;
import com.jenking.xiaoyunhui.models.base.ResultModel;
import com.jenking.xiaoyunhui.presenters.MatchPresenter;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jenking.xiaoyunhui.activity.SchoolListActivity.SelectSchoolResutlCode;

public class CreateMatchActivity extends BaseActivity implements MatchContract {

    private Unbinder unbinder;
    private MatchPresenter matchPresenter;

    //data stack
    private String matchTitle;//比赛标题
    private String matchAbstract;//比赛简要说明
    private String selectMatchTypeId;//比赛类型id
    private String selectMatchTypeName;//比赛类型名称
    private String matchAthletesNum;//比赛运动员个数
    private String selectRefereeId;//比赛类型id
    private String selectRefereeName;//比赛类型名称
    private String matchDetail;//比赛要求
    private String matchTime;//比赛时间
    private String matchImagePath;//首页图像
    private String matchImageUrl;//图像在服务器中的url地址
    private String matchAddress;//比赛地点
    private String selectSchoolId;//主办方id
    private String selectSchoolName;//主板方名称

    @BindView(R.id.input_match_title)
    EditText input_match_title;//输入标题

    @BindView(R.id.input_match_abstract)
    EditText input_match_abstract;//输入简要说明

    @BindView(R.id.select_match_type)
    TextView select_match_type;//选择比赛类型

    @BindView(R.id.input_match_number)
    EditText input_match_number;//输入比赛人数

    @BindView(R.id.select_referee)
    TextView select_referee;//选择裁判员

    @BindView(R.id.input_match_detail)
    EditText input_match_detail;//输入比赛要求

    @BindView(R.id.select_match_time)
    TextView select_match_time;//选择比赛时间

    @BindView(R.id.input_match_address)
    EditText input_match_address;//输入比赛地点

    @BindView(R.id.select_match_organizer)
    TextView select_match_organizer;//主办方

    @BindView(R.id.select_match_img)
    ImageView select_match_img;

    @BindView(R.id.loading)
    CommonLoading loading;

    @OnClick({R.id.back})
    void back(){
        finish();
    }

    @OnClick(R.id.footer)
    void onClick(){
        checkInfoAndSubmit();
    }

    @OnClick(R.id.select_referee)
    void select_referee(){
        Intent intent = new Intent(context,RefereeListActivity.class);
        startActivityForResult(intent,RefereeListActivity.SelectRefereeResultId);
    }

    @OnClick(R.id.select_match_type)
    void select_match_type(){
        Intent intent = new Intent(context,MatchTypeListActivity.class);
        startActivityForResult(intent,MatchTypeListActivity.SelectTypeResultCode);
    }

    @OnClick(R.id.select_match_time)
    void setSelect_match_time(){
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.e("date",date.getTime()/1000+"");
                matchTime = date.getTime()/1000+"";
                select_match_time.setText(StringUtil.getStrTime(date.getTime()/1000+"","yyyy年MM月dd日 HH时mm分ss秒"));
            }
        }).setTitleText("选择比赛时间")
                .setType(new boolean[]{true,true,true,true,true,true})
                .build();
        pvTime.show();
    }

    @OnClick(R.id.select_match_organizer)
    void select_match_organizer(){
        Intent intent = new Intent(context,SchoolListActivity.class);
        startActivityForResult(intent,SchoolListActivity.SelectSchoolResutlCode);
    }



    @OnClick(R.id.select_match_img)
    void setSelectMatchImg(){
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(CreateMatchActivity.this)
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
                .withAspectRatio(16,9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
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
                        matchImageUrl = result;
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
        // cancelable.cancel(); // 取消请求
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        matchPresenter = new MatchPresenter(context,this);
    }

    void setLoadingEnable(boolean enable){
        if (loading!=null){
            loading.setVisibility(enable?View.VISIBLE:View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("resultCode",""+resultCode);
        switch (resultCode){
            case MatchTypeListActivity.SelectTypeResultCode:
                if (data!=null){
                    selectMatchTypeId = data.getStringExtra("match_type_id");
                    selectMatchTypeName = data.getStringExtra("match_type_name");
                    select_match_type.setText(selectMatchTypeName);
                }
                break;
            case RefereeListActivity.SelectRefereeResultId:
                if (data!=null){
                    selectRefereeId =data.getStringExtra("select_referee_id");
                    selectRefereeName =data.getStringExtra("select_referee_name");
                    select_referee.setText(selectRefereeName);
//                    select_referee.setText("哈哈哈哈哈");
                    Log.e("selectRefereeId",""+selectRefereeId);
                    Log.e("selectRefereeName",""+selectRefereeName);
                }
                break;
            case SchoolListActivity.SelectSchoolResutlCode:
                if (data!=null){
                    selectSchoolId = data.getStringExtra("select_school_id");
                    selectSchoolName = data.getStringExtra("select_school_name");
                    select_match_organizer.setText(selectSchoolName);
                    Log.e("selectSchoolId",""+selectSchoolId);
                    Log.e("selectSchoolName",""+selectSchoolName);
                }
                break;
        }

        switch (requestCode){
            case PictureConfig.CHOOSE_REQUEST:
                // 图片、视频、音频选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size()>0){
                    matchImagePath = selectList.get(0).getCutPath();
                    Glide.with(context).load(matchImagePath).into(select_match_img);
                }else {
                    matchImagePath = "";
                    Glide.with(context).load(R.mipmap.img_add).into(select_match_img);
                }
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                break;
        }
    }

    void checkInfoAndSubmit(){
        matchTitle = input_match_title.getText().toString();
        matchAbstract = input_match_abstract.getText().toString();
        matchAthletesNum = input_match_number.getText().toString();
        matchDetail = input_match_detail.getText().toString();
        matchAddress = input_match_address.getText().toString();
        if (!StringUtil.isNotEmpty(matchTitle)){
            Toast.makeText(context, "请输入标题", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(matchAbstract)){
            Toast.makeText(context, "请输入简要说明", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(selectMatchTypeId)){
            Toast.makeText(context, "请选择类型", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(matchAthletesNum)){
            Toast.makeText(context, "请输入人数", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(selectRefereeId)){
            Toast.makeText(context, "请选择裁判员", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(matchDetail)){
            Toast.makeText(context, "请输入要求", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(matchTime)){
            Toast.makeText(context, "请选择比赛时间", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(matchAddress)){
            Toast.makeText(context, "请输入比赛地点", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(matchImagePath)){
            Toast.makeText(context, "请选择宣传图像", Toast.LENGTH_SHORT).show();
        }else if (!StringUtil.isNotEmpty(selectSchoolId)){
            Toast.makeText(context, "请选择主办方", Toast.LENGTH_SHORT).show();
        }
        else{
            setLoadingEnable(true);
            uploadFile(matchImagePath);

        }
    }

    void submitData(){
        Map<String,String> params = RequestService.getBaseParams(context);
        params.put("match_title",matchTitle);
        params.put("match_time",matchTime);
        params.put("match_create_time",StringUtil.getTime());
        params.put("match_referee_id",selectRefereeId);
        params.put("match_type",selectMatchTypeId);
        params.put("match_manager",AccountTool.getLoginUser(context)!=null?AccountTool.getLoginUser(context).getUser_id()+"":"");
        params.put("match_abstract",matchAbstract);
        params.put("match_detail",matchDetail);
        params.put("match_athletes_num",matchAthletesNum);
        params.put("match_status","1");
        params.put("match_organizers",selectSchoolId);
        params.put("match_address",matchAddress);
        params.put("match_img",matchImageUrl);
        Log.e("checkInfoAndSubmit",params.toString());
        matchPresenter.addMatch(params);
    }

    @Override
    public void getAllMatchResult(boolean isSuccess, Object object) {

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
        setLoadingEnable(false);
        if (checkResultModel(isSuccess,object)){
            Toast.makeText(context, "发布比赛成功", Toast.LENGTH_SHORT).show();
            setLoadingEnable(false);
            finish();
        }
    }

    @Override
    public void addUserMatchResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getUserMatchByMatchIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByUserIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void getMatchByRefereeIdResult(boolean isSuccess, Object object) {

    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void failed(Object object) {

    }
}
