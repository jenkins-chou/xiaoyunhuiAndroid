package com.jenking.xiaoyunhui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jenking.xiaoyunhui.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CommonTipsDialog extends Dialog {

    private Context context;
    private String title;
    private final String message;
    private Unbinder unbinder;
    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private OnClickListener onClickListener;

    private int msgStart = 0;
    private int msgEnd = 0;

    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.title)
    TextView titleTv;
    @BindView(R.id.message)
    TextView messageTv;

    @BindView(R.id.top_line)
    View top_line;
    @BindView(R.id.bottom_line)
    View bottom_line;
    @BindView(R.id.button_bar)
    LinearLayout button_bar;

    @BindView(R.id.confirm)
    TextView confirm;

    @BindView(R.id.cancel)
    TextView cancel;
    private String confirm_str = "确定";
    private String cancel_str = "取消";

    @OnClick(R.id.close)
    void close(){
        dismiss();
    }

    @OnClick(R.id.cancel)
    void click_cancel(){
        dismiss();
        if (onClickListener!=null){
            onClickListener.cancel();
        }
    }

    @OnClick(R.id.confirm)
    void click_confirm(){
        dismiss();
        if (onClickListener!=null){
            onClickListener.confirm();
        }
    }

    public CommonTipsDialog(Context context, String title, String message, boolean iscancelable) {
        super(context, R.style.CommonDialogStyle);
        this.context = context;
        this.title = title;
        this.message = message;
        this.iscancelable = iscancelable;
    }

    public CommonTipsDialog(Context context, String title, String message, int msgStart, int msgEnd, boolean iscancelable) {
        super(context, R.style.CommonDialogStyle);
        this.context = context;
        this.title = title;
        this.message = message;
        this.msgStart = msgStart;
        this.msgEnd = msgEnd;
        this.iscancelable = iscancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initData();
        initSize();
        initView();
    }

    //初始化数据
    public void initData() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.common_tips_dialog, null);
        unbinder = ButterKnife.bind(this,view);
        setContentView(view);
    }

    //初始化大小
    public void initSize() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.7); // 宽度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        setCancelable(iscancelable);//点击外部可不可以dismiss
    }

    void initView(){
        titleTv.setText(title);
        confirm.setText(confirm_str);
        cancel.setText(cancel_str);
        if (msgStart!=0&&msgEnd!=0){
            setMessage(message,msgStart,msgEnd);
        }else{
            messageTv.setText(message);
        }

        if (onClickListener!=null){
            bottom_line.setVisibility(View.VISIBLE);
            button_bar.setVisibility(View.VISIBLE);
            close.setVisibility(View.GONE);
            top_line.setVisibility(View.GONE);
        }
    }

    public CommonTipsDialog setConfirmButton(String string){
        this.confirm_str = string;
        return this;
    }

    public CommonTipsDialog setCancelButton(String string){
        this.cancel_str = string;
        return this;
    }

    public CommonTipsDialog setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        return this;
    }
    public static CommonTipsDialog create(Context context, String title, String message, boolean iscancelable){
        final CommonTipsDialog dialog = new CommonTipsDialog(context,title,message,iscancelable);
        return dialog;
    }

    public static CommonTipsDialog create(Context context, String title, String message, int msgStart, int msgEnd, boolean iscancelable){
        final CommonTipsDialog dialog = new CommonTipsDialog(context,title,message,msgStart,msgEnd,iscancelable);
        return dialog;
    }

    //设置messgae
    public CommonTipsDialog setMessage(String message, int start, int end){
        SpannableString spannableString = new SpannableString(message);
        //设置颜色
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7C0A")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体大小，true表示前面的字体大小20单位为dip
        spannableString.setSpan(new AbsoluteSizeSpan(14, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体，BOLD为粗体
//        spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        messageTv = findViewById(R.id.message);
        messageTv.setText(spannableString);
        return this;
    }

    public static void showTip(Context context, String title, String message, boolean iscancelable){
        CommonTipsDialog.create(context,title,message,iscancelable).setOnClickListener(new OnClickListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {

            }
        }).show();
    }

    public static void show(Context context, String title, String message, boolean iscancelable){
        CommonTipsDialog.create(context,title,message,iscancelable).setOnClickListener(new OnClickListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void confirm() {

            }
        }).show();
    }

    public interface OnClickListener{
        void cancel();
        void confirm();
    }

}