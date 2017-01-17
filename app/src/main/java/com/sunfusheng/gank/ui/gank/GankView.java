package com.sunfusheng.gank.ui.gank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.widget.RecyclerViewLayout.LoadingStateDelegate;
import com.sunfusheng.gank.widget.RecyclerViewLayout.MultiTypeRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankView extends FrameLayout implements GankContract.View {

    @BindView(R.id.multiTypeRecyclerView)
    MultiTypeRecyclerView multiTypeRecyclerView;

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
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onLoading() {
        multiTypeRecyclerView.setLoadingState(LoadingStateDelegate.STATE.LOADING);
    }

    @Override
    public void onSuccess(List<?> list) {
        multiTypeRecyclerView.setData(list);
        multiTypeRecyclerView.setLoadingState(LoadingStateDelegate.STATE.SUCCEED);
    }

    @Override
    public void onError() {
        multiTypeRecyclerView.setLoadingState(LoadingStateDelegate.STATE.FAILED);
    }

    @Override
    public void onEmpty() {
        multiTypeRecyclerView.setLoadingState(LoadingStateDelegate.STATE.EMPTY);
    }
}
