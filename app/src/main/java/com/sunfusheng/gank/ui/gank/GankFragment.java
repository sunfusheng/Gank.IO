package com.sunfusheng.gank.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunfusheng.gank.base.BaseFragment;
import com.sunfusheng.gank.model.GankItem;
import com.sunfusheng.gank.model.GankItemGirl;
import com.sunfusheng.gank.model.GankItemTitle;
import com.sunfusheng.gank.viewbinder.ContentItemViewBinder;
import com.sunfusheng.gank.viewbinder.GirlItemViewBinder;
import com.sunfusheng.gank.viewbinder.TitleItemViewBinder;

/**
 * @author by sunfusheng on 2017/1/13.
 */
public class GankFragment extends BaseFragment {

    private GankPresenter mPresenter;
    private GankView mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = new GankView(getContext());
        mPresenter = new GankPresenter(mView);
        mPresenter.init();
        mView.register(GankItem.class, new ContentItemViewBinder());
        mView.register(GankItemGirl.class, new GirlItemViewBinder());
        mView.register(GankItemTitle.class, new TitleItemViewBinder());
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unInit();
        }
    }
}
