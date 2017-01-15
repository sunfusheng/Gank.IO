package com.sunfusheng.gank.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.adapter.HeaderViewRecyclerAdapter;
import com.sunfusheng.gank.adapter.RecyclerListAdapter;
import com.sunfusheng.gank.base.RecyclerView.DefaultTipsHelper;
import com.sunfusheng.gank.model.IMoreData;
import com.sunfusheng.gank.widget.RecyclerViewLayout.SwipeRefreshLayout;
import com.sunfusheng.gank.widget.RecyclerViewLayout.TipsHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/12.
 */
public abstract class BaseListFragment<E extends IMoreData> extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private boolean mIsLoading;
    private TipsHelper mTipsHelper;
    private HeaderViewRecyclerAdapter mHeaderAdapter;
    private RecyclerListAdapter<E, ?> mOriginAdapter;

    private InteractionListener mInteractionListener;

    private final RefreshEventDetector mRefreshEventDetector = new RefreshEventDetector();
    private final AutoLoadEventDetector mAutoLoadEventDetector = new AutoLoadEventDetector();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mInteractionListener = createInteraction();
        mTipsHelper = createTipsHelper();
    }

    private void initView() {

        recyclerView.addOnScrollListener(mAutoLoadEventDetector);

        RecyclerView.LayoutManager layoutManager = onCreateLayoutManager();
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
        }

        mOriginAdapter = createAdapter();
        mHeaderAdapter = new HeaderViewRecyclerAdapter(mOriginAdapter);
        recyclerView.setAdapter(mHeaderAdapter);
        mHeaderAdapter.adjustSpanSize(recyclerView);


        if (allowPullToRefresh()) {
            refreshLayout.setNestedScrollingEnabled(true);
            refreshLayout.setOnRefreshListener(mRefreshEventDetector);
        } else {
            refreshLayout.setEnabled(false);
        }
    }

    protected TipsHelper createTipsHelper() {
        return new DefaultTipsHelper(this);
    }

    @NonNull
    public abstract RecyclerListAdapter createAdapter();

    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected InteractionListener createInteraction() {
        return null;
    }

    @Override
    public void onDestroyView() {
        recyclerView.removeOnScrollListener(mAutoLoadEventDetector);
        super.onDestroyView();
    }

    public HeaderViewRecyclerAdapter getHeaderAdapter() {
        return mHeaderAdapter;
    }

    public RecyclerListAdapter<E, ?> getOriginAdapter() {
        return mOriginAdapter;
    }

    public SwipeRefreshLayout getRecyclerRefreshLayout() {
        return refreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public boolean allowPullToRefresh() {
        return true;
    }

    public void refresh() {
        if (isFirstPage()) {
            mTipsHelper.showLoading(true);
        } else {
            refreshLayout.setRefreshing(true);
        }

        requestRefresh();
    }

    public boolean isFirstPage() {
        return mOriginAdapter.getItemCount() <= 0;
    }

    public class RefreshEventDetector implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            requestRefresh();
        }
    }

    public class AutoLoadEventDetector extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
            RecyclerView.LayoutManager manager = view.getLayoutManager();
            if (manager.getChildCount() > 0) {
                int count = manager.getItemCount();
                int last = ((RecyclerView.LayoutParams) manager
                        .getChildAt(manager.getChildCount() - 1).getLayoutParams()).getViewAdapterPosition();

                if (last == count - 1 && !mIsLoading && mInteractionListener != null) {
                    requestMore();
                }
            }
        }
    }

    private void requestRefresh() {
        if (mInteractionListener != null && !mIsLoading) {
            mIsLoading = true;
            mInteractionListener.requestRefresh();
        }
    }

    private void requestMore() {
        if (mInteractionListener != null && mInteractionListener.hasMore() && !mIsLoading) {
            mIsLoading = true;
            mInteractionListener.requestMore();
        }
    }

    public abstract class InteractionListener {
        public void requestRefresh() {
            requestComplete();

            if (mOriginAdapter.isEmpty()) {
                mTipsHelper.showEmpty();
            } else if (hasMore()) {
                mTipsHelper.showHasMore();
            } else {
                mTipsHelper.hideHasMore();
            }
        }

        public void requestMore() {
            requestComplete();
        }

        public void requestFailure() {
            requestComplete();
            mTipsHelper.showError(isFirstPage(), new Exception("net error"));
        }

        protected void requestComplete() {
            mIsLoading = false;

            if (refreshLayout != null) {
                refreshLayout.setRefreshing(false);
            }

            mTipsHelper.hideError();
            mTipsHelper.hideEmpty();
            mTipsHelper.hideLoading();
        }

        protected boolean hasMore() {
            return mOriginAdapter.getItem(mOriginAdapter.getItemCount() - 1).hasMore();
        }
    }
}
