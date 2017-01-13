package com.sunfusheng.gank.ui.gank;

import android.content.Context;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankPresenter implements GankContract.Presenter {

    private Context mContext;
    private GankView mView;

    public GankPresenter(Context context, GankView mView) {
        this.mContext = context;
        this.mView = mView;
    }

    @Override
    public void init() {
        if (mView != null) {
            mView.setPresenter(this);
            mView.onAttach();
        }
    }

    @Override
    public void unInit() {
        if (mView != null) {
            mView.onDetach();
        }
    }

    @Override
    public void loadGankList() {

    }
}
