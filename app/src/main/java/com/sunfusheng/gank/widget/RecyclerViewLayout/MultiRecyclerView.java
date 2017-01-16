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
import com.sunfusheng.gank.widget.SwipeRefreshLayout.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/15.
 */
public class MultiRecyclerView extends FrameLayout {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerRefreshLayout)
    SwipeRefreshLayout recyclerRefreshLayout;
    @BindView(R.id.error_stub)
    ViewStub errorStub;
    @BindView(R.id.empty_stub)
    ViewStub emptyStub;

    View loadingView;
    View errorView;
    View emptyView;

    private OnClickListener errorViewClickListener;
    private OnClickListener emptyViewClickListener;

    private LoadingStateDelegate loadingStateDelegate;

    public MultiRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public MultiRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_recyclerview, this);
        ButterKnife.bind(this, view);

        loadingView = ButterKnife.findById(view, R.id.loading_view);
        loadingStateDelegate = new LoadingStateDelegate(recyclerView, loadingView, errorStub, emptyStub);
        loadingStateDelegate.setViewState(LoadingStateDelegate.LOADING_STATE.STATE_LOADING);
    }

    public void setLoadingState(@LoadingStateDelegate.LOADING_STATE int state) {
        if (state == LoadingStateDelegate.LOADING_STATE.STATE_LOAD_FAILED) {
            if (recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() > 0) {
                loadingStateDelegate.setViewState(LoadingStateDelegate.LOADING_STATE.STATE_LOAD_SUCCEED);
            } else {
                emptyView = loadingStateDelegate.setViewState(LoadingStateDelegate.LOADING_STATE.STATE_LOAD_FAILED);
                setErrorViewClickListener(errorViewClickListener);
            }
        } else if (state == LoadingStateDelegate.LOADING_STATE.STATE_LOAD_EMPTY) {
            emptyView = loadingStateDelegate.setViewState(LoadingStateDelegate.LOADING_STATE.STATE_LOAD_EMPTY);
            setEmptyViewClickListener(emptyViewClickListener);
        } else {
            loadingStateDelegate.setViewState(state);
        }
    }

    public void setErrorViewClickListener(OnClickListener errorLayoutClickListener) {
        this.errorViewClickListener = errorLayoutClickListener;
        if (errorView != null) {
            errorView.setOnClickListener(errorLayoutClickListener);
        }
    }

    public void setEmptyViewClickListener(OnClickListener emptyViewClickListener) {
        this.emptyViewClickListener = emptyViewClickListener;
        if (emptyView != null) {
            emptyView.setOnClickListener(emptyViewClickListener);
        }
    }

    public void setEmptyView(int layoutId) {
        if (emptyStub != null && layoutId != -1) {
            emptyStub.setLayoutResource(layoutId);
        }
    }

    public void setErrorView(int layoutId) {
        if (errorStub != null && layoutId != -1) {
            errorStub.setLayoutResource(layoutId);
        }
    }
}
