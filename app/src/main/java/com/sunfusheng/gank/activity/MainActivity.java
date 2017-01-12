package com.sunfusheng.gank.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.fragment.TestFragment;
import com.sunfusheng.gank.adapter.TestAdapter;
import com.sunfusheng.gank.base.BaseActivity;
import com.sunfusheng.gank.widget.CircleLoadingView;
import com.sunfusheng.gank.widget.RefreshableWidget.RefreshableLayout;
import com.sunfusheng.gank.widget.RefreshableWidget.RefreshableLayoutFooter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RefreshableLayout.RefreshableLayoutListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.circleLoadingView)
    CircleLoadingView circleLoadingView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshableLayout)
    RefreshableLayout refreshableLayout;

    private List<String> mList = new ArrayList<>();
    private int count = 15;
    private TestAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TestFragment())
                .commit();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            mList.add("青蛙" + i + "号");
        }
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TestAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);

        refreshableLayout.setPinnedTime(1000);
        refreshableLayout.setMoveForHorizontal(true);
        refreshableLayout.setPullLoadEnable(true);
        refreshableLayout.setAutoLoadMore(true);
        mAdapter.setCustomLoadMoreView(new RefreshableLayoutFooter(this));
        refreshableLayout.enableReleaseToLoadMore(true);
        refreshableLayout.enableRecyclerViewPullUp(true);
        refreshableLayout.enablePullUpWhenLoadCompleted(true);
    }

    private void initListener() {
        refreshableLayout.setRefreshableLayoutListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            count = 15;
            initData();
            mAdapter.setData(mList);
            refreshableLayout.stopRefresh();
        }, 1000);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        new Handler().postDelayed(() -> {
            count += 5;
            initData();
            mAdapter.setData(mList);
            refreshableLayout.stopLoadMore();
        }, 1000);
    }

}
