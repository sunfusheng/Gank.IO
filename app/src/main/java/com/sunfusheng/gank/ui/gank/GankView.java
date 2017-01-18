package com.sunfusheng.gank.ui.gank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunfusheng.gank.R;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.util.DateUtil;
import com.sunfusheng.gank.widget.RecyclerViewLayout.LoadingStateDelegate;
import com.sunfusheng.gank.widget.RecyclerViewLayout.MultiTypeRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankView extends FrameLayout implements GankContract.View, MultiTypeRecyclerView.OnRequestListener, MultiTypeRecyclerView.OnScrollListener {

    @BindView(R.id.multiTypeRecyclerView)
    MultiTypeRecyclerView multiTypeRecyclerView;
    @BindView(R.id.iv_girl)
    ImageView ivGirl;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_girl)
    RelativeLayout rlGirl;

    private Context mContext;
    private GankContract.Presenter mPresenter;

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

        rlGirl.setVisibility(INVISIBLE);
        multiTypeRecyclerView.setOnRequestListener(this);
        multiTypeRecyclerView.setOnScrollListener(this);
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
    public void onSuccess(List<Object> list) {
        if (list.get(0) instanceof GankItemGirl) {
            girl = (GankItemGirl) list.get(0);
            lastGirl = girl;
            updateFakeView(girl, 1);
        }
        rlGirl.setVisibility(VISIBLE);
        multiTypeRecyclerView.setData(list);
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

    private GankItemGirl girl;
    private GankItemGirl lastGirl;
    private int flag;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        View realItemView = recyclerView.findChildViewUnder(rlGirl.getMeasuredWidth() / 2, rlGirl.getMeasuredHeight() + 1);
        if (realItemView != null) {
            int dealtY = realItemView.getTop() - rlGirl.getHeight();

            if (realItemView.getTag() != null) {
                Object item = realItemView.getTag();
                if (item instanceof GankItemGirl) {
                    girl = (GankItemGirl) item;
                    Log.d("----5 ", "time: "+DateUtil.convertString2String(girl.publishedAt));
                }
                if ((item instanceof GankItemGirl) && realItemView.getTop() > 0) {
                    Log.d("----1 ", "dealtY: " + dealtY + " top: "+realItemView.getTop() + " Y: " +rlGirl.getTranslationY());
                    rlGirl.setTranslationY(dealtY);
                    updateFakeView(girl, 1);
                } else {
                    Log.d("----2 ", "dealtY: " + dealtY + " top: "+realItemView.getTop() + " Y: " +rlGirl.getTranslationY());
                    rlGirl.setTranslationY(0);
                }
            } else if (realItemView.getTop() <= rlGirl.getHeight()) {
                Log.d("----3 ", "dealtY: " + dealtY + " top: "+realItemView.getTop() + " Y: " +rlGirl.getTranslationY());
                rlGirl.setTranslationY(0);
                updateFakeView(girl, 3);
            } else {
                Log.d("----4 ", "dealtY: " + dealtY + " top: "+realItemView.getTop() + " Y: " +rlGirl.getTranslationY());
            }
        }
    }

    private void updateFakeView(GankItemGirl girl, int i) {
        if (girl == null) return;
//        if (lastGirl == girl) return;
//        lastGirl = girl;
        if (flag == i) return;
        if (flag == 1 && lastGirl != null) {
            girl = lastGirl;
        } else if (flag == 3) {
            lastGirl = girl;
        }
        flag = i;

        Log.d("----5 ", "success: "+flag + " time: "+DateUtil.convertString2String(girl.publishedAt));

        tvTime.setText(DateUtil.convertString2String(girl.publishedAt));
        Glide.with(ivGirl.getContext())
                .load(girl.url)
                .centerCrop()
                .placeholder(R.color.transparent)
                .crossFade()
                .into(ivGirl);
    }

}
