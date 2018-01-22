package com.sunfusheng.gank.widget.RecyclerViewWrapper;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.widget.EmptyView;
import com.sunfusheng.gank.widget.SwipeRefreshLayout.DragDistanceConverterEg;
import com.sunfusheng.gank.widget.SwipeRefreshLayout.RefreshView;
import com.sunfusheng.gank.widget.SwipeRefreshLayout.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author by sunfusheng on 2017/1/15.
 */
public class RecyclerViewWrapper extends FrameLayout {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.loading_view)
    EmptyView loadingView;
    @BindView(R.id.error_view)
    EmptyView errorView;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    @BindView(R.id.loading_more_view)
    RefreshView loadingMoreView;

    private OnClickListener errorViewClickListener;
    private OnClickListener emptyViewClickListener;
    private OnLoadListener onLoadListener;
    private OnScrollListener onScrollListener;

    private LoadingStateDelegate loadingStateDelegate;

    private LinearLayoutManager linearLayoutManager;
    private MultiTypeAdapter multiTypeAdapter;
    private int lastItemCount;
    private int firstVisibleItemPosition = RecyclerView.NO_POSITION;
    private int lastVisibleItemPosition = RecyclerView.NO_POSITION;

    public RecyclerViewWrapper(@NonNull Context context) {
        this(context, null);
    }

    public RecyclerViewWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.DefaultAttr);
    }

    public RecyclerViewWrapper(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener();
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_recyclerview, this);
        ButterKnife.bind(this, view);

        loadingView = view.findViewById(R.id.loading_view);
        loadingStateDelegate = new LoadingStateDelegate(swipeRefreshLayout, loadingView, errorView, emptyView);

        swipeRefreshLayout.setDragDistanceConverter(new DragDistanceConverterEg());
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        multiTypeAdapter = new MultiTypeAdapter();
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(multiTypeAdapter);
        alphaInAnimationAdapter.setDuration(500);
        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(alphaInAnimationAdapter);
        slideInBottomAnimationAdapter.setDuration(500);
        recyclerView.setAdapter(slideInBottomAnimationAdapter);
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (onLoadListener != null) {
                lastItemCount = 0;
                onLoadListener.onLoad();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    int itemCount = linearLayoutManager.getItemCount();
                    int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                    if (itemCount != lastItemCount && lastPosition == itemCount - 1 && onLoadListener != null) {
                        lastItemCount = itemCount;
                        onLoadListener.onLoadMore();
                        loadingMoreView.startLoading();
                    }
                }

                if (onScrollListener != null) {
                    onScrollListener.onScrolled(recyclerView, dx, dy);
                }
            }
        });

        errorView.setButtonOnClickListener(v -> {
            if (onLoadListener != null) {
                setLoadingState(LoadingStateDelegate.STATE.LOADING);
                lastItemCount = 0;
                onLoadListener.onLoad();
            }
        });
    }

    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull ItemViewBinder<T, ?> binder) {
        multiTypeAdapter.register(clazz, binder);
    }

    public void setData(@Nullable List<?> items) {
        if (items != null) {
            multiTypeAdapter.setItems(items);
            multiTypeAdapter.notifyDataSetChanged();
        }
    }

    public List<?> getData() {
        return multiTypeAdapter.getItems();
    }

    public void notifyDataSetChanged() {
        multiTypeAdapter.notifyDataSetChanged();
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.addItemDecoration(decor);
    }

    public void setLoadingState(@LoadingStateDelegate.STATE int state) {
        if (loadingMoreView.getVisibility() == VISIBLE) {
            loadingMoreView.stopLoading();
        }
        swipeRefreshLayout.setRefreshing(false);
        if (state == LoadingStateDelegate.STATE.ERROR) {
            if (recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() > 0) {
                loadingStateDelegate.setLoadingState(LoadingStateDelegate.STATE.SUCCEED);
            } else {
                loadingStateDelegate.setLoadingState(LoadingStateDelegate.STATE.ERROR);
                setErrorViewClickListener(errorViewClickListener);
            }
        } else if (state == LoadingStateDelegate.STATE.EMPTY) {
            loadingStateDelegate.setLoadingState(LoadingStateDelegate.STATE.EMPTY);
            setEmptyViewClickListener(emptyViewClickListener);
        } else {
            loadingStateDelegate.setLoadingState(state);
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

    public int getItemCount() {
        return multiTypeAdapter.getItemCount();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }

    public MultiTypeAdapter getMultiTypeAdapter() {
        return multiTypeAdapter;
    }

    public int getFirstVisibleItemPosition() {
        return firstVisibleItemPosition;
    }

    public int getLastVisibleItemPosition() {
        return lastVisibleItemPosition;
    }

    public Object getFirstVisibleItem() {
        if (multiTypeAdapter.getItemCount() <= 0) return null;
        if (firstVisibleItemPosition == RecyclerView.NO_POSITION) return null;
        if (firstVisibleItemPosition >= multiTypeAdapter.getItemCount()) return null;
        return multiTypeAdapter.getItems().get(firstVisibleItemPosition);
    }

    public Object getLastVisibleItem() {
        if (multiTypeAdapter.getItemCount() <= 0) return null;
        if (lastVisibleItemPosition == RecyclerView.NO_POSITION) return null;
        if (lastVisibleItemPosition >= multiTypeAdapter.getItemCount()) return null;
        return multiTypeAdapter.getItems().get(lastVisibleItemPosition);
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnLoadListener {
        void onLoad();

        void onLoadMore();
    }

    public interface OnScrollListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState);

        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }
}
