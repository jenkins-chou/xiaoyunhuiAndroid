package com.jenking.xiaoyunhui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.github.library.listener.OnRecyclerItemClickListener;
import com.jenking.xiaoyunhui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class CommonBottomListDialog extends Dialog {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView titleTv;

    private BaseRecyclerAdapter adapter;
    private String title = "";
    private String selectItem = "";
    private List<String> datas;

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable = false;//控制返回键是否dismiss
    private Context context;
    private Unbinder unbinder;

    public CommonBottomListDialog(Context context, String title, List<String> datas, String selectItem, boolean isCancelable) {
        super(context, R.style.CommonDialogStyle);
        this.context = context;
        this.title = title;
        this.datas = datas;
        this.selectItem = selectItem;
        this.iscancelable = isCancelable;
    }

    @OnClick(R.id.close)
    void close(){
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = LayoutInflater.from(context).inflate(R.layout.common_bottom_list_dialog,null,false);
        unbinder = ButterKnife.bind(this,rootView);

        titleTv.setText(title);

        if (datas==null){
            datas = new ArrayList<>();
            datas.add("none");
        }
        adapter = new BaseRecyclerAdapter<String>(context,datas,R.layout.common_bototm_list_item) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.item_text,item+"");
//                if (selectItem!=null&&selectItem.equals(item)){
//                    helper.getView(R.id.item_icon).setVisibility(View.VISIBLE);
//                }
            }
        };

        adapter.setOnRecyclerItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                ImageView item_icon = view.findViewById(R.id.item_icon);
//                item_icon.setVisibility(View.VISIBLE);
                if (datas!=null){
                    setOnItemClickListener(datas.get(position));
                    dismiss();
                }
            }
        });

        adapter.openLoadAnimation(false);//关闭加载动画

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        recyclerView.setAdapter(adapter);



        setContentView(rootView);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss



        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    protected abstract void setOnItemClickListener(String value);
}