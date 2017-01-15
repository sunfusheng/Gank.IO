package com.sunfusheng.gank.widget.RecyclerViewLayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.sunfusheng.gank.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/15.
 */
public class RecyclerViewLayout extends FrameLayout {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerRefreshLayout)
    SwipeRefreshLayout recyclerRefreshLayout;
    @BindView(R.id.error_view_stub)
    ViewStub errorViewStub;
    @BindView(R.id.empty_view_stub)
    ViewStub emptyViewStub;
    View loadingViewLayout;

    public RecyclerViewLayout(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_recyclerview, this);
        ButterKnife.bind(this, view);
        loadingViewLayout = view.findViewById(R.id.loading_view_layout);
    }
}
