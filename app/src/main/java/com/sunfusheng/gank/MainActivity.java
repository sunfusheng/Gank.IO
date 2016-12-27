package com.sunfusheng.gank;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sunfusheng.gank.adapter.TestAdapter;
import com.sunfusheng.gank.widget.CircleLoadingView;
import com.sunfusheng.gank.widget.RefreshableWidget.RefreshableLayout;
import com.sunfusheng.gank.widget.RefreshableWidget.RefreshableLayoutFooter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RefreshableLayout.RefreshableLayoutListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.monIndicator)
    CircleLoadingView monIndicator;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshableLayout)
    RefreshableLayout refreshableLayout;

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        for (int i=1; i<=15; i++) {
            mList.add("青蛙"+i+"号");
        }
    }

    private void initView() {
        TestAdapter adapter = new TestAdapter(this, mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 静默加载模式不能设置FooterView
        recyclerView.setAdapter(adapter);

        refreshableLayout.setPinnedTime(1000); // 设置刷新完成以后，HeaderView固定的时间
        refreshableLayout.setMoveForHorizontal(true);
        refreshableLayout.setPullLoadEnable(true);
        refreshableLayout.setAutoLoadMore(true);
        adapter.setCustomLoadMoreView(new RefreshableLayoutFooter(this));
        refreshableLayout.enableReleaseToLoadMore(true);
        refreshableLayout.enableRecyclerViewPullUp(true);
        refreshableLayout.enablePullUpWhenLoadCompleted(true);
    }

    private void initListener() {
        refreshableLayout.setRefreshableLayoutListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> refreshableLayout.stopRefresh(), 1000);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        new Handler().postDelayed(() -> refreshableLayout.stopLoadMore(), 1000);
    }

}
