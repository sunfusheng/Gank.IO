package com.sunfusheng.gank.widget.RefreshableWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.widget.CircleLoadingView;
import com.sunfusheng.gank.widget.RefreshableWidget.callback.IHeaderCallBack;

public class RefreshableLayoutHeader extends LinearLayout implements IHeaderCallBack {

    private CircleLoadingView circleLoadingView;
    private TextView tvStatus;

    public RefreshableLayoutHeader(Context context) {
        super(context);
        initView(context);
    }

    public RefreshableLayoutHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        ViewGroup headerLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_refreshable_header, this);
        circleLoadingView = (CircleLoadingView) headerLayout.findViewById(R.id.circleLoadingView);
        tvStatus = (TextView) headerLayout.findViewById(R.id.tv_status);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateNormal() {
        isLoading = false;
        circleLoadingView.setLoadingOffset(isLoading, mOffsetY);
        tvStatus.setText(R.string.refreshablelayout_header_hint_normal);
    }

    @Override
    public void onStateReady() {
        isLoading = false;
        circleLoadingView.setLoadingOffset(isLoading, mOffsetY);
        tvStatus.setText(R.string.refreshablelayout_header_hint_ready);
    }

    @Override
    public void onStateRefreshing() {
        isLoading = true;
        circleLoadingView.setLoadingOffset(isLoading, mOffsetY);
        circleLoadingView.invalidate();
        tvStatus.setText(R.string.refreshablelayout_header_hint_refreshing);
    }

    @Override
    public void onStateFinish(boolean success) {
        isLoading = false;
        circleLoadingView.setLoadingOffset(isLoading, mOffsetY);
        tvStatus.setText(success ? R.string.refreshablelayout_header_hint_loaded_success : R.string.refreshablelayout_header_hint_loaded_fail);
    }

    private boolean isLoading = true;
    private int mOffsetY;

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {
        mOffsetY = offsetY;
        circleLoadingView.setLoadingOffset(isLoading, offsetY);
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
