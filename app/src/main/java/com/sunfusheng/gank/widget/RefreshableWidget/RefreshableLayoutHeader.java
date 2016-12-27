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

    /**
     * hide footer when disable pull refresh
     */
    public void hide() {
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onStateNormal() {
        circleLoadingView.setVisibility(VISIBLE);
        tvStatus.setText(R.string.xrefreshview_header_hint_normal);
    }

    @Override
    public void onStateReady() {
        circleLoadingView.setVisibility(VISIBLE);
        tvStatus.setText(R.string.xrefreshview_header_hint_ready);
    }

    @Override
    public void onStateRefreshing() {
        circleLoadingView.setVisibility(VISIBLE);
        tvStatus.setText(R.string.xrefreshview_header_hint_loading);
    }

    @Override
    public void onStateFinish(boolean success) {
        circleLoadingView.setVisibility(GONE);
        tvStatus.setText(success ? R.string.xrefreshview_header_hint_loaded : R.string.xrefreshview_header_hint_loaded_fail);
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {

    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
