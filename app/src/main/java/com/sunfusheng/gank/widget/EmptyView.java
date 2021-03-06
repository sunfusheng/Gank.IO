package com.sunfusheng.gank.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.widget.LoadingView.LoadingView;

/**
 * @author sunfusheng on 2018/1/20.
 */
public class EmptyView extends FrameLayout {

    private LoadingView loadingView;
    private TextView tvTitle;
    private TextView tvDetail;
    protected Button button;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.DefaultAttr);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);
        boolean showLoading = typedArray.getBoolean(R.styleable.EmptyView_showLoading, false);
        String title = typedArray.getString(R.styleable.EmptyView_title);
        String detail = typedArray.getString(R.styleable.EmptyView_detail);
        String button = typedArray.getString(R.styleable.EmptyView_button);
        typedArray.recycle();

        show(showLoading, title, detail, button, null);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, this, true);
        loadingView = findViewById(R.id.empty_view_loading);
        tvTitle = findViewById(R.id.empty_view_title);
        tvDetail = findViewById(R.id.empty_view_detail);
        button = findViewById(R.id.empty_view_button);
    }

    public void show(boolean showLoading, String titleText, String detailText, String buttonText, OnClickListener onButtonClickListener) {
        setLoadingShowing(showLoading);
        setTitleText(titleText);
        setDetailText(detailText);
        setButton(buttonText, onButtonClickListener);
        show();
    }

    public void show(boolean loading) {
        setLoadingShowing(loading);
        setTitleText(null);
        setDetailText(null);
        setButton(null, null);
        show();
    }

    public void show(String titleText, String detailText) {
        setLoadingShowing(false);
        setTitleText(titleText);
        setDetailText(detailText);
        setButton(null, null);
        show();
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
        setLoadingShowing(false);
        setTitleText(null);
        setDetailText(null);
        setButton(null, null);
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    public boolean isLoading() {
        return loadingView.getVisibility() == VISIBLE;
    }

    public void setLoadingShowing(boolean show) {
        loadingView.setVisibility(show ? VISIBLE : GONE);
    }

    public void setTitleText(String text) {
        tvTitle.setText(text);
        tvTitle.setVisibility(text != null ? VISIBLE : GONE);
    }

    public void setDetailText(String text) {
        tvDetail.setText(text);
        tvDetail.setVisibility(text != null ? VISIBLE : GONE);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setDetailColor(int color) {
        tvDetail.setTextColor(color);
    }

    public void setButton(String text, OnClickListener onClickListener) {
        button.setText(text);
        button.setVisibility(text != null ? VISIBLE : GONE);
        setButtonOnClickListener(onClickListener);
    }

    public void setButtonOnClickListener(OnClickListener onClickListener) {
        button.setOnClickListener(onClickListener);
    }
}