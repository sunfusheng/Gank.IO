package com.sunfusheng.gank.ui.gank;

import android.content.Context;
import android.os.Handler;

import com.sunfusheng.gank.model.TestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 2017/1/13.
 */
public class GankPresenter implements GankContract.Presenter {

    private Context mContext;
    private GankView mView;
    private List<Object> mList;

    public GankPresenter(Context context, GankView mView) {
        this.mContext = context;
        this.mView = mView;
    }

    @Override
    public void init() {
        mList = new ArrayList<>();
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
    public void loadList() {
        mView.onLoading();
        for (int i=0; i<20; i++) {
            mList.add(new TestEntity("测试数据："+i));
        }
        new Handler().postDelayed(() -> mView.onSuccess(mList), 2000);
    }

    @Override
    public void loadMoreList() {

    }
}
