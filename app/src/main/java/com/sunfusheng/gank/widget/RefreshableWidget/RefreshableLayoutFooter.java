package com.sunfusheng.gank.widget.RefreshableWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.widget.RefreshableWidget.callback.IFooterCallBack;

public class RefreshableLayoutFooter extends LinearLayout implements IFooterCallBack {

    private Context mContext;
    private View rlRootView;
    private ProgressBar progressBar;
    private TextView tvStatus;
    private boolean isShowing = true;

    public RefreshableLayoutFooter(Context context) {
        super(context);
        initView(context);
    }

    public RefreshableLayoutFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        ViewGroup view = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.layout_refreshable_footer, this);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        rlRootView = view.findViewById(R.id.rl_rootView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        tvStatus = (TextView) view.findViewById(R.id.tv_status);
    }

    @Override
    public void callWhenNotAutoLoadMore(final RefreshableLayout refreshableLayout) {
        progressBar.setVisibility(GONE);
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setClickable(true);
        tvStatus.setText(R.string.xrefreshview_footer_hint_click);
        tvStatus.setOnClickListener(v -> refreshableLayout.notifyLoadMore());
    }

    @Override
    public void onStateReady() {
        progressBar.setVisibility(GONE);
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setClickable(true);
        tvStatus.setText(R.string.xrefreshview_footer_hint_click);
    }

    @Override
    public void onStateRefreshing() {
        progressBar.setVisibility(VISIBLE);
        tvStatus.setVisibility(GONE);
        tvStatus.setClickable(false);
        show(true);
    }

    @Override
    public void onReleaseToLoadMore() {
        progressBar.setVisibility(GONE);
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setClickable(false);
        tvStatus.setText(R.string.xrefreshview_footer_hint_release);
    }

    @Override
    public void onStateFinish(boolean hideFooter) {
        progressBar.setVisibility(GONE);
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setClickable(false);
        if (hideFooter) {
            tvStatus.setText(R.string.xrefreshview_footer_hint_normal);
        } else {
            //处理数据加载失败时ui显示的逻辑，也可以不处理，看自己的需求
            tvStatus.setText(R.string.xrefreshview_footer_hint_fail);
        }
    }

    @Override
    public void onStateComplete() {
        progressBar.setVisibility(GONE);
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setClickable(false);
        tvStatus.setText(R.string.xrefreshview_footer_hint_complete);
    }

    @Override
    public void show(final boolean show) {
        if (show == isShowing) return;
        isShowing = show;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rlRootView.getLayoutParams();
        lp.height = show ? LayoutParams.WRAP_CONTENT : 0;
        rlRootView.setLayoutParams(lp);
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public int getFooterHeight() {
        return getMeasuredHeight();
    }
}
