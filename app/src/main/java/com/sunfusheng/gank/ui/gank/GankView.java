package com.sunfusheng.gank.ui.gank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.gank.GankApp;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.ui.PhotoViewsActivity;
import com.sunfusheng.gank.util.DateUtil;
import com.sunfusheng.gank.widget.GildeImageView.GlideImageView;
import com.sunfusheng.gank.widget.RecyclerViewLayout.LoadingStateDelegate;
import com.sunfusheng.gank.widget.RecyclerViewLayout.MultiTypeRecyclerView;
import com.sunfusheng.gank.widget.SwipeRefreshLayout.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankView extends FrameLayout implements GankContract.View,
        MultiTypeRecyclerView.OnRequestListener,
        MultiTypeRecyclerView.OnScrollListener,
        SwipeRefreshLayout.OnDragOffsetListener {

    @BindView(R.id.multiTypeRecyclerView)
    MultiTypeRecyclerView multiTypeRecyclerView;
    @BindView(R.id.giv_girl)
    GlideImageView givGirl;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_girl)
    RelativeLayout rlGirl;

    private Context mContext;
    private GankContract.Presenter mPresenter;
    private int lastPos = -1;
    private GankItemGirl curGirl;

    public GankView(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onAttach() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_gank, this);
        ButterKnife.bind(this, view);

        tvTime.setTypeface(GankApp.songTi);
        rlGirl.setVisibility(INVISIBLE);
        multiTypeRecyclerView.setOnRequestListener(this);
        multiTypeRecyclerView.setOnScrollListener(this);
        multiTypeRecyclerView.getSwipeRefreshLayout().setOnDragOffsetListener(this);
        givGirl.setOnClickListener(v -> {
            PhotoViewsActivity.startActivity(givGirl, curGirl.url);
        });
    }

    @Override
    public void onDetach() {
        multiTypeRecyclerView.removeAllViews();
        multiTypeRecyclerView = null;
    }

    @Override
    public void onLoading() {
        multiTypeRecyclerView.setLoadingState(LoadingStateDelegate.STATE.LOADING);
    }

    @Override
    public void onSuccess(List<Object> list, boolean isLoadMore) {
        multiTypeRecyclerView.setData(list);
        renderFakeView(0);
        rlGirl.setVisibility(VISIBLE);
        multiTypeRecyclerView.setLoadingState(LoadingStateDelegate.STATE.SUCCEED);
    }

    @Override
    public void onError() {
        multiTypeRecyclerView.setLoadingState(LoadingStateDelegate.STATE.ERROR);
    }

    @Override
    public void onEmpty() {
        multiTypeRecyclerView.setLoadingState(LoadingStateDelegate.STATE.EMPTY);
    }

    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            mPresenter.onRefresh();
        }
    }

    @Override
    public void onLoadingMore() {
        if (mPresenter != null) {
            mPresenter.onLoadingMore();
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        View realItemView = recyclerView.findChildViewUnder(rlGirl.getMeasuredWidth() / 2, rlGirl.getMeasuredHeight() + 1);
        if (realItemView == null) return;
        if (realItemView.getTag() != null) {
            boolean isGirl = (boolean) realItemView.getTag();
            if (isGirl && realItemView.getTop() > 0) {
                rlGirl.setTranslationY(realItemView.getTop() - rlGirl.getHeight());
                renderFakeView(1);
            } else {
                rlGirl.setTranslationY(0);
            }
        } else if (realItemView.getTop() <= rlGirl.getHeight()) {
            rlGirl.setTranslationY(0);
            renderFakeView(2);
        }
    }

    // pos: 为了提高渲染效率的临时解决方案，调用一次pos+1
    private void renderFakeView(int pos) {
        if (lastPos == pos) return;
        lastPos = pos;
        if (multiTypeRecyclerView.getFirstVisibleItem() == null) return;
        for (int i = multiTypeRecyclerView.getFirstVisibleItemPosition(); i >= 0; i--) {
            Object item = multiTypeRecyclerView.getData().get(i);
            if (item instanceof GankItemGirl) {
                curGirl = (GankItemGirl) item;
                break;
            }
        }
        tvTime.setText(DateUtil.convertString2String(curGirl.publishedAt));
        givGirl.loadNetImage(curGirl.url, R.mipmap.liuyifei);
    }

    @Override
    public void onDragOffset(float offsetY) {
        if (offsetY > 0) {
            if (rlGirl.getVisibility() == VISIBLE) {
                rlGirl.setVisibility(INVISIBLE);
            }
        } else {
            if (rlGirl.getVisibility() == INVISIBLE && multiTypeRecyclerView.getItemCount() > 0) {
                rlGirl.setVisibility(VISIBLE);
            }
        }
    }
}
