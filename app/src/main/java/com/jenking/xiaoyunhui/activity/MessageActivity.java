package com.jenking.xiaoyunhui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;
import com.jenking.xiaoyunhui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private List<String> datas;
    private BaseRecyclerAdapter baseRecyclerAdapter;

    @OnClick(R.id.back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    @Override
    public void initData() {
        super.initData();
        datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        baseRecyclerAdapter = new BaseRecyclerAdapter(context,datas,R.layout.activity_message_item) {
            @Override
            protected void convert(BaseViewHolder helper, Object item) {

            }
        };

        baseRecyclerAdapter.openLoadAnimation(false);
        recyclerView.setAdapter(baseRecyclerAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));


    }
}
