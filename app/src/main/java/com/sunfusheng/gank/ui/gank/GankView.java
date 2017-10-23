package com.sunfusheng.gank.ui.gank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.gank.MainApplication;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.model.GankItemTitle;
import com.sunfusheng.gank.ui.AboutActivity;
import com.sunfusheng.gank.ui.ImagesActivity;
import com.sunfusheng.gank.util.DateUtil;
import com.sunfusheng.gank.viewprovider.GankItemGirlViewProvider;
import com.sunfusheng.gank.viewprovider.GankItemTitleViewProvider;
import com.sunfusheng.gank.viewprovider.GankItemViewProvider;
import com.sunfusheng.gank.widget.RecyclerViewWrapper.LoadingStateDelegate;
import com.sunfusheng.gank.widget.RecyclerViewWrapper.RecyclerViewWrapper;
import com.sunfusheng.gank.widget.SwipeRefreshLayout.SwipeRefreshLayout;
import com.sunfusheng.glideimageview.GlideImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankView extends FrameLayout implements GankContract.View,
        RecyclerViewWrapper.OnRequestListener,
        RecyclerViewWrapper.OnScrollListener,
        SwipeRefreshLayout.OnDragOffsetListener {

    @BindView(R.id.recyclerViewWrapper)
    RecyclerViewWrapper recyclerViewWrapper;
    @BindView(R.id.giv_girl)
    GlideImageView givGirl;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_girl)
    RelativeLayout rlGirl;
    @BindView(R.id.iv_about)
    ImageView ivAbout;

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

        tvTime.setTypeface(MainApplication.songTi);
        rlGirl.setVisibility(INVISIBLE);

        recyclerViewWrapper.register(GankItem.class, new GankItemViewProvider());
        recyclerViewWrapper.register(GankItemGirl.class, new GankItemGirlViewProvider());
        recyclerViewWrapper.register(GankItemTitle.class, new GankItemTitleViewProvider());

        recyclerViewWrapper.setOnRequestListener(this);
        recyclerViewWrapper.setOnScrollListener(this);
        recyclerViewWrapper.getSwipeRefreshLayout().setOnDragOffsetListener(this);

        givGirl.setOnClickListener(v -> ImagesActivity.startActivity(givGirl.getContext(), MainApplication.girls, curGirl.url));
        ivAbout.setOnClickListener(v -> AboutActivity.startActivity(mContext));
    }

    @Override
    public void onDetach() {
        recyclerViewWrapper.removeAllViews();
        recyclerViewWrapper = null;
    }

    @Override
    public void onLoading() {
        recyclerViewWrapper.setLoadingState(LoadingStateDelegate.STATE.LOADING);
    }

    @Override
    public void onSuccess(List<Object> list, boolean isLoadMore) {
        recyclerViewWrapper.setData(list);
        renderFakeView(0);
        rlGirl.setVisibility(VISIBLE);
        recyclerViewWrapper.setLoadingState(LoadingStateDelegate.STATE.SUCCEED);
    }

    @Override
    public void onError() {
        recyclerViewWrapper.setLoadingState(LoadingStateDelegate.STATE.ERROR);
    }

    @Override
    public void onEmpty() {
        recyclerViewWrapper.setLoadingState(LoadingStateDelegate.STATE.EMPTY);
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
        if (realItemView == null) {
            return;
        }
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
        if (recyclerViewWrapper.getFirstVisibleItem() == null) return;
        for (int i = recyclerViewWrapper.getFirstVisibleItemPosition(); i >= 0; i--) {
            Object item = recyclerViewWrapper.getData().get(i);
            if (item instanceof GankItemGirl) {
                curGirl = (GankItemGirl) item;
                break;
            }
        }
        tvTime.setText(DateUtil.convertString2String(curGirl.publishedAt));
        givGirl.loadImage(curGirl.url, R.mipmap.she);
    }

    @Override
    public void onDragOffset(float offsetY) {
        if (offsetY > 0) {
            if (rlGirl.getVisibility() == VISIBLE) {
                rlGirl.setVisibility(INVISIBLE);
            }
        } else {
            if (rlGirl.getVisibility() == INVISIBLE && recyclerViewWrapper.getItemCount() > 0) {
                rlGirl.setVisibility(VISIBLE);
            }
        }
    }
}
